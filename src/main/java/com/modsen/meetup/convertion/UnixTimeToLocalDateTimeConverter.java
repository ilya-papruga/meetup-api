package com.modsen.meetup.convertion;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class UnixTimeToLocalDateTimeConverter implements Converter <Long, LocalDateTime> {
    @Override
    public LocalDateTime convert(Long source) {

        if (source == null)
        {
            return null;
        }

        return LocalDateTime.ofInstant(Instant.ofEpochMilli(source), ZoneId.systemDefault());
    }
}
