package week7.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder // builder() 메서드 생성을 위함
@NoArgsConstructor(access = AccessLevel.PROTECTED) // protected 기본 생성자
@AllArgsConstructor(access = AccessLevel.PRIVATE) // ⭐ 수정: Builder 사용을 강제하기 위해 PRIVATE 설정
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    // 🚩 수정/추가: Application.java에서 Review.builder().title("...") 및 getTitle() 호출을 위해 추가
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "star", nullable = false)
    private Float star;

    // Application.java의 테스트 코드에서 사용되는 hasPhoto 필드
    @Column(name = "has_photo")
    private Boolean hasPhoto;

    // --- 연관 관계: N:1 (FK: user_id) ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // --- 연관 관계: N:1 (FK: store_id) ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    // --- 연관 관계: 1:N (Review : ReviewPhoto) ---
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<ReviewPhoto> reviewPhotos = new ArrayList<>();

    // --- 연관 관계: 1:1 (Review : Reply) ---
    @OneToOne(mappedBy = "review", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Reply reply;
}
