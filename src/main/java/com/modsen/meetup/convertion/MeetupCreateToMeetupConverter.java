package com.modsen.meetup.convertion;

import com.modsen.meetup.dto.MeetupCreate;
import com.modsen.meetup.entity.Meetup;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class MeetupCreateToMeetupConverter implements Converter <MeetupCreate, Meetup> {

    @Override
    public Meetup convert(MeetupCreate dto) {

        Meetup entity = new Meetup();

        entity.setTopic(dto.getTopic());
        entity.setDescription(dto.getDescription());
        entity.setOrganizer(dto.getOrganizer());
        entity.setDateTime(dto.getDateTime());
        entity.setPlace(dto.getPlace());

        return entity;

    }
}
