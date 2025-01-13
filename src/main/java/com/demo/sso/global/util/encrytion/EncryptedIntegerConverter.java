package com.demo.sso.global.util.encrytion;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Converter
public class EncryptedIntegerConverter implements AttributeConverter<Integer, String> {
    private final TwoWayEncryption twoWayEncryption;

    @Override
    public String convertToDatabaseColumn(Integer attribute) {
        if (attribute == null) {
            return null;
        }
        return twoWayEncryption.encrypt(String.valueOf(attribute));
    }

    @Override
    public Integer convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return Integer.parseInt(twoWayEncryption.decrypt(dbData));
    }
}
