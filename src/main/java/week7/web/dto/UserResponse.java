// 파일: week7.web.dto.UserResponse.java
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
}