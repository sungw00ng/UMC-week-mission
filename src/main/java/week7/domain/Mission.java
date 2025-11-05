package week7.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder // builder() 메서드 생성을 위함
@NoArgsConstructor(access = AccessLevel.PROTECTED) // protected 기본 생성자
@AllArgsConstructor(access = AccessLevel.PRIVATE) // Builder 사용을 강제하기 위해 PRIVATE 설정
@Table(name = "mission")
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_id")
    private Long id;

    // 🚩 추가: Application.java의 Mission.builder().title("...") 및 getTitle() 호출을 위해 추가
    private String title;

    @Column(name = "deadline", nullable = false)
    private LocalDate deadline;

    // 🚩 수정: Application.java에서 Mission.builder().content("구매")를 사용하므로 conditional을 content로 변경
    @Column(name = "content", nullable = false, length = 255)
    private String content;

    @Column(name = "point", nullable = false)
    private Integer point;

    // Application.java에서 임시로 Mission 객체를 만들 때,
    // 이 필드를 설정하지 않으면 런타임 오류가 날 수 있습니다.
    // 임시 객체 생성을 위해 created_at 필드를 제거하거나 builder에서 설정해주어야 합니다.
    // 여기서는 테스트 편의를 위해 필드를 유지하되, Application.java에서 설정이 누락되면 문제가 될 수 있습니다.
    private LocalDateTime createdAt; // @Column(name = "created_at", nullable = false)는 Lombok이 처리

    // N:1 관계: Mission 입장에서 Store (FK: store_id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    // 1:N 관계 (N:M 해소): Mission과 UserMission
    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL)
    private List<UserMission> userMissions = new ArrayList<>();
}
