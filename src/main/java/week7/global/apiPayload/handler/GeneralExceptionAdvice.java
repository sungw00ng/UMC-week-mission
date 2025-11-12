package week7.global.apiPayload.handler;

import week7.global.apiPayload.ApiResponse;
import week7.global.apiPayload.code.BaseErrorCode;
import week7.global.apiPayload.code.GeneralErrorCode;
import week7.global.apiPayload.exception.GeneralException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException; // ✅ Import
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap; // ✅ Import
import java.util.Map;   // ✅ Import

@RestControllerAdvice
public class GeneralExceptionAdvice {

    // 1. 커스텀 예외 처리 (GeneralException 상속 예외)
    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(
            GeneralException ex
    ) {

        return ResponseEntity.status(ex.getCode().getStatus())
                .body(ApiResponse.onFailure(
                                ex.getCode()
                        )
                );
    }

    /**
     * ✅ 2. DTO 유효성 검증 실패 예외 처리 (@Valid)
     * MethodArgumentNotValidException을 처리하여 VALID_FAIL 응답 반환
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex
    ) {
        // 검사에 실패한 필드와 그에 대한 메시지를 저장하는 Map
        Map<String, String> errors = new HashMap<>();

        // 모든 에러 필드와 메시지를 추출하여 맵에 담습니다.
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        GeneralErrorCode code = GeneralErrorCode.VALID_FAIL;

        // GeneralErrorCode.VALID_FAIL과 상세 에러 목록(errors)을 담아 응답 생성
        ApiResponse<Map<String, String>> errorResponse = ApiResponse.onFailure(code, errors);

        return ResponseEntity.status(code.getStatus()).body(errorResponse);
    }

    // 3. 그 외의 정의되지 않은 모든 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(
            Exception ex
    ) {

        BaseErrorCode code = GeneralErrorCode.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(code.getStatus())
                .body(ApiResponse.onFailure(
                                code,
                                ex.getMessage()
                        )
                );
    }
}