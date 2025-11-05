package week7.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus; // 👈 import 추가

@Getter
@AllArgsConstructor
public enum CommonSuccessCode implements BaseSuccessCode { // 👈 BaseSuccessCode 구현

    // 🚩 수정: HttpStatus를 첫 번째 인자로 추가했습니다.
    _OK(HttpStatus.OK, "COMMON200", "요청에 성공했습니다."),
    _CREATED(HttpStatus.CREATED, "COMMON201", "리소스 생성에 성공했습니다.");

    // 🚩 추가: HttpStatus 필드
    private final HttpStatus status;
    private final String code;
    private final String message;

    // Lombok의 @Getter 덕분에 getStatus(), getCode(), getMessage()가 자동으로 구현됩니다.
}