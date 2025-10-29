package week5.domain;

import jakarta.persistence.*;
import lombok.AccessLevel; // AccessLevel 임포트
import lombok.AllArgsConstructor; // AllArgsConstructor 임포트
import lombok.Builder; // Builder 임포트
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder // ⭐ 추가: builder() 메서드 생성을 위함
@NoArgsConstructor(access = AccessLevel.PROTECTED) // ⭐ 수정: protected 기본 생성자
@AllArgsConstructor // ⭐ 추가: Builder 패턴과 함께 사용 권장
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
}
