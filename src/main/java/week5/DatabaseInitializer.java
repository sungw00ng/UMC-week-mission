package week5;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import week5.domain.Location;
import week5.domain.LocationRepository;
import week5.domain.Mission;
import week5.domain.MissionRepository;
import week5.domain.Review;
import week5.domain.ReviewRepository;
import week5.domain.Store;
import week5.domain.StoreRepository;
import week5.domain.User;
import week5.domain.UserRepository;
import week5.domain.UserMission;
import week5.domain.UserMissionRepository;

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

    public DatabaseInitializer(
            UserRepository userRepository,
            LocationRepository locationRepository,
            StoreRepository storeRepository,
            MissionRepository missionRepository,
            ReviewRepository reviewRepository,
            UserMissionRepository userMissionRepository) {
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
        this.storeRepository = storeRepository;
        this.missionRepository = missionRepository;
        this.reviewRepository = reviewRepository;
        this.userMissionRepository = userMissionRepository;
    }

    /**
     * @Transactional: 이 메서드 전체를 하나의 트랜잭션으로 묶어 Lazy Loading을 허용합니다.
     */
    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // ----------------------------------------------------
        // 1. 테스트 데이터 셋업
        // ----------------------------------------------------

        User userA = User.builder()
                .name("Tester A")
                .email("testa@umc.com")
                .birth(LocalDate.of(1990, 1, 1))
                .point(1000)
                .socialUid("social_uid_A")
                .updatedAt(LocalDateTime.now())
                .build();

        User userB = User.builder()
                .name("Tester B")
                .email("testb@umc.com")
                .birth(LocalDate.of(1995, 5, 20))
                .point(500)
                .socialUid("social_uid_B")
                .updatedAt(LocalDateTime.now())
                .build();
        userRepository.save(userA);
        userRepository.save(userB);

        // 지역 (강남, 홍대)
        Location locationGangnam = Location.builder().name("강남").build();
        Location locationHongdae = Location.builder().name("홍대").build();
        locationRepository.save(locationGangnam);
        locationRepository.save(locationHongdae);

        // 가게 (Store A, Store B)
        Store storeA = Store.builder().name("강남 커피숍").detailAddress("강남점").managerNumber("02-1111-2222").location(locationGangnam).build();
        Store storeB = Store.builder().name("홍대 빵집").detailAddress("홍대점").managerNumber("02-3333-4444").location(locationHongdae).build();
        storeRepository.save(storeA);
        storeRepository.save(storeB);

        // 미션 (Mission 1, 2, 3)
        Mission mission1 = Mission.builder().title("강남 아메리카노 미션").content("구매").store(storeA).point(10).deadline(LocalDate.now().plusDays(30)).build();
        Mission mission2 = Mission.builder().title("강남 베이글 미션").content("구매").store(storeA).point(20).deadline(LocalDate.now().plusDays(10)).build();
        Mission mission3 = Mission.builder().title("홍대 샌드위치 미션").content("구매").store(storeB).point(30).deadline(LocalDate.now().plusDays(50)).build();
        missionRepository.save(mission1);
        missionRepository.save(mission2);
        missionRepository.save(mission3);

        // 리뷰 (Review A, B, C)
        Review reviewA = Review.builder().title("리뷰 A").content("커피가 맛있어요.").star(4.5f).createdAt(LocalDateTime.now()).user(userA).store(storeA).hasPhoto(false).build();
        Review reviewB = Review.builder().title("리뷰 B (사진 있음)").content("사진이 예뻐요.").star(5.0f).createdAt(LocalDateTime.now()).user(userA).store(storeA).hasPhoto(true).build();
        Review reviewC = Review.builder().title("리뷰 C").content("샌드위치가 훌륭해요.").star(3.0f).createdAt(LocalDateTime.now()).user(userB).store(storeB).hasPhoto(false).build();
        reviewRepository.save(reviewA);
        reviewRepository.save(reviewB);
        reviewRepository.save(reviewC);

        // 사용자 미션 (UserMission)
        UserMission um1_pending = UserMission.builder().user(userA).mission(mission1).isComplete(false).build(); // User A: 진행중
        UserMission um2_completed = UserMission.builder().user(userA).mission(mission2).isComplete(true).build(); // User A: 완료
        UserMission um3_pending = UserMission.builder().user(userB).mission(mission3).isComplete(false).build();  // User B: 진행중
        userMissionRepository.save(um1_pending);
        userMissionRepository.save(um2_completed);
        userMissionRepository.save(um3_pending);

        System.out.println("----------------------------------------------------");
        System.out.println("✅ 미션 테스트 데이터 셋업 완료");
        System.out.println("----------------------------------------------------");

        // ----------------------------------------------------
        // 2. 4가지 미션 쿼리 테스트 (지연 로딩 문제 해결됨)
        // ----------------------------------------------------

        Pageable pageable = PageRequest.of(0, 10);

        // [미션 1] 특정 가게의 리뷰 목록 조회 (사진 배제)
        System.out.println("\n[미션 1] '강남 커피숍'의 리뷰 목록 조회 (사진 배제)");
        reviewRepository.findByStoreId(storeA.getId()).stream()
                .filter(r -> !r.getHasPhoto())
                .forEach(r -> System.out.println("   - 제목: " + r.getTitle() + ", 사진 여부: " + r.getHasPhoto()));


        // [미션 2] 마이 페이지 화면 쿼리 (특정 사용자의 리뷰 목록, 페이징)
        System.out.println("\n[미션 2] UserA의 리뷰 목록 조회 (페이징)");
        reviewRepository.findByUserId(userA.getId(), pageable)
                .getContent()
                .forEach(r -> System.out.println("   - 작성자: " + r.getUser().getName() + ", 제목: " + r.getTitle()));


        // [미션 3] 내가 진행중/진행 완료한 미션 모아서 보는 쿼리 (진행중, 페이징)
        System.out.println("\n[미션 3] UserA의 진행 중 미션 목록 조회 (isComplete=false, 페이징)");
        userMissionRepository.findByUserIdAndIsComplete(userA.getId(), false, pageable)
                .getContent()
                .forEach(um -> System.out.println("   - 제목: " + um.getMission().getTitle() + ", 완료 여부: " + um.getIsComplete()));


        // [미션 4] 홈 화면 쿼리 (현재 선택된 지역에서 도전 가능한 미션 목록, 페이징)
        // @Transactional 덕분에 m.getStore().getName() 접근 시 LazyInitializationException 발생하지 않습니다.
        System.out.println("\n[미션 4] '강남' 지역에서 도전 가능한 미션 목록 조회 (@Query, 페이징)");
        missionRepository.findAvailableMissionsByLocation("강남", pageable)
                .getContent()
                .forEach(m -> System.out.println("   - 제목: " + m.getTitle() + ", 가게: " + m.getStore().getName()));

        System.out.println("\n----------------------------------------------------");
        System.out.println("✅ 모든 미션 쿼리 테스트 완료");
        System.out.println("----------------------------------------------------");
    }
}
