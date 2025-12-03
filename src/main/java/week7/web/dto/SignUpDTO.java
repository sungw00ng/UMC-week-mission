package week7.web.dto;

import week7.domain.Enums.Gender;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class SignUpDTO {
    private String name;
    private Gender gender;
    private LocalDate birth;
    private String address;
    private String email;
    private String phoneNumber;
    // 약관 동의 관련 필드 등 생략
}