package week3.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 모든 API 응답의 형식을 통일하기 위한 Wrapper 클래스
 */
@Getter
@AllArgsConstructor
public class CommonResponse<T> {
    private final Boolean isSuccess;
    private final Integer code;
    private final String message;
    private final T result;

    // 성공 응답 포맷 (HTTP 200/201 시 사용)
    public static <T> CommonResponse<T> onSuccess(T result) {
        return new CommonResponse<>(true, 1000, "요청에 성공하였습니다.", result);
    }
}