package week7.converter;

import week7.domain.User;
import week7.domain.UserMission;
import week7.web.dto.UserRequest;
import week7.web.dto.UserResponse;
import week7.domain.Enums.Gender;
import week7.domain.Enums.SocialType;

import java.time.LocalDateTime;

public class UserConverter {

    /**
     * 1. DTO -> Entity 변환 (회원가입 요청)
     */
    public static User toUser(UserRequest.SignUpDTO request) {

        Gender gender = request.getGender() != null ? request.getGender() : Gender.NONE;
        SocialType socialType = request.getSocialType() != null ? request.getSocialType() : SocialType.KAKAO;

        return User.builder()
                .name(request.getName())
                .gender(gender)
                .birth(request.getBirth())
                .address(request.getAddress())
                .socialUid(request.getSocialUid())
                .socialType(socialType)
                .email(request.getEmail())
                .point(0) // 초기 포인트 0 설정 (가정)
                .updatedAt(LocalDateTime.now())
                .build();
    }

    // ------------------------------------------------------------------------------------------------

    /**
     * 2. Entity -> DTO 변환 (회원가입 결과)
     */
    public static UserResponse.SignUpResultDTO toSignUpResultDTO(User user) {
        return UserResponse.SignUpResultDTO.builder()
                .userId(user.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    /**
     * 3. Entity -> DTO 변환 (미션 수락 결과)
     */
    public static UserResponse.MissionAcceptResultDTO toMissionAcceptResultDTO(UserMission userMission) {
        return UserResponse.MissionAcceptResultDTO.builder()
                .userMissionId(userMission.getId())
                .userId(userMission.getUser().getId())
                .missionId(userMission.getMission().getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    // 🚩 추가: 4. Entity -> DTO 변환 (미션 완료 결과)
    public static UserResponse.MissionCompleteResultDTO toMissionCompleteResultDTO(UserMission userMission) {
        return UserResponse.MissionCompleteResultDTO.builder()
                .userMissionId(userMission.getId())
                .userId(userMission.getUser().getId())
                .missionId(userMission.getMission().getId())
                // Mission 엔티티에서 포인트 가져오기
                .missionPoint(userMission.getMission().getPoint())
                // User 엔티티에서 업데이트된 총 포인트 가져오기
                .userTotalPoint(userMission.getUser().getPoint())
                .completedAt(LocalDateTime.now())
                .build();
    }
}