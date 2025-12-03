// week7.converter.StoreConverter.java

package week7.converter;

import org.springframework.data.domain.Page;
import week7.domain.Store;
import week7.web.dto.StoreResponse;
import java.util.List;
import java.util.stream.Collectors;

public class StoreConverter {

    /**
     * Store Entity -> StorePreviewDTO 변환
     */
    public static StoreResponse.StorePreviewDTO toStorePreviewDTO(Store store) {
        return StoreResponse.StorePreviewDTO.builder()
                .storeId(store.getId())
                .name(store.getName())
                .detailAddress(store.getDetailAddress())
                .score(4.5f) // ⭐ [임시] 평점은 임의의 값(4.5f)으로 설정 (나중에 실제 로직 구현 필요)
                .locationName(store.getLocation().getName()) // Location 엔티티에서 name을 가져옴
                .build();
    }

    /**
     * Page<Store> -> StorePreviewListDTO 변환 (페이징 정보 포함)
     */
    public static StoreResponse.StorePreviewListDTO toStorePreviewListDTO(Page<Store> storePage) {

        List<StoreResponse.StorePreviewDTO> storePreviewDTOList = storePage.getContent().stream()
                .map(StoreConverter::toStorePreviewDTO)
                .collect(Collectors.toList());

        return StoreResponse.StorePreviewListDTO.builder()
                .storeList(storePreviewDTOList)
                .isFirst(storePage.isFirst())
                .isLast(storePage.isLast())
                .totalPage(storePage.getTotalPages())
                .totalElements(storePage.getTotalElements())
                .listSize(storePreviewDTOList.size())
                .build();
    }
}