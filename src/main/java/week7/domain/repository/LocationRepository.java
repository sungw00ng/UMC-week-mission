package week7.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import week7.domain.Location;

// Location 엔티티를 관리하는 Repository
// 테스트 데이터 저장을 위해 JpaRepository 기능만 상속받아 사용합니다.
@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

}
