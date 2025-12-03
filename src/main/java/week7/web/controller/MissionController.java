package week7.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import week7.converter.MissionConverter;
import week7.domain.Mission;
import week7.global.apiPayload.ApiResponse;
import week7.global.apiPayload.code.CommonSuccessCode;
import week7.service.MissionService;
import week7.web.dto.MissionResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/missions") // 기본 URI 설정
public class MissionController {

    private final MissionService missionService;

    // 특정 지역의 미션 목록 조회 API
    // GET /missions?location={locationName}&page={page}
    @GetMapping("")
    public ApiResponse<MissionResponse.MissionPreviewListDTO> getMissionList(
            @RequestParam(name = "location") String locationName,
            @RequestParam(name = "page") Integer page
    ) {
        Page<Mission> missionPage = missionService.getMissionList(locationName, page);

        MissionResponse.MissionPreviewListDTO missionListDTO = MissionConverter.toMissionPreviewListDTO(missionPage);

        // CommonSuccessCode._OK (200)로 응답 반환
        return ApiResponse.onSuccess(CommonSuccessCode._OK, missionListDTO);
    }
}