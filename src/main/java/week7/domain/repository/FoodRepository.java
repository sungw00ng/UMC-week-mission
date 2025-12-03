package week7.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import week7.domain.Food;

public interface FoodRepository extends JpaRepository<Food, Long> {
    // Food 엔티티의 기본 CRUD 작업을 위해 필요합니다.
}