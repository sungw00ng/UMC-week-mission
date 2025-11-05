package week7.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SignUpResultDTO {
    private Long userId;
    // 추가적인 반환 정보 생략
}