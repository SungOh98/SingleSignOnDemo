package com.demo.sso.global.util.encrytion;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Entity <-> DB 변환과정 중에 암/복호화를 해줄 Converter
 * 1. DB Insert
 * - Insert 전, Entity에 명시되어 있는 컬럼을 암호화한다.
 * 2. Entity Fetch
 * - Entity를 DB에서 꺼내온 후, Entity에 명시되어 있는 컬럼을 복호화한다.
 */
@RequiredArgsConstructor
@Component
@Converter
public class CryptoConverter implements AttributeConverter<String, String> {
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
        if (dbData == null) {
            return null;
        }
        return twoWayEncryption.decrypt(dbData);
    }
}
