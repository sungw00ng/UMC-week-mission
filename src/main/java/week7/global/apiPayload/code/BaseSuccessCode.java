package week7.global.apiPayload.code;

import org.springframework.http.HttpStatus; // 👈 import 추가

public interface BaseSuccessCode {

    // 🚩 추가: HTTP 상태 코드 (200, 201 등)를 반환하는 메서드
    HttpStatus getStatus();
    String getCode();
    String getMessage();
}