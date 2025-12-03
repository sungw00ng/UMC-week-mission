package week7.web.dto;

import lombok.Getter;
import week7.domain.Enums.Gender;
import week7.domain.Enums.SocialType;

import java.util.List;
import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserRequest {

    // 1. 회원가입 요청 DTO (UserServiceImpl.signUp의 입력)
    @Getter
    public static class SignUpDTO {

        // --- User 엔티티 필드 관련 ---

        @NotBlank
        String name;

        // String이 아닌 Enum으로 받기 때문에 @NotBlank 대신 @NotNull 사용
        @NotNull
        Gender gender;

        @NotNull
        LocalDate birth;

        @NotBlank
        String address;

        @NotBlank
        String specAddress;

        String socialUid;

        SocialType socialType;

        String email;

        // --- UserFood (선호 음식) 저장 관련 ---

        @NotNull(message = "선호 음식 목록은 필수입니다.")
        @Size(min = 1, message = "선호 음식을 최소 하나 이상 선택해야 합니다.")
        // 선호하는 Food 엔티티의 ID 목록
        private List<Long> foodPreferenceIdList; // 👈 UserServiceImpl에서 이 목록을 사용합니다.
    }
}