package week7.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import week7.domain.UserFood;

public interface UserFoodRepository extends JpaRepository<UserFood, Long> {
    // 기본 CRUD 메서드 제공
}