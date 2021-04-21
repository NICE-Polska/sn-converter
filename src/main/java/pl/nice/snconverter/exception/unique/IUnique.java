package pl.nice.snconverter.exception.unique;

public interface IUnique {
    boolean checkUnique(String columnName, String value);
}
