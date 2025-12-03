package week7.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import week7.domain.Review;
import week7.domain.Store;
import week7.domain.repository.ReviewRepository;
import week7.domain.repository.StoreRepository; // StoreRepository 주입 필요
import week7.global.apiPayload.code.GeneralErrorCode; // 예외 처리용
import week7.global.apiPayload.exception.GeneralException; // 예외 처리용
import week7.service.ReviewService;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;

    @Override
    public List<Review> getReviewList(Long storeId) {
        // 1. Store 존재 여부 확인 (Store가 없으면 리뷰도 조회할 수 없음)
        if (!storeRepository.existsById(storeId)) {
            // GeneralException을 사용하여 404 응답을 반환
            throw new GeneralException(GeneralErrorCode.NOT_FOUND);
        }

        // 2. ReviewRepository의 findByStoreId 메서드를 사용하여 리뷰 목록 조회
        // ReviewRepository.java 스니펫에 findByStoreId가 정의되어 있음
        return reviewRepository.findByStoreId(storeId);
    }

    // Controller에서 Store 정보를 얻기 위한 헬퍼 메서드
    @Override
    public Store getStore(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new GeneralException(GeneralErrorCode.NOT_FOUND));
    }
}