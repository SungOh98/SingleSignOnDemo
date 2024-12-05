package com.demo.sso.global.util.encrytion;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Converter
public class OnlyEncryptionConverter  implements AttributeConverter<String, String> {
    private final TwoWayEncryption twoWayEncryption;
    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null) {
            return null;
        }
        return twoWayEncryption.encrypt(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return dbData;
    }
}
