package blackjack.exception.validator;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NameNotDealerValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NameNotDealer {
    String message() default "플레이어 이름이 딜러일 수 없습니다.";

    Class[] groups() default {};

    Class[] payload() default {};

}
