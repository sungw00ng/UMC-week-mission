// src/main/java/week4/domain/Mission.java
package week4.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "mission")
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_id")
    private Long id;

    @Column(name = "deadline", nullable = false)
    private LocalDate deadline;

    @Column(name = "conditional", nullable = false, length = 255)
    private String conditional;

    @Column(name = "point", nullable = false)
    private Integer point;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // N:1 관계: Mission 입장에서 Store (FK: store_id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    // 1:N 관계 (N:M 해소): Mission과 UserMission
    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL)
    private List<UserMission> userMissions = new ArrayList<>();
}