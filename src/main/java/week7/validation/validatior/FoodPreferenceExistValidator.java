package week7.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import week7.domain.repository.FoodRepository; // FoodRepository가 있다고 가정
import week7.global.apiPayload.code.GeneralErrorCode;
import week7.validation.annotation.ExistFoodPreferences;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FoodPreferenceExistValidator implements ConstraintValidator<ExistFoodPreferences, List<Long>> {

    // FoodRepository를 주입받아 DB 검증 로직을 수행합니다.
    private final FoodRepository foodRepository;

    @Override
    public boolean isValid(List<Long> values, ConstraintValidatorContext context) {
        if (values == null || values.isEmpty()) {
            return true;
        }

        // DTO로 넘어온 모든 Food ID가 DB에 존재하는지 확인
        boolean isValid = values.stream()
                .allMatch(foodRepository::existsById);

        if (!isValid) {
            // Validation 실패 시, 응답 메시지를 GeneralErrorCode의 메시지로 변경
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(GeneralErrorCode.NOT_FOUND.getMessage())
                    .addConstraintViolation();
        }

        return isValid;
    }
}