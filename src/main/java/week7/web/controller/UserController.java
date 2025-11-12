package week7.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid; // ✅ @Valid import 유지
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import week7.domain.User;
import week7.domain.UserMission;
import week7.converter.UserConverter;
import week7.service.UserService;
import week7.web.dto.UserRequest;
import week7.web.dto.UserResponse;
import week7.global.apiPayload.code.CommonSuccessCode;
import week7.global.apiPayload.code.GeneralErrorCode;

@Tag(name = "User", description = "사용자 및 미션 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // 1. 회원가입 API
    @Operation(summary = "회원가입 API", description = "UMC 회원을 등록하는 API입니다. Validation이 적용됩니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "성공: 회원가입 성공"),
            // ✅ GlobalExceptionHandler가 MethodArgumentNotValidException을 잡아 GeneralErrorCode.VALID_FAIL로 처리
            @ApiResponse(responseCode = "400", description = "실패: 유효성 검증 실패", content = @Content(schema = @Schema(implementation = GeneralErrorCode.class))),
            @ApiResponse(responseCode = "500", description = "실패: 서버 오류", content = @Content(schema = @Schema(implementation = GeneralErrorCode.class)))
    })
    @PostMapping("/signup")
    // ✅ @Valid 추가: DTO의 유효성 검증 활성화
    public week7.global.apiPayload.ApiResponse<UserResponse.SignUpResultDTO> signUp(
            @RequestBody @Valid UserRequest.SignUpDTO request) {

        User user = userService.signUp(request);

        // ✅ FQCN 사용: Ambiguous name (이름 모호성) 문제 방지 및 명확성 확보
        return week7.global.apiPayload.ApiResponse.onSuccess(CommonSuccessCode._CREATED, UserConverter.toSignUpResultDTO(user));
    }

    // 2. 미션 수락 API
    @Operation(summary = "미션 수락 API", description = "특정 사용자가 특정 미션을 수락하고, UserMission 엔티티를 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공: 미션 수락 성공"),
            @ApiResponse(responseCode = "404", description = "실패: 사용자 또는 미션을 찾을 수 없음", content = @Content(schema = @Schema(implementation = GeneralErrorCode.class)))
    })
    @PostMapping("/{userId}/missions/{missionId}")
    // ✅ FQCN 사용: 반환 타입 명확성 확보
    public week7.global.apiPayload.ApiResponse<UserResponse.MissionAcceptResultDTO> acceptMission(
            @Parameter(description = "사용자 ID", example = "1") @PathVariable Long userId,
            @Parameter(description = "미션 ID", example = "2") @PathVariable Long missionId) {

        UserMission userMission = userService.acceptMission(userId, missionId);

        // ✅ FQCN 사용
        return week7.global.apiPayload.ApiResponse.onSuccess(CommonSuccessCode._OK, UserConverter.toMissionAcceptResultDTO(userMission));
    }
}