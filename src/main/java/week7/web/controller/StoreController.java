// week7.web.controller.StoreController.java

package week7.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import week7.domain.Store;
import week7.global.apiPayload.ApiResponse;
import week7.global.apiPayload.code.CommonSuccessCode;
import week7.converter.StoreConverter;
import week7.service.StoreService;
import week7.web.dto.StoreResponse;
import org.springframework.data.domain.Page;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores") // 기본 URI
public class StoreController {

    private final StoreService storeService;

    // 1. 특정 지역의 가게 목록 조회 API
    // GET /stores?location={locationName}&page={page}
    @GetMapping("")
    public ApiResponse<StoreResponse.StorePreviewListDTO> getStoreList(
            @RequestParam(name = "location") String locationName, // 지역 이름 (예: 강남구)
            @RequestParam(name = "page") Integer page // 페이지 번호 (0부터 시작)
    ) {
        Page<Store> storePage = storeService.getStoreList(locationName, page);

        StoreResponse.StorePreviewListDTO storeListDTO = StoreConverter.toStorePreviewListDTO(storePage);

        // CommonSuccessCode._OK (200)로 응답
        return ApiResponse.onSuccess(CommonSuccessCode._OK, storeListDTO);
    }
}