package net.alterapp.altercar.validation.validator;

import net.alterapp.altercar.validation.Patronymic;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PatronymicValidator implements ConstraintValidator<Patronymic, String> {

  private Patronymic constraint;

  @Override
  public void initialize(Patronymic constraintAnnotation) {
    this.constraint = constraintAnnotation;
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null || value.isBlank()) {
      return true;
    }
    return value.length() >= constraint.min();
  }

}
