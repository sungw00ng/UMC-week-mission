package week7.web.dto;

import lombok.Getter;
import java.time.LocalDate;
import java.util.List;
import week7.domain.Enums.Gender;
import week7.domain.Enums.SocialType;
import week7.validation.annotation.ExistFoodPreferences; // ✅ import 추가

public class UserRequest {

    //회원 가입 시 요청 받는 DTO입니다.
    @Getter
    public static class SignUpDTO {
        private String name;
        private Gender gender;
        private LocalDate birth;
        private String address;
        private String socialUid;
        private SocialType socialType;
        private String email;

        private List<Long> agreeTerms;

        // ✅ 커스텀 유효성 검증 어노테이션 적용
        @ExistFoodPreferences
        private List<Long> foodPreference;
    }
}