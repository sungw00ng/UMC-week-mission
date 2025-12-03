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
import week7.converter.UserConverter;
import week7.service.UserService;
import week7.web.dto.UserRequest;
import week7.global.apiPayload.exception.GeneralException; // 👈 추가
import week7.global.apiPayload.code.GeneralErrorCode; // 👈 추가


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MissionRepository missionRepository;
    private final UserMissionRepository userMissionRepository;

    @Override
    @Transactional
    public User signUp(UserRequest.SignUpDTO request) {

        User newUser = UserConverter.toUser(request);
        // [추가] 선호 음식 및 약관 동의 로직이 있다면 여기서 처리
        return userRepository.save(newUser);
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

    // 🚩 추가: 3. 미션 완료
    @Override
    @Transactional
    public UserMission completeMission(Long userMissionId) {

        // 1. UserMission 엔티티 조회 (예외 처리)
        UserMission userMission = userMissionRepository.findById(userMissionId)
                .orElseThrow(() -> new GeneralException(GeneralErrorCode.NOT_FOUND));

        // 2. 미션 완료 상태 확인
        if (userMission.getIsComplete()) {
            throw new GeneralException(GeneralErrorCode.BAD_REQUEST);
        }

        // 3. 미션 완료 처리 및 사용자 포인트 업데이트
        userMission.markAsComplete();

        User user = userMission.getUser();
        user.addPoint(userMission.getMission().getPoint());

        // (JPA 변경 감지에 의해 자동으로 DB에 반영됨)

        // 4. 업데이트된 UserMission 반환
        return userMission;
    }
}