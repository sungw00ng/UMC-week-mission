package week7.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

public class UserResponse {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignUpResultDTO {
        private Long userId;
        private LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MissionAcceptResultDTO {
        private Long userMissionId;
        private Long userId;
        private Long missionId;
        private LocalDateTime createdAt;
    }

    // 🚩 추가: 미션 완료 결과 DTO
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MissionCompleteResultDTO {
        private Long userMissionId;
        private Long userId;
        private Long missionId;
        private Integer missionPoint; // 획득한 포인트
        private Integer userTotalPoint; // 업데이트된 사용자 총 포인트
        private LocalDateTime completedAt;
    }
}