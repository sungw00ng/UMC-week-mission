package week7.domain.test.controller;

import week7.domain.test.converter.TestConverter;
import week7.domain.test.dto.res.TestResDTO;
import week7.domain.test.service.query.TestQueryService;
import week7.global.apiPayload.ApiResponse;
import week7.global.apiPayload.code.CommonSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/temp")
public class TestController {

    private final TestQueryService testQueryService;

    @GetMapping("/test")
    public ApiResponse<TestResDTO.Testing> test() {
        // 응답 코드 정의
        CommonSuccessCode code = CommonSuccessCode._OK;

        return ApiResponse.onSuccess(
                code,
                TestConverter.toTestingDTO("This is Test!")
        );
    }

    // 예외 상황
    @GetMapping("/exception")
    public ApiResponse<TestResDTO.Exception> exception(
            @RequestParam(required = false) Long flag
    ) {

        testQueryService.checkFlag(flag);

        // 예외가 발생하지 않으면 정상 응답
        CommonSuccessCode code = CommonSuccessCode._OK;
        return ApiResponse.onSuccess(code, TestConverter.toExceptionDTO("Exception Check Passed!"));
    }
}