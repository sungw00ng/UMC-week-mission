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
import week7.web.dto.UserResponse;

import java.time.LocalDateTime;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;

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


    @Test
    @DisplayName("성공: 진행 중 미션을 완료하면 포인트가 지급되고 상태가 변경된다.")
    void completeMission_Success() throws Exception {
        Long userId = 1L;
        // 미션 완료 시도할 UserMission의 ID
        Long userMissionId = 10L;

        // 1. Mock 객체 설정: 실제 반환 타입인 UserMission 엔티티를 생성
        UserMission mockUserMission = UserMission.builder()
                .id(userMissionId)
                .user(User.builder().id(userId).build())
                .mission(null)
                .isComplete(true) // 완료 상태
                .build();

        // 2. Mocking 라인 수정: UserMission 객체를 반환하도록 설정하고, userMissionId만 인자로 사용
        //    (UserService 시그니처: completeMission(Long userMissionId))
        doReturn(mockUserMission).when(userService).completeMission(userMissionId);

        // 3. API 호출 (PATCH)
        mockMvc.perform(patch(COMPLETE_MISSION_URL, userId, userMissionId)
                        .contentType(MediaType.APPLICATION_JSON))

                // 4. 결과 검증 (HTTP 200 OK)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value("COMMON200"))

                // 주의: 컨트롤러가 UserMission 엔티티를 DTO로 변환하여 반환한다고 가정하고 검증
                .andExpect(jsonPath("$.result.userMissionId").value(userMissionId))
                .andDo(print());
    }

    @Test
    @DisplayName("실패: 이미 완료된 미션 재시도 시 BAD_REQUEST 에러가 발생한다.")
    void completeMission_Failure_AlreadyCompleted() throws Exception {
        Long userId = 1L;
        Long userMissionId = 10L;

        // 1. Mocking 라인 수정: GeneralException 예외를 던지도록 설정하고, userMissionId만 인자로 사용
        doThrow(new GeneralException(GeneralErrorCode.BAD_REQUEST))
                .when(userService).completeMission(userMissionId);

        // 2. API 호출 및 결과 검증 (HTTP 400 Bad Request)
        mockMvc.perform(patch(COMPLETE_MISSION_URL, userId, userMissionId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(false))
                .andExpect(jsonPath("$.code").value("COMMON400_1"))
                .andDo(print());
    }
}