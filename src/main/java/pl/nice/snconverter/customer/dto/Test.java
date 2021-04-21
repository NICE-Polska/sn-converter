package pl.nice.snconverter.customer.dto;

import pl.nice.snconverter.exception.unique.IUnique;

public class Test implements IUnique {
    @Override
    public boolean checkUnique(String columnName, String value) {
        return false;
    }
}
