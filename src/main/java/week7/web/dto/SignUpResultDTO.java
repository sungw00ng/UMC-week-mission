package week7.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime; // LocalDateTime 임포트 추가

@Getter
@Builder
@AllArgsConstructor
public class SignUpResultDTO {
    private Long userId;
    // UserConverter에서 빌드하는 데 필요하여 추가
    private LocalDateTime createdAt;
    // 추가적인 반환 정보 생략
}