package week3.review;

import week3.common.CommonResponse;
import week3.review.dto.ReviewRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    @PostMapping // POST /api/v1/reviews
    public ResponseEntity<CommonResponse<Long>> createReview(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody ReviewRequest request) {

        // ❗ Service 로직 구현 필요: 리뷰 데이터 검증, DB 저장
        Long dummyReviewId = 42L;

        return ResponseEntity
                .status(HttpStatus.CREATED) // HTTP 201 Created
                .body(CommonResponse.onSuccess(dummyReviewId));
    }
}