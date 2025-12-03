package week7.service;

import week7.domain.Review;
import week7.domain.Store;
import java.util.List;

public interface ReviewService {

    // 특정 가게의 리뷰 목록 조회
    List<Review> getReviewList(Long storeId);

    // Store 엔티티를 조회하는 헬퍼 메서드 (Controller에서 Store 정보 접근을 위해)
    Store getStore(Long storeId);
}