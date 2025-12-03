package week7.service;

import week7.domain.User;
import week7.domain.UserMission;
import week7.web.dto.UserRequest;

// 사용자 관련 비즈니스 로직을 정의합니다.
public interface UserService {

    // 1. 회원가입
    User signUp(UserRequest.SignUpDTO request);

    // 2. 미션 수락 (userId와 missionId를 받아 UserMission을 생성)
    UserMission acceptMission(Long userId, Long missionId);

    // 🚩 추가: 3. 미션 완료 (UserMission ID를 받아 처리)
    UserMission completeMission(Long userMissionId);
}