package week7.converter;

import week7.domain.User;
import week7.domain.UserMission;
import week7.web.dto.UserRequest;
import week7.web.dto.UserResponse; // DTO 컨테이너 임포트
import week7.domain.Enums.Gender;
import week7.domain.Enums.SocialType;

import java.time.LocalDateTime;

public class UserConverter {

    /**
     * 1. DTO -> Entity 변환 (회원가입 요청)
     */
    public static User toUser(UserRequest.SignUpDTO request) {

        // Enum 필드의 Null 처리 (DTO에 값이 없을 경우 기본값으로 처리)
        Gender gender = request.getGender() != null ? request.getGender() : Gender.NONE;
        SocialType socialType = request.getSocialType() != null ? request.getSocialType() : SocialType.KAKAO;

        // User 엔티티에 맞게 빌더 패턴으로 변환
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
     * UserConverter.toSignUpResultDTO(user) 오류 해결: UserResponse 내부 클래스 사용
     */
    public static UserResponse.SignUpResultDTO toSignUpResultDTO(User user) {
        return UserResponse.SignUpResultDTO.builder()
                .userId(user.getId())
                // UserResponse.SignUpResultDTO에 createdAt 필드가 있으므로 문제 해결
                .createdAt(LocalDateTime.now())
                .build();
    }

    /**
     * 3. Entity -> DTO 변환 (미션 수락 결과)
     * UserConverter.toMissionAcceptResultDTO(userMission) 오류 해결: UserResponse 내부 클래스 사용
     */
    public static UserResponse.MissionAcceptResultDTO toMissionAcceptResultDTO(UserMission userMission) {
        return UserResponse.MissionAcceptResultDTO.builder()
                .userMissionId(userMission.getId())
                .userId(userMission.getUser().getId())
                .missionId(userMission.getMission().getId())
                // UserResponse.MissionAcceptResultDTO에 createdAt 필드가 있으므로 문제 해결
                .createdAt(LocalDateTime.now())
                .build();
    }
}