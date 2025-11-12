// week7.domain.Food.java (수정 필요)

package week7.domain;

import jakarta.persistence.*;
import lombok.*; // ⭐ lombok 전체 임포트
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder // ⭐ 추가
@NoArgsConstructor(access = AccessLevel.PROTECTED) // ⭐ 추가
@AllArgsConstructor // ⭐ 추가
@Table(name = "food")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private Long id;

    @Column(name = "name", nullable = false, length = 255) // ⭐ 추가
    private String name;

    // Food와 UserFood는 1:N 관계
    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL)
    @Builder.Default // 빌더 패턴 사용 시 리스트 초기화 오류 방지
    private List<UserFood> userFoods = new ArrayList<>();
}