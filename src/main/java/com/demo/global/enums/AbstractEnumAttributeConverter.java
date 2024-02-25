package com.demo.global.enums;


import com.demo.global.error.code.ErrorCode;
import com.demo.global.error.exception.EnumMappingException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class AbstractEnumAttributeConverter<E extends Enum<E> & EnumType> implements AttributeConverter<E, Integer> {

    private final Class<E> targetEnumClass;

    private final ErrorCode toDatabaseCommonErrorCode;
    private final ErrorCode toEntityCode;

    private final boolean nullable;

    public AbstractEnumAttributeConverter(Class<E> enumClass, ErrorCode toDatabaseCommonErrorCode, ErrorCode toEntityCode, boolean nullable) {
        this.targetEnumClass = enumClass;
        this.toDatabaseCommonErrorCode = toDatabaseCommonErrorCode;
        this.toEntityCode = toEntityCode;
        this.nullable = nullable;
    }

    /**
     * Enum으로 받은 값을 코드로 변환
     *
     * @param attribute Enum 정보
     */
    @Override
    public Integer convertToDatabaseColumn(E attribute) {
        if (!nullable && attribute == null) throw new EnumMappingException(toDatabaseCommonErrorCode);
        return EnumValueConverterUtils.toCode(attribute);
    }

    /**
     * DB에 저장된 매핑 코드값을 이름으로 변경
     *
     * @param dbData db 상수 값
     */
    @Override
    public E convertToEntityAttribute(Integer dbData) {
        if (!nullable && dbData == null) {
            throw new EnumMappingException(toEntityCode);
        }
        return EnumValueConverterUtils.findByCode(targetEnumClass, dbData);
    }
}
