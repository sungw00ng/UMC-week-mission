package week3.mission.dto;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class MissionCompleteResponse {
    private Long completedMissionId;
    private Integer earnedPoints;
    private Integer userCurrentLevel;
}