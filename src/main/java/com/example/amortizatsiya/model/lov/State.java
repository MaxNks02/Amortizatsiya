package com.example.amortizatsiya.model.lov;

import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

public enum State {
    ENTERED("Проведен"),
    VALIDATED("Утверждён"),
    DELETED("Удален");

    public final String value;


    public static State create(String value) {
        for (State offState : values()) {
            if (value.equals(offState.getValue())) {
                return offState;
            }
        }
        return null;
    }


    State(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Converter(autoApply = true)
    public static class StateConverter implements AttributeConverter<State, String> {

        @Override
        public String convertToDatabaseColumn(State attribute) {
            return attribute.getValue();
        }

        @Override
        public State convertToEntityAttribute(String dbData) {
            return create(dbData);
        }
    }
}
