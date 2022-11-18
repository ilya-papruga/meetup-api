package com.modsen.meetup.convertion;

import com.modsen.meetup.dto.MeetupRead;
import com.modsen.meetup.entity.Meetup;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MeetupToMeetupReadConverter implements Converter<Meetup, MeetupRead> {

    @Override
    public MeetupRead convert(Meetup entity) {

        MeetupRead dto = new MeetupRead();

        dto.setId(entity.getId());
        dto.setTopic(entity.getTopic());
        dto.setDescription(entity.getDescription());
        dto.setOrganizer(entity.getOrganizer());
        dto.setDateTime(entity.getDateTime());
        dto.setPlace(entity.getPlace());
        dto.setVersion(entity.getVersion());

        return dto;

    }
}
