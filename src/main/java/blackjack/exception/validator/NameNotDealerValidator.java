package blackjack.exception.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameNotDealerValidator implements ConstraintValidator<NameNotDealer, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !"딜러".equals(value);
    }
}
