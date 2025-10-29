package week3.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class ReviewRequest {
    private String targetType; // "store" 또는 "mission"
    private Long targetId;
    private Integer rating; // 1~5점
    private String content;
}