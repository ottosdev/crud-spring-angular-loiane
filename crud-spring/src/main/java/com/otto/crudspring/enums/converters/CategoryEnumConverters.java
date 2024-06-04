package com.otto.crudspring.enums.converters;

import com.otto.crudspring.enums.CategoryEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class CategoryEnumConverters implements AttributeConverter<CategoryEnum, String> {
    @Override
    public String convertToDatabaseColumn(CategoryEnum categoryEnum) {
        if (categoryEnum == null) {
            return null;
        }
        return categoryEnum.getValue();
    }

    @Override
    public CategoryEnum convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }
        return Stream.of(CategoryEnum.values())
                .filter(c -> c.getValue().equals(value))
                .findFirst().orElseThrow(IllegalArgumentException::new);
    }
}
