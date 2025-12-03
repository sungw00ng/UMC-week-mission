package week7.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import week7.validation.validator.FoodPreferenceExistValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FoodPreferenceExistValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistFoodPreferences {

    String message() default "해당하는 선호 음식이 존재하지 않습니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}