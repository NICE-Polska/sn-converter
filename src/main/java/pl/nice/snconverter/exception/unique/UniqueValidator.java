package pl.nice.snconverter.exception.unique;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueValidator implements ConstraintValidator<Unique, String> {
    @Override
    public void initialize(Unique constraintAnnotation) {
    }
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //return value.startsWith("A");
        return true;
    }
}
