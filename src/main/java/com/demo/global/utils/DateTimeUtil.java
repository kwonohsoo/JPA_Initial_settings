package com.demo.global.utils;

import com.querydsl.core.types.dsl.DateTemplate;
import com.querydsl.core.types.dsl.Expressions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    /**
     * param으로 받은 LocalDateTime타입의 값을 포맷팅 하여 리턴한다.
     *
     * @param localDateTime
     * @return
     */
    public static String LocalDateTimeToLocalTimeString(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    /**
     * param으로 받은 문자를 format에 맞게 변환 후 리턴한다.
     *
     * @param localDateTime
     * @param format        변환할 date format
     * @return format에 맞게 변환된 DateTemplate
     */
    public static DateTemplate<LocalDateTime> getDateTemplate(Object localDateTime, String format) {
        return Expressions.dateTemplate(LocalDateTime.class, "DATE_FORMAT({0}, {1})", localDateTime, format);
    }

}
