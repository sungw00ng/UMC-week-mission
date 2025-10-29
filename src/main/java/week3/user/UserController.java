package week3.user;

import week3.common.CommonResponse;
import week3.user.dto.UserSignUpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
// import jakarta.validation.Valid; // Validation 사용 시

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    // 1. 회원 가입 API (POST /api/v1/users/signup)
    @PostMapping("/signup")
    public ResponseEntity<CommonResponse<Long>> signUp(
            //@Valid @RequestBody UserSignUpRequest request) { // Validation 적용
            @RequestBody UserSignUpRequest request) {

        // ❗ Service 로직 구현 필요: 데이터 유효성 검사, 비밀번호 암호화, DB 저장
        Long dummyUserId = 1L;

        return ResponseEntity
                .status(HttpStatus.CREATED) // HTTP 201 Created
                .body(CommonResponse.onSuccess(dummyUserId));
    }
}