package week3.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
// import jakarta.validation.constraints.*; // Validation 사용 시

@Getter
@NoArgsConstructor
public class UserSignUpRequest {
    // @Email(message = "유효하지 않은 이메일 형식입니다.") // 예시
    private String email;

    // @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.") // 예시
    private String password;

    private String nickname;
    private String name;
}