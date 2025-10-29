package week3.home;

import week3.common.CommonResponse;
import week3.home.dto.HomeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {

    @GetMapping // GET /api/v1/home
    public ResponseEntity<CommonResponse<HomeResponse>> getHomeData(
            @RequestHeader("Authorization") String authHeader) {

        // ❗ Service 로직 구현 필요: 사용자 정보, 미션 개요 등 통합 조회
        HomeResponse dummyResponse = HomeResponse.builder()
                .welcomeMessage("OOO님, 환영합니다!")
                .userNickname("mark")
                .missionsInProgressCount(2)
                .recentReviewTitles(Arrays.asList("A 카페 리뷰", "B 마트 리뷰"))
                .build();

        return ResponseEntity.ok(CommonResponse.onSuccess(dummyResponse));
    }
}