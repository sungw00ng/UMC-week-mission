package week3.mission.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class MissionCompleteRequest {
    private String proofImageUrl;
    private String memo;
    private String completedDate;
}