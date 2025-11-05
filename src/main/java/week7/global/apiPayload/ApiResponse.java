package week7.global.apiPayload;

import week7.global.apiPayload.code.BaseErrorCode;
import week7.global.apiPayload.code.BaseSuccessCode;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ApiResponse<T> {

    @JsonProperty("isSuccess")
    private final Boolean isSuccess;

    @JsonProperty("code")
    private final String code;

    @JsonProperty("message")
    private final String message;

    @JsonProperty("result")
    private T result;

    // 성공한 경우 (result 포함)
    public static <T> ApiResponse<T> onSuccess(BaseSuccessCode code, T result) {
        return new ApiResponse<>(true, code.getCode(), code.getMessage(), result);
    }

    // ⭐ 추가: 성공한 경우 (result 미포함)
    public static <Void> ApiResponse<Void> onSuccess(BaseSuccessCode code) {
        return new ApiResponse<>(true, code.getCode(), code.getMessage(), null);
    }

    // 실패한 경우 (result 포함)
    public static <T> ApiResponse<T> onFailure(BaseErrorCode code, T result) {
        return new ApiResponse<>(false, code.getCode(), code.getMessage(), result);
    }

    // 실패한 경우 (result 미포함) - GeneralExceptionAdvice에서 Void를 처리하기 위해 사용
    public static <T> ApiResponse<T> onFailure(BaseErrorCode code) {
        return new ApiResponse<>(false, code.getCode(), code.getMessage(), null);
    }
}