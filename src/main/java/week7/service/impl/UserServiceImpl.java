package week7.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import week7.domain.Mission;
import week7.domain.User;
import week7.domain.UserMission;
import week7.domain.repository.MissionRepository;
import week7.domain.repository.UserMissionRepository;
import week7.domain.repository.UserRepository;
import week7.converter.UserConverter; // Converter 사용
import week7.service.UserService;
import week7.web.dto.UserRequest;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor // final 필드를 주입 (DI) 받기 위한 Lombok 어노테이션
@Transactional(readOnly = true) // 트랜잭션 기본 설정
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MissionRepository missionRepository;
    private final UserMissionRepository userMissionRepository;

    @Override
    @Transactional // 쓰기 작업이므로 트랜잭션 필요
    public User signUp(UserRequest.SignUpDTO request) {

        // 1. DTO -> Entity 변환
        User newUser = UserConverter.toUser(request);

        // 2. [추가] 선호 음식 및 약관 동의 로직이 있다면 여기서 처리 (FoodRepository, TermRepository 등 필요)
        // UserFood, UserTerm 엔티티를 생성하여 저장해야 함

        // 3. Repository를 통해 DB에 저장
        return userRepository.save(newUser);
    }

    @Override
    @Transactional
    public UserMission acceptMission(Long userId, Long missionId) {

        // 1. User와 Mission 엔티티 조회 (예외 처리 필요)
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new IllegalArgumentException("Mission not found: " + missionId));

        // 2. UserMission 엔티티 생성
        UserMission userMission = UserMission.builder()
                .user(user)
                .mission(mission)
                .isComplete(false) // 미션 수락 시 미완료 상태
                .build();

        // 3. Repository에 저장
        return userMissionRepository.save(userMission);
    }
}