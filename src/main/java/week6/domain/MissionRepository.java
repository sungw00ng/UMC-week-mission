package week6.domain;

import week5.domain.Mission; // Mission 엔티티를 임포트
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface MissionRepository extends JpaRepository<week6.domain.Mission, Long> {

    // 특정 지역의 미션 목록 조회 (@Query 사용 + 페이징)
    // Mission m -> Store s -> Location l 로 조인하여 Location의 name을 조건으로 사용
    @Query("select m from Mission m join m.store s join s.location l where l.name = :locationName")
    Page<week6.domain.Mission> findAvailableMissionsByLocation(@Param("locationName") String locationName, Pageable pageable);
}