package week7.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import week7.domain.User;
import week7.domain.UserMission;
import week7.global.apiPayload.ApiResponse;
import week7.converter.UserConverter;
import week7.service.UserService;
import week7.web.dto.UserRequest;
import week7.web.dto.UserResponse;
import week7.global.apiPayload.code.CommonSuccessCode;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // 1. 회원가입 API
    @PostMapping("/signup")
    public ApiResponse<UserResponse.SignUpResultDTO> signUp(@RequestBody UserRequest.SignUpDTO request) {

        User user = userService.signUp(request);

        return ApiResponse.onSuccess(CommonSuccessCode._CREATED, UserConverter.toSignUpResultDTO(user));
    }

    // 2. 미션 수락 API
    @PostMapping("/{userId}/missions/{missionId}")
    public ApiResponse<UserResponse.MissionAcceptResultDTO> acceptMission(
            @PathVariable Long userId,
            @PathVariable Long missionId) {

        UserMission userMission = userService.acceptMission(userId, missionId);

        return ApiResponse.onSuccess(CommonSuccessCode._OK, UserConverter.toMissionAcceptResultDTO(userMission));
    }

    // 🚩 추가: 3. 진행 중 미션 완료 API
    // PATCH /users/missions/{userMissionId}/complete
    @PatchMapping("/missions/{userMissionId}/complete")
    public ApiResponse<UserResponse.MissionCompleteResultDTO> completeMission(
            @PathVariable Long userMissionId) {

        UserMission completedUserMission = userService.completeMission(userMissionId);

        return ApiResponse.onSuccess(CommonSuccessCode._OK,
                UserConverter.toMissionCompleteResultDTO(completedUserMission));
    }
}