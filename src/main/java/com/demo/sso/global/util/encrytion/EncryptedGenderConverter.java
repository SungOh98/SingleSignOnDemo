package com.demo.sso.global.util.encrytion;

import com.demo.sso.domain.user.domain.Gender;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Converter
public class EncryptedGenderConverter implements AttributeConverter<Gender, String> {
    private final TwoWayEncryption twoWayEncryption;

    @Override
    public String convertToDatabaseColumn(Gender attribute) {
        if (attribute == null) {
            return null;
        }
        return twoWayEncryption.encrypt(attribute.toString());
    }

    @Override
    public Gender convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return Gender.valueOf(twoWayEncryption.decrypt(dbData));
    }
}
