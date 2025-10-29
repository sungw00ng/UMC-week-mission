package week5.domain;

import jakarta.persistence.*;
import lombok.AccessLevel; // AccessLevel 임포트
import lombok.AllArgsConstructor; // AllArgsConstructor 임포트
import lombok.Builder; // Builder 임포트
import lombok.Getter;
import lombok.NoArgsConstructor;
import week4.domain.Enums.Gender;
import week4.domain.Enums.SocialType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder // ⭐ 추가: builder() 메서드 생성을 위함
@NoArgsConstructor(access = AccessLevel.PROTECTED) // ⭐ 수정: protected 기본 생성자
@AllArgsConstructor // ⭐ 추가: Builder 패턴과 함께 사용 권장
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", columnDefinition = "enum('MALE','FEMALE','NONE')")
    private Gender gender;

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @Column(name = "address", columnDefinition = "enum('지역구')")
    private String address;

    @Column(name = "social_uid", nullable = false, length = 255)
    private String socialUid;

    @Enumerated(EnumType.STRING)
    @Column(name = "social_type", columnDefinition = "enum('KAKAO','NAVER','APPLE','GOOGLE')")
    private SocialType socialType;

    @Column(name = "point", nullable = false)
    private Integer point;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // --- 연관 관계: 1(User) : N(리스트) ---
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserFood> userFoods = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserTerm> userTerms = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserMission> userMissions = new ArrayList<>();
}
