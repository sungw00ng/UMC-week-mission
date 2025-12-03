package week7.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import week7.domain.Mission;
import week7.domain.User;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "user_mission")
public class UserMission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_mission_id")
    private Long id;

    // DDL의 bit(1)은 Boolean으로 매핑
    @Column(name = "is_complete", nullable = false)
    private Boolean isComplete;

    // N:1 관계: UserMission 입장에서 User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // N:1 관계: UserMission 입장에서 Mission
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id", nullable = false)
    private Mission mission;

    // 🚩 추가: 미션을 완료 상태로 변경하는 비즈니스 로직
    public void markAsComplete() {
        this.isComplete = true;
    }
}