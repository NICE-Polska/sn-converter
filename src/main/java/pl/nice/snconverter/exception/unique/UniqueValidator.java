package pl.nice.snconverter.exception.unique;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UniqueValidator implements ConstraintValidator<Unique, String> {
    private String columnName;
    private Class<?> aClass;
    private final ApplicationContext applicationContext;

    @Override
    public void initialize(Unique constraintAnnotation) {
        this.columnName = constraintAnnotation.columnName();
        this.aClass = constraintAnnotation.clazz();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Object object = applicationContext.getAutowireCapableBeanFactory().createBean(aClass);
        IUnique iUnique = (IUnique) object;
        return iUnique.checkUnique(columnName, value);
    }
}
