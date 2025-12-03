package week7.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import week7.domain.Mission;
import week7.domain.User;
import week7.domain.UserFood; // 👈 변경
import week7.domain.UserMission;
import week7.domain.repository.FoodRepository;
import week7.domain.repository.MissionRepository;
import week7.domain.repository.UserFoodRepository; // 👈 변경
import week7.domain.repository.UserMissionRepository;
import week7.domain.repository.UserRepository;
import week7.converter.UserConverter;
import week7.service.UserService;
import week7.web.dto.UserRequest;
import week7.global.apiPayload.code.GeneralErrorCode;
import week7.global.apiPayload.exception.GeneralException;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MissionRepository missionRepository;
    private final UserMissionRepository userMissionRepository;
    private final FoodRepository foodRepository;
    private final UserFoodRepository userFoodRepository; // 👈 UserFoodRepository 주입

    @Override
    @Transactional
    public User signUp(UserRequest.SignUpDTO request) {

        // 1. User 생성 및 저장
        User newUser = UserConverter.toUser(request);
        User savedUser = userRepository.save(newUser);

        // 2. 선호 음식 (UserFood) 저장 로직
        request.getFoodPreferenceIdList().stream()
                // 요청 DTO의 ID를 사용하여 Food 엔티티 조회 및 검증
                .map(foodId -> {
                    return foodRepository.findById(foodId)
                            .orElseThrow(() -> new GeneralException(GeneralErrorCode.FOOD_NOT_FOUND));
                })
                // UserFood 엔티티 생성
                .map(food -> {
                    return UserFood.builder()
                            .user(savedUser)
                            .food(food)
                            .build();
                })
                // UserFood Repository를 통해 저장
                .forEach(userFoodRepository::save);

        return savedUser;
    }

    @Override
    @Transactional
    public UserMission acceptMission(Long userId, Long missionId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(GeneralErrorCode.NOT_FOUND));

        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new GeneralException(GeneralErrorCode.NOT_FOUND));

        UserMission userMission = UserMission.builder()
                .user(user)
                .mission(mission)
                .isComplete(false)
                .build();

        return userMissionRepository.save(userMission);
    }

    @Override
    @Transactional
    public UserMission completeMission(Long userMissionId) {

        // 1. UserMission 엔티티 조회
        UserMission userMission = userMissionRepository.findById(userMissionId)
                .orElseThrow(() -> new GeneralException(GeneralErrorCode.NOT_FOUND));

        // 2. 미션 완료 상태 확인: 이미 완료된 경우 예외 발생 (BAD_REQUEST)
        if (userMission.getIsComplete()) {
            throw new GeneralException(GeneralErrorCode.BAD_REQUEST);
        }

        // 3. 미션 완료 처리 및 사용자 포인트 업데이트
        userMission.markAsComplete();

        User user = userMission.getUser();
        user.addPoint(userMission.getMission().getPoint()); // User.addPoint() 호출

        return userMission;
    }
}