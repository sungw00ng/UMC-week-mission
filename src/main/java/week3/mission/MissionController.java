package week3.mission;

import week3.common.CommonResponse;
import week3.mission.dto.MissionCompleteRequest;
import week3.mission.dto.MissionCompleteResponse;
import week3.mission.dto.MissionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Arrays;

@RestController
@RequestMapping("/api/v1/missions")
public class MissionController {

    // 1. 미션 목록 조회 (GET /api/v1/missions?status=...)
    @GetMapping
    public ResponseEntity<CommonResponse<List<MissionDto>>> getMissionList(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam("status") String status) {

        // ❗ Service 로직 구현 필요: 토큰에서 user ID 추출, status에 따른 DB 조회
        List<MissionDto> dummyMissions = Arrays.asList(
                MissionDto.builder().missionId(1L).title("독서 미션").status(status).currentProgress(1).totalGoal(5).build()
        );

        return ResponseEntity.ok(CommonResponse.onSuccess(dummyMissions));
    }

    // 2. 미션 성공 처리 (POST /api/v1/missions/{missionId}/complete)
    @PostMapping("/{missionId}/complete")
    public ResponseEntity<CommonResponse<MissionCompleteResponse>> completeMission(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable("missionId") Long missionId,
            @RequestBody MissionCompleteRequest request) {

        // ❗ Service 로직 구현 필요: 미션 상태 변경, 인증 정보 저장, 포인트 지급
        MissionCompleteResponse dummyResponse = MissionCompleteResponse.builder()
                .completedMissionId(missionId)
                .earnedPoints(500)
                .userCurrentLevel(3)
                .build();

        return ResponseEntity.ok(CommonResponse.onSuccess(dummyResponse));
    }
}