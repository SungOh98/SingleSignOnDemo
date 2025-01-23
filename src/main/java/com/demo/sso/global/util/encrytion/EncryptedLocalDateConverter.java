package com.demo.sso.global.util.encrytion;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Component
@Converter
public class EncryptedLocalDateConverter implements AttributeConverter<LocalDate, String> {
    private final TwoWayEncryption twoWayEncryption;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public String convertToDatabaseColumn(LocalDate attribute) {
        if (attribute == null) {
            return null;
        }
        return twoWayEncryption.encrypt(String.valueOf(attribute));
    }

    @Override
    public LocalDate convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return LocalDate.parse(twoWayEncryption.decrypt(dbData), FORMATTER);
    }
}
