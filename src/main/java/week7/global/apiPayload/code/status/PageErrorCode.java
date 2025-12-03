package week7.global.apiPayload.code.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import week7.global.apiPayload.code.BaseErrorCode;

// Page 관련 에러 코드를 정의
@Getter
@AllArgsConstructor
public enum PageErrorCode implements BaseErrorCode {

    PAGE_NUMBER_TOO_SMALL(HttpStatus.BAD_REQUEST, "PAGE4001", "페이지 번호는 1 이상이어야 합니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}