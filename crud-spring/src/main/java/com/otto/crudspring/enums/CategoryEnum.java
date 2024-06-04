package com.otto.crudspring.enums;

public enum CategoryEnum {
    BACKEND("Back-end"), FRONTEND("Front-end");

    private String value;

    private CategoryEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "CategoryEnum{" +
                "value='" + value + '\'' +
                '}';
    }
}
