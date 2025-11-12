package week7;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import week7.domain.Location;
import week7.domain.repository.LocationRepository;
import week7.domain.Mission;
import week7.domain.repository.MissionRepository;
import week7.domain.Review;
import week7.domain.repository.ReviewRepository;
import week7.domain.Store;
import week7.domain.repository.StoreRepository;
import week7.domain.User;
import week7.domain.repository.UserRepository;
import week7.domain.UserMission;
import week7.domain.repository.UserMissionRepository;
import week7.domain.Food; // ⭐ Food 엔티티 임포트
import week7.domain.repository.FoodRepository; // ⭐ FoodRepository 임포트

import week7.domain.Enums.Gender;
import week7.domain.Enums.SocialType;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 애플리케이션 시작 시 데이터베이스 초기화 및 미션 쿼리 테스트를 수행하는 컴포넌트입니다.
 * CommandLineRunner 인터페이스를 구현하며, run() 메서드에 @Transactional을 적용하여
 * LazyInitializationException을 방지합니다.
 */
@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final StoreRepository storeRepository;
    private final MissionRepository missionRepository;
    private final ReviewRepository reviewRepository;
    private final UserMissionRepository userMissionRepository;
    private final FoodRepository foodRepository; // ⭐ 추가: FoodRepository 필드 추가

    // ⭐ 수정: FoodRepository 의존성 주입 (TermRepository는 제거)
    public DatabaseInitializer(
            UserRepository userRepository,
            LocationRepository locationRepository,
            StoreRepository storeRepository,
            MissionRepository missionRepository,
            ReviewRepository reviewRepository,
            UserMissionRepository userMissionRepository,
            FoodRepository foodRepository) {
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
        this.storeRepository = storeRepository;
        this.missionRepository = missionRepository;
        this.reviewRepository = reviewRepository;
        this.userMissionRepository = userMissionRepository;
        this.foodRepository = foodRepository; // ⭐ 필드 할당
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        // --- 1. 데이터 초기화 ---
        Pageable pageable = PageRequest.of(0, 5); // 페이징 설정 (페이지 0, 사이즈 5)

        // ✅ 음식 (Food) 데이터 초기화 (foodPreference 유효성 검증을 위해 필요)
        // 이 코드가 정상 작동하려면 Food.java에 name 필드와 @Builder가 필요합니다.
        System.out.println("[초기화] Food 데이터 3개 저장 (ID 1, 2, 3)");
        Food foodA = Food.builder().name("치킨").build(); // ID 1
        Food foodB = Food.builder().name("피자").build(); // ID 2
        Food foodC = Food.builder().name("햄버거").build(); // ID 3
        foodRepository.save(foodA);
        foodRepository.save(foodB);
        foodRepository.save(foodC);

        // 사용자 (User 엔티티에 맞춰 필드 채움)
        System.out.println("[초기화] User 데이터 2개 저장");
        User userA = User.builder()
                .name("UserA")
                .gender(Gender.MALE)
                .birth(LocalDate.of(1990, 1, 1))
                .address("강남구")
                .socialUid("social123")
                .socialType(SocialType.KAKAO)
                .email("usera@example.com")
                .point(0)
                .phoneNumber("010-1111-2222")
                .updatedAt(LocalDateTime.now())
                .build();

        User userB = User.builder()
                .name("UserB")
                .gender(Gender.FEMALE)
                .birth(LocalDate.of(1995, 5, 5))
                .address("서대문구")
                .socialUid("social456")
                .socialType(SocialType.NAVER)
                .email("userb@example.com")
                .point(0)
                .phoneNumber("010-3333-4444")
                .updatedAt(LocalDateTime.now())
                .build();
        userRepository.save(userA);
        userRepository.save(userB);

        // 지역 (Location)
        System.out.println("[초기화] Location 데이터 2개 저장");
        Location locationA = Location.builder().name("강남").build();
        Location locationB = Location.builder().name("홍대").build();
        locationRepository.save(locationA);
        locationRepository.save(locationB);

        // 가게 (Store)
        System.out.println("[초기화] Store 데이터 2개 저장");
        Store storeA = Store.builder().name("강남 카페").detailAddress("강남대로 100").managerNumber("02-1234-5678").location(locationA).build();
        Store storeB = Store.builder().name("강남 식당").detailAddress("테헤란로 200").managerNumber("02-8765-4321").location(locationA).build();
        Store storeC = Store.builder().name("홍대 술집").detailAddress("홍익로 15").managerNumber("02-5555-4444").location(locationB).build();
        storeRepository.save(storeA);
        storeRepository.save(storeB);
        storeRepository.save(storeC);

        // 미션 (Mission)
        System.out.println("[초기화] Mission 데이터 2개 저장");
        Mission missionA = Mission.builder().title("강남 카페 방문").content("커피 한 잔 구매").point(50).deadline(LocalDate.now().plusDays(7)).store(storeA).createdAt(LocalDateTime.now()).build(); // ID 1
        Mission missionB = Mission.builder().title("강남 식당 리뷰").content("식사 후 리뷰 작성").point(100).deadline(LocalDate.now().plusDays(10)).store(storeB).createdAt(LocalDateTime.now()).build(); // ID 2
        missionRepository.save(missionA);
        missionRepository.save(missionB);

        // 리뷰 (Review)
        System.out.println("[초기화] Review 데이터 3개 저장");
        Review reviewA = Review.builder().title("좋아요").content("친절해요!").star(4.5f).hasPhoto(true).user(userA).store(storeA).createdAt(LocalDateTime.now()).build();
        Review reviewB = Review.builder().title("별로").content("불친절해요.").star(2.0f).hasPhoto(false).user(userB).store(storeA).createdAt(LocalDateTime.now()).build();
        Review reviewC = Review.builder().title("최고").content("강력 추천!").star(5.0f).hasPhoto(true).user(userA).store(storeB).createdAt(LocalDateTime.now()).build();
        reviewRepository.save(reviewA);
        reviewRepository.save(reviewB);
        reviewRepository.save(reviewC);

        // 유저 미션 (UserMission)
        System.out.println("[초기화] UserMission 데이터 1개 저장 (진행 중)");
        UserMission userMissionA = UserMission.builder().isComplete(false).user(userA).mission(missionA).build(); // UserA가 MissionA를 수락 (진행 중)
        userMissionRepository.save(userMissionA);


        // --- 2. 미션 쿼리 테스트 (기존 쿼리 테스트 유지) ---

        // [미션 1] 특정 가게의 리뷰 목록 조회 (사진 리뷰만)
        System.out.println("\n[미션 1] '강남 카페'의 리뷰 목록 조회");
        reviewRepository.findByStoreId(storeA.getId()).stream()
                .filter(Review::getHasPhoto)
                .forEach(r -> System.out.println("   - 제목: " + r.getTitle() + ", 사진 여부: " + r.getHasPhoto()));


        // [미션 2] 마이 페이지 화면 쿼리 (특정 사용자의 리뷰 목록, 페이징)
        System.out.println("\n[미션 2] UserA의 리뷰 목록 조회 (@EntityGraph, 페이징)");
        reviewRepository.findByUserId(userA.getId(), pageable)
                .getContent()
                .forEach(r -> System.out.println("   - 작성자: " + r.getUser().getName() + ", 제목: " + r.getTitle()));


        // [미션 3] 내가 진행중/진행 완료한 미션 모아서 보는 쿼리 (진행중, 페이징)
        System.out.println("\n[미션 3] UserA의 진행 중 미션 목록 조회 (@EntityGraph, isComplete=false, 페이징)");
        userMissionRepository.findByUserIdAndIsComplete(userA.getId(), false, pageable)
                .getContent()
                .forEach(um -> System.out.println("   - 제목: " + um.getMission().getTitle() + ", 완료 여부: " + um.getIsComplete()));


        // [미션 4] 홈 화면 쿼리 (현재 선택된 지역에서 도전 가능한 미션 목록, 페이징)
        System.out.println("\n[미션 4] '강남' 지역에서 도전 가능한 미션 목록 조회 (@Query, 페이징)");
        missionRepository.findAvailableMissionsByLocation(locationA.getName(), pageable)
                .getContent()
                .forEach(m -> System.out.println("   - 제목: " + m.getTitle() + ", 가게: " + m.getStore().getName()));
    }
}