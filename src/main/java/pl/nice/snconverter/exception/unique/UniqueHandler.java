package pl.nice.snconverter.exception.unique;

public interface UniqueHandler {
    boolean isUnique(String fieldName, String value);

    boolean isUnique(String fieldName, String value, Long id);
}
