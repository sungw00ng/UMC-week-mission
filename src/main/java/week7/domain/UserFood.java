package week7.domain;

import jakarta.persistence.*;
import lombok.*;
import week7.domain.Enums.Gender; // User.java에 필요한 import라고 가정
import week7.domain.Enums.SocialType; // User.java에 필요한 import라고 가정

@Entity
@Getter
@Builder // 👈 추가
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED) // 👈 추가
@Table(name = "user_food")
public class UserFood { // 👈 BaseEntity 상속 제거

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_food_id")
    private Long id;

    // N:1 관계: UserFood 입장에서 User (FK: user_id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // N:1 관계: UserFood 입장에서 Food (FK: food_id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;
}