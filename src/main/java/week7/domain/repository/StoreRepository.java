// week7.domain.repository.StoreRepository.java

package week7.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import week7.domain.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    /**
     * 특정 Location name을 조건으로 가게 목록을 페이징하여 조회하는 메서드
     * 💡 메서드 이름 기반 쿼리 (Query Method)를 사용합니다.
     * Store s -> Location l 로 조인하여 Location의 name을 조건으로 사용합니다.
     */
    Page<Store> findByLocationName(String locationName, Pageable pageable);
}