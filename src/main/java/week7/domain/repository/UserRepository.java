package week7.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import week7.domain.User;

// User 엔티티를 관리하는 Repository
// 테스트 데이터 저장을 위해 JpaRepository 기능만 상속받아 사용합니다.
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // (필요하다면 소셜 로그인 시 사용할 findBySocialTypeAndSocialUid 등을 추가할 수 있습니다.)
}
