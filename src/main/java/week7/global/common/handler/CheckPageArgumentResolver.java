package week7.global.common.handler;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import week7.global.apiPayload.code.status.PageErrorCode; // 신규 에러 코드 임포트
import week7.global.common.annotation.CheckPage;
import week7.global.apiPayload.exception.GeneralException;
import week7.global.apiPayload.code.GeneralErrorCode;

public class CheckPageArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CheckPage.class) && parameter.getParameterType().equals(Integer.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        CheckPage checkPageAnnotation = parameter.getParameterAnnotation(CheckPage.class);
        String pageParameterName = checkPageAnnotation.value();
        String pageStr = webRequest.getParameter(pageParameterName);

        if (pageStr == null || pageStr.isEmpty()) {
            // 값이 없는 경우, 기본값 1로 가정하고 0-based page로 변환 (1 -> 0)
            return 0;
        }

        int page;
        try {
            page = Integer.parseInt(pageStr);
        } catch (NumberFormatException e) {
            // 숫자가 아닌 경우 400 에러
            throw new GeneralException(GeneralErrorCode.BAD_REQUEST);
        }

        // 1. 값 유효성 검증 (page >= 1) -> page <= 0 이면 에러 발생
        if (page <= 0) {
            throw new GeneralException(PageErrorCode.PAGE_NUMBER_TOO_SMALL);
        }

        // 2. Spring Data JPA의 0-based page로 변환 (page - 1)
        return page - 1;
    }
}