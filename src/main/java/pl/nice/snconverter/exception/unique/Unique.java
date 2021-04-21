package pl.nice.snconverter.exception.unique;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = UniqueValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Unique {
    String columnName();
    Class<?> clazz();
    String message() default "{unique.error.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
