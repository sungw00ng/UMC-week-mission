package week7.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import week7.domain.UserMission;

@Repository
public interface UserMissionRepository extends JpaRepository<UserMission, Long> {

    /**
     * 미션 3: 특정 사용자의 진행 중 미션 목록 조회 (페이징 포함)
     * 💡 LazyInitializationException 해결을 위해 @EntityGraph를 사용하여 'mission' 엔티티를 즉시 로딩합니다.
     * UserMission 엔티티 내부에 mission 필드가 있다고 가정하고 attributePaths = {"mission"}을 추가합니다.
     */
    @EntityGraph(attributePaths = {"mission"})
    Page<UserMission> findByUserIdAndIsComplete(Long userId, Boolean isComplete, Pageable pageable);
}
