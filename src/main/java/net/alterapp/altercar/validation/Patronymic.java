package net.alterapp.altercar.validation;

import net.alterapp.altercar.validation.validator.PatronymicValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PatronymicValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Patronymic {

  String message() default "{validation.patronymic}";

  int min() default 0;

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
