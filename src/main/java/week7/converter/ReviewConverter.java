package week7.converter;

import week7.domain.Review;
import week7.domain.Store;
import week7.web.dto.ReviewResponse;
import java.util.List;
import java.util.stream.Collectors;

public class ReviewConverter {

    /**
     * Review Entity -> ReviewPreviewDTO 변환
     */
    public static ReviewResponse.ReviewPreviewDTO toReviewPreviewDTO(Review review) {
        return ReviewResponse.ReviewPreviewDTO.builder()
                .reviewId(review.getId())
                // Review 엔티티 내에 User 엔티티가 포함되어 있고, User에 name 필드가 있다고 가정
                .userName(review.getUser().getName())
                .star(review.getStar())
                .content(review.getContent())
                .createdAt(review.getCreatedAt())
                .build();
    }

    /**
     * List<Review> -> ReviewPreviewListDTO 변환
     */
    public static ReviewResponse.ReviewPreviewListDTO toReviewPreviewListDTO(Store store, List<Review> reviewList) {

        List<ReviewResponse.ReviewPreviewDTO> reviewPreviewDTOList = reviewList.stream()
                .map(ReviewConverter::toReviewPreviewDTO)
                .collect(Collectors.toList());

        return ReviewResponse.ReviewPreviewListDTO.builder()
                .storeId(store.getId())
                .storeName(store.getName())
                .reviewList(reviewPreviewDTOList)
                .listSize(reviewPreviewDTOList.size())
                .build();
    }
}