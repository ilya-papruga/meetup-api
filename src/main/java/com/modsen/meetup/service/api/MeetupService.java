package com.modsen.meetup.service.api;

import com.modsen.meetup.dto.MeetupUpdate;
import com.modsen.meetup.entity.Meetup;
import com.modsen.meetup.dto.MeetupCreate;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Validated
public interface MeetupService {

    List<Meetup> readAll ();
    Meetup readOne (UUID uuid);
    Meetup create (@Valid MeetupCreate dto);
    Meetup update (UUID uuid, @Valid MeetupUpdate dto, LocalDateTime dtUpdate);
    void delete (UUID uuid, LocalDateTime dtUpdate);

}
