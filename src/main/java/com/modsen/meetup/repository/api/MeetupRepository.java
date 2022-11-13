package com.modsen.meetup.repository.api;

import com.modsen.meetup.dto.filter.Filter;
import com.modsen.meetup.dto.MeetupUpdate;
import com.modsen.meetup.entity.Meetup;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface MeetupRepository {

    List<Meetup> findAll ();

    List<Meetup> findAllSorted(Filter filter);
    Meetup findOne(UUID uuid);
    Meetup createNew (Meetup meetup);
    void updateByUuid (UUID uuid, MeetupUpdate dto, LocalDateTime dtUpdate);
    void deleteByUuid (UUID uuid, LocalDateTime dtUpdate);

}
