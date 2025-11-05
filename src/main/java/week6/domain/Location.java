package week6.domain;

import jakarta.persistence.*;
import lombok.AccessLevel; // AccessLevel 임포트
import lombok.AllArgsConstructor; // AllArgsConstructor 임포트
import lombok.Builder; // Builder 임포트
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder // ⭐ 추가: builder() 메서드 생성을 위함
@NoArgsConstructor(access = AccessLevel.PROTECTED) // ⭐ 수정: protected 기본 생성자
@AllArgsConstructor // ⭐ 추가: Builder 패턴과 함께 사용 권장
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    // 1:N 관계: Location과 Store
    // Lombok이 @Builder를 생성할 때 List 필드 초기화를 방해하지 않도록 @Builder.Default 어노테이션을 
    // 사용할 수도 있지만, 복잡성을 줄이기 위해 일단 이대로 유지합니다.
    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    private List<Store> stores = new ArrayList<>();
}
