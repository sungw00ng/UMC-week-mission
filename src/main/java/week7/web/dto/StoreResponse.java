// week7.web.dto.StoreResponse.java

package week7.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;
import week7.web.dto.StoreResponse.StorePreviewDTO; // 내부 클래스 임포트

public class StoreResponse {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StorePreviewDTO {
        private Long storeId;
        private String name;
        private String detailAddress;
        private Float score; // ⭐ [추가] 평점 필드 (테스트 시 null 가능)
        private String locationName; // ⭐ [추가] 지역구 이름

        // TODO: Mission 관련 정보를 추가해야 할 수 있습니다.
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StorePreviewListDTO {
        private List<StorePreviewDTO> storeList;
        private Integer listSize; // 현재 페이지의 아이템 개수
        private Integer totalPage; // 전체 페이지 수
        private Long totalElements; // 전체 아이템 개수
        private Boolean isFirst;
        private Boolean isLast;
    }
}