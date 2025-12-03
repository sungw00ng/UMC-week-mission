package week7.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

public class ReviewResponse {

    // 개별 리뷰 미리보기 DTO (목록에서 사용)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewPreviewDTO {
        private Long reviewId;
        private String userName; // 작성자 이름
        private Float star;      // 별점
        private String content;   // 리뷰 내용
        private LocalDateTime createdAt; // 작성 일시
    }

    // 리뷰 목록 조회 응답 DTO
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewPreviewListDTO {
        private Long storeId;
        private String storeName;
        private List<ReviewPreviewDTO> reviewList;
        private Integer listSize;
    }
}