package week7.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import week7.domain.User;
import week7.domain.UserMission;
import week7.global.apiPayload.code.GeneralErrorCode;
import week7.global.apiPayload.exception.GeneralException;
import week7.service.UserService;
import week7.web.dto.UserRequest;
import week7.web.dto.UserResponse;

import java.time.LocalDateTime;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post; // 👈 POST 요청 import 추가
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any; // 👈 any() 사용을 위해 추가

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@DisplayName("UserController 통합 테스트")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    // URL: /users/{userId}/missions/{userMissionId}/complete 가정
    private static final String COMPLETE_MISSION_URL = "/users/{userId}/missions/{userMissionId}/complete";
    // URL: /users 가정 (회원가입 엔드포인트)
    private static final String SIGN_UP_URL = "/users";


    // -------------------------------------------------------------------------------------
    // 1. 미션 완료 테스트 (기존 코드 유지)
    // -------------------------------------------------------------------------------------

    @Test
    @DisplayName("성공: 진행 중 미션을 완료하면 포인트가 지급되고 상태가 변경된다.")
    void completeMission_Success() throws Exception {
        Long userId = 1L;
        Long userMissionId = 10L;

        UserMission mockUserMission = UserMission.builder()
                .id(userMissionId)
                .user(User.builder().id(userId).build())
                .mission(null)
                .isComplete(true)
                .build();

        doReturn(mockUserMission).when(userService).completeMission(userMissionId);

        mockMvc.perform(patch(COMPLETE_MISSION_URL, userId, userMissionId)
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value("COMMON200"))
                .andExpect(jsonPath("$.result.userMissionId").value(userMissionId))
                .andDo(print());
    }

    @Test
    @DisplayName("실패: 이미 완료된 미션 재시도 시 BAD_REQUEST 에러가 발생한다.")
    void completeMission_Failure_AlreadyCompleted() throws Exception {
        Long userId = 1L;
        Long userMissionId = 10L;

        doThrow(new GeneralException(GeneralErrorCode.BAD_REQUEST))
                .when(userService).completeMission(userMissionId);

        mockMvc.perform(patch(COMPLETE_MISSION_URL, userId, userMissionId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(false))
                .andExpect(jsonPath("$.code").value("COMMON400_1"))
                .andDo(print());
    }

    // -------------------------------------------------------------------------------------
    // 2. 회원가입 테스트 (새로 추가)
    // -------------------------------------------------------------------------------------

    @Test
    @DisplayName("성공: 유효한 정보와 선호 음식 목록으로 회원가입에 성공한다.")
    void signUp_Success() throws Exception {
        // 1. 요청 DTO JSON 생성 (선호 음식 ID 포함)
        String requestJson = """
            {
              "name": "테스트유저",
              "gender": "MALE",
              "birth": "1990-01-01",
              "address": "서울시",
              "specAddress": "강남구",
              "socialUid": "test_uid_123",
              "socialType": "KAKAO",
              "email": "test@example.com",
              "foodPreferenceIdList": [1, 3, 5] 
            }
            """;

        // 2. Mock 객체 설정: userService.signUp()이 저장된 User 객체를 반환하도록 설정
        Long savedUserId = 2L;
        User mockUser = User.builder().id(savedUserId).name("테스트유저").point(0).build();
        // 서비스 내부에서 DTO를 받으므로, 어떤 DTO가 들어와도 mockUser를 반환하도록 any() 사용
        doReturn(mockUser).when(userService).signUp(any(UserRequest.SignUpDTO.class));

        // 3. API 호출 (POST /users)
        mockMvc.perform(post(SIGN_UP_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))

                // 4. 결과 검증 (HTTP 200 OK)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value("COMMON200"))

                // 응답 DTO 필드 검증 (UserResponse.SignUpResultDTO가 반환된다고 가정)
                .andExpect(jsonPath("$.result.userId").value(savedUserId))
                .andDo(print());
    }

    @Test
    @DisplayName("실패: 선호 음식 ID가 누락되면 DTO 유효성 검사 실패로 BAD_REQUEST 에러가 발생한다.")
    void signUp_Failure_MissingFoodPreference() throws Exception {
        // 1. 요청 DTO 생성 (foodPreferenceIdList 필드 누락)
        String requestJson = """
            {
              "name": "테스트유저",
              "gender": "MALE",
              "birth": "1990-01-01",
              "address": "서울시",
              "specAddress": "강남구",
              "socialUid": "test_uid_123",
              "socialType": "KAKAO",
              "email": "test@example.com"
            }
            """;

        // 2. API 호출 및 결과 검증 (HTTP 400 Bad Request)
        // @NotNull 유효성 검사 실패는 Controller 레벨에서 자동으로 처리되어 예외 핸들러로 전달됩니다.
        mockMvc.perform(post(SIGN_UP_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))

                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(false))
                .andExpect(jsonPath("$.code").value("COMMON400_1")) // 유효성 검사 실패 시의 공통 에러 코드
                .andDo(print());
    }

    @Test
    @DisplayName("실패: 유효하지 않은 선호 음식 ID를 포함하면 FOOD_NOT_FOUND 에러가 발생한다.")
    void signUp_Failure_FoodNotFound() throws Exception {
        // 1. 요청 DTO JSON 생성 (존재하지 않는 ID 가정)
        String requestJson = """
            {
              "name": "테스트유저",
              "gender": "MALE",
              "birth": "1990-01-01",
              "address": "서울시",
              "specAddress": "강남구",
              "socialUid": "test_uid_123",
              "socialType": "KAKAO",
              "email": "test@example.com",
              "foodPreferenceIdList": [999] 
            }
            """;

        // 2. Mocking: 서비스가 FOOD_NOT_FOUND 예외를 던지도록 설정 (유효하지 않은 Food ID가 service로 넘어갔을 때)
        doThrow(new GeneralException(GeneralErrorCode.FOOD_NOT_FOUND))
                .when(userService).signUp(any(UserRequest.SignUpDTO.class));

        // 3. API 호출 및 결과 검증 (HTTP 404 Not Found)
        mockMvc.perform(post(SIGN_UP_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))

                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.isSuccess").value(false))
                .andExpect(jsonPath("$.code").value("FOOD4001")) // GeneralErrorCode에서 정의한 코드
                .andDo(print());
    }
}