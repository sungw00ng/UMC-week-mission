package week3.home.dto;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter @Builder
public class HomeResponse {
    private String welcomeMessage;
    private String userNickname;
    private Integer missionsInProgressCount;
    private List<String> recentReviewTitles;
}