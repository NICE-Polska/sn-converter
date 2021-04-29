package pl.nice.snconverter.exception.unique;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UniqueValidator implements ConstraintValidator<Unique, String> {
    private String fieldName;
    private Class<?> handler;
    private Operation operation;
    private final ApplicationContext applicationContext;
    private Object o;

    @Override
    public void initialize(Unique constraintAnnotation) {
        this.fieldName = constraintAnnotation.fieldName();
        this.handler = constraintAnnotation.handler();
        this.operation = constraintAnnotation.operation();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Object object = applicationContext.getAutowireCapableBeanFactory().createBean(handler);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest();
        var uniqueHandler = (UniqueHandler) object;

        if(operation == Operation.UPDATE) {
            String[] tmps = uri.toUriString().split("/"); //TODO zastanowic sie nad tym - sprobowac zrobic to bardziej generycznie
            Long id = Long.parseLong(tmps[tmps.length - 1]);
            return uniqueHandler.isUnique(fieldName, value, id);
        }

        return uniqueHandler.isUnique(fieldName, value);
    }
}
