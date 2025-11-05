package week7.web.dto;

import lombok.Getter;
import java.time.LocalDate;
import java.util.List;
import week7.domain.Enums.Gender;
import week7.domain.Enums.SocialType;

public class UserRequest {

    //회원 가입 시 요청 받는 DTO입니다.
    @Getter // <== 이 어노테이션 덕분에 request.getGender() 호출이 가능합니다.
    public static class SignUpDTO {
        // 필수 입력 필드들
        private String name;
        private Gender gender; // <== 타입 참조 문제 최종 해결
        private LocalDate birth;
        private String address;
        private String socialUid;
        private SocialType socialType;
        private String email;

        // 약관 동의 리스트
        private List<Long> agreeTerms;
        // 선호 음식 리스트
        private List<Long> foodPreference;
    }

    // 필요하다면 다른 요청 DTO (예: LoginDTO, UpdateInfoDTO 등)를 여기에 추가합니다.
}