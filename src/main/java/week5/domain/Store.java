package week5.domain;

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
@Table(name = "store")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "manager_number", nullable = false, length = 20)
    private String managerNumber;

    @Column(name = "detail_address", nullable = false, length = 255)
    private String detailAddress;

    // N:1 관계: Store 입장에서 Location (FK: location_id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    // 1:N 관계: Store와 Mission
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<Mission> missions = new ArrayList<>();

    // 1:N 관계: Store와 Review
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();
}
