package week7.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import week7.domain.Review; // ✅ import 수정

import java.util.List;

@Repository
// ✅ 수정: JpaRepository<Review, Long>으로 변경
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByStoreId(Long storeId);

    @EntityGraph(attributePaths = {"user"})
    Page<Review> findByUserId(Long userId, Pageable pageable);
}