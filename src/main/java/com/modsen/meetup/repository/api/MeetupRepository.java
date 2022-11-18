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
    Meetup findOne(Long id);
    Meetup createNew (Meetup meetup);
    void updateByUuid (Long id, MeetupUpdate dto, Long version);
    void deleteByUuid (Long id, Long version);

}
