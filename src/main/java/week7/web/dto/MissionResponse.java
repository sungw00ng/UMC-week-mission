package week7.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

public class MissionResponse {

    // 개별 미션 미리보기 DTO (목록에서 사용)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MissionPreviewDTO {
        private Long missionId;
        private Long storeId;
        private String storeName;
        private String title;
        private String content;
        private Integer point;
        private LocalDate deadline;
    }

    // 미션 목록 조회 응답 DTO (페이징 정보 포함)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MissionPreviewListDTO {
        private List<MissionPreviewDTO> missionList;
        private Integer listSize;
        private Integer totalPage;
        private Long totalElements;
        private Boolean isFirst;
        private Boolean isLast;
    }
}