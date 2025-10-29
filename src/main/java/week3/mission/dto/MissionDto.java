package week3.mission.dto;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class MissionDto {
    private Long missionId;
    private String title;
    private String status; // in_progress, completed
    private Integer currentProgress;
    private Integer totalGoal;
}