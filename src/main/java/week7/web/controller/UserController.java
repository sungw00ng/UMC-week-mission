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

@RestController
@RequiredArgsConstructor
@RequestMapping("/users") // 기본 URI
public class UserController {

    private final UserService userService;

    // 1. 회원가입 API
    // POST /users/signup
    @PostMapping("/signup")
    public ApiResponse<UserResponse.SignUpResultDTO> signUp(@RequestBody UserRequest.SignUpDTO request) {

        User user = userService.signUp(request);

        // ApiResponse.onSuccess()는 글로벌 응답 구조 파일(ApiResponse.java)에 구현되어 있어야 합니다.
        // 여기서는 예시로 성공 코드를 가정합니다.
        // 🚨 이 부분에서 UserConverter.toSignUpResultDTO(user)가 빨간 줄 오류가 발생했습니다.
        return ApiResponse.onSuccess(UserConverter.toSignUpResultDTO(user));
    }

    // 2. 미션 수락 API
    // POST /users/{userId}/missions/{missionId}
    @PostMapping("/{userId}/missions/{missionId}")
    public ApiResponse<UserResponse.MissionAcceptResultDTO> acceptMission(
            @PathVariable Long userId,
            @PathVariable Long missionId) {

        UserMission userMission = userService.acceptMission(userId, missionId);

        // 🚨 이 부분에서 UserConverter.toMissionAcceptResultDTO(userMission)가 빨간 줄 오류가 발생했습니다.
        return ApiResponse.onSuccess(UserConverter.toMissionAcceptResultDTO(userMission));
    }
}