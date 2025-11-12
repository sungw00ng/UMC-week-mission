package week7.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import week7.domain.Food; // Food 엔티티를 import

// Food 엔티티의 기본 CRUD 작업을 수행하는 Repository
@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    // existsById(ID id)는 JpaRepository에 기본적으로 정의되어 있어
    // FoodPreferenceExistValidator에서 바로 사용할 수 있습니다.
}