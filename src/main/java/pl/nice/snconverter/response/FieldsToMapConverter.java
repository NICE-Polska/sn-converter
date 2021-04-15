package pl.nice.snconverter.response;

import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class FieldsToMapConverter<T> {

    public Map<String, Object> getFieldsAsMap(T t) {
        Class<?> c = t.getClass();
        Map<String, Object> fields = new LinkedHashMap<>();

        Arrays.stream(c.getDeclaredFields())
                .filter(field -> {
                    field.setAccessible(true);
                    try {
                        return field.get(t) != null;
                    } catch (IllegalAccessException ex) {
                        ex.printStackTrace();
                    }
                    return false;
                })
                .forEach(field -> {
                    try {
                        field.setAccessible(true);
                        fields.put(field.getName(), field.get(t));
                        field.setAccessible(false);
                    } catch (IllegalAccessException ex) {
                        ex.printStackTrace();
                    }
                });

        return fields;
    }

    public Map<String, Object> getFieldsAsMapAll(T t) {
        Class<?> c = t.getClass();
        Map<String, Object> fields = new LinkedHashMap<>();

        Arrays.stream(c.getDeclaredFields())
                .forEach(field -> {
                    field.setAccessible(true);
                    try {
                        fields.put(field.getName(), field.get(t));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    field.setAccessible(false);
                });
        return fields;
    }
}
