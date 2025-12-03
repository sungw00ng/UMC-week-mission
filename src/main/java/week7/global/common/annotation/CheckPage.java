package week7.global.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 프론트엔드에서 전달하는 1-based page number를 받기 위한 커스텀 어노테이션.
 * 쿼리 스트링 'page' 값을 받으며, 값의 유효성 (page >= 1)을 검증합니다.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckPage {
    // page 쿼리 파라미터의 이름을 명시
    String value() default "page";
}