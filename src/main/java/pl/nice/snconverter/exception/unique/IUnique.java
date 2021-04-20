package pl.nice.snconverter.exception.unique;

public interface IUnique<T> {
    boolean checkUnique(T t);
}
