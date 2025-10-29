package week5.domain;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 미션 1: 특정 가게의 리뷰 목록 조회 (메서드 이름 기반 쿼리)
    List<Review> findByStoreId(Long storeId);

    /**
     * 미션 2: 마이 페이지 리뷰 목록 조회 (페이징 포함)
     * 💡 LazyInitializationException 해결을 위해 @EntityGraph를 사용하여 'user' 엔티티를 즉시 로딩(Eager Fetching)합니다.
     * 이렇게 하면 리뷰를 조회할 때 작성자 정보도 함께 가져오므로 트랜잭션 종료 후에도 접근이 가능합니다.
     */
    @EntityGraph(attributePaths = {"user"})
    Page<Review> findByUserId(Long userId, Pageable pageable);
}
