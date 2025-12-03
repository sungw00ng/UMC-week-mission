package week7.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import week7.domain.*;
import week7.domain.Enums.Gender;
import week7.domain.Enums.SocialType;
import week7.domain.repository.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 애플리케이션 시작 시 테스트 데이터를 주입하는 클래스입니다.
 * application.yml에서 'spring.profiles.active=test' 또는 'local' 등의 환경에서만 실행되도록 설정할 수 있습니다.
 */
@Component
@RequiredArgsConstructor
@Profile({"local", "test"}) // 이 프로파일에서만 실행되도록 설정
public class DatabaseInitializer implements CommandLineRunner {

    private final LocationRepository locationRepository;
    private final StoreRepository storeRepository;
    private final MissionRepository missionRepository;
    private final UserRepository userRepository;
    private final UserMissionRepository userMissionRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // --- 1. Location (지역) 생성 ---
        Location location = Location.builder()
                .name("강남구")
                .build();
        location = locationRepository.save(location);

        // --- 2. Store (가게) 생성 ---
        Store store = Store.builder()
                .name("강남 떡볶이")
                .managerNumber("01012345678")
                .detailAddress("역삼동 123-45")
                .location(location)
                .build();
        store = storeRepository.save(store);

        // --- 3. Mission (미션) 생성 ---
        Mission mission = Mission.builder()
                .title("첫 방문 미션")
                .deadline(LocalDate.now().plusDays(7))
                .content("만원 이상 주문하기")
                .point(500) // 획득 포인트
                .createdAt(LocalDateTime.now())
                .store(store)
                .build();
        mission = missionRepository.save(mission);

        // --- 4. User (사용자) 생성 ---
        User user = User.builder()
                .name("테스트유저1")
                .gender(Gender.MALE)
                .birth(LocalDate.of(1990, 1, 1))
                .address("강남구")
                .socialUid("testuser1")
                .socialType(SocialType.KAKAO)
                .email("test1@test.com")
                .point(1000) // 초기 포인트 1000점
                .updatedAt(LocalDateTime.now())
                .build();
        user = userRepository.save(user);

        // --- 5. UserMission (사용자 미션) 생성 (미완료 상태) ---
        // 이 UserMission을 완료 API를 통해 완료할 것입니다.
        UserMission userMission = UserMission.builder()
                .user(user)
                .mission(mission)
                .isComplete(false)
                .build();
        userMissionRepository.save(userMission);
    }
}