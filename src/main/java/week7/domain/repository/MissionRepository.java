package week7.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import week7.domain.Mission; // ✅ import 수정

@Repository
// ✅ 수정: JpaRepository<Mission, Long>으로 변경
public interface MissionRepository extends JpaRepository<Mission, Long> {

    // JPQL의 제네릭 타입도 Mission으로 통일
    @Query("select m from Mission m join m.store s join s.location l where l.name = :locationName")
    Page<Mission> findAvailableMissionsByLocation(@Param("locationName") String locationName, Pageable pageable);
}