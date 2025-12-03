package week7.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import week7.converter.ReviewConverter;
import week7.domain.Review;
import week7.domain.Store;
import week7.global.apiPayload.ApiResponse;
import week7.global.apiPayload.code.CommonSuccessCode;
import week7.service.ReviewService;
import week7.web.dto.ReviewResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
// Store에 종속된 리소스이므로 /stores/{storeId}/reviews 형태의 URI를 사용합니다.
@RequestMapping("/stores")
public class ReviewController {

    private final ReviewService reviewService;

    // 특정 가게의 리뷰 목록 조회 API
    // GET /stores/{storeId}/reviews
    @GetMapping("/{storeId}/reviews")
    public ApiResponse<ReviewResponse.ReviewPreviewListDTO> getStoreReviewList(
            @PathVariable Long storeId
    ) {
        // 1. 리뷰 목록 조회
        List<Review> reviewList = reviewService.getReviewList(storeId);

        // 2. Store 정보 조회 (Converter에 전달하기 위함)
        Store store = reviewService.getStore(storeId);

        // 3. Entity -> DTO 변환
        ReviewResponse.ReviewPreviewListDTO resultDTO = ReviewConverter.toReviewPreviewListDTO(store, reviewList);

        // 4. 성공 응답 반환 (CommonSuccessCode._OK (200) 사용)
        return ApiResponse.onSuccess(CommonSuccessCode._OK, resultDTO);
    }
}