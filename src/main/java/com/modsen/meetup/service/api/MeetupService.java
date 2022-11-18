package com.modsen.meetup.service.api;

import com.modsen.meetup.dto.MeetupRead;
import com.modsen.meetup.dto.MeetupUpdate;
import com.modsen.meetup.dto.filter.FilterString;
import com.modsen.meetup.dto.MeetupCreate;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface MeetupService {

    List<MeetupRead> readAll (FilterString filter);
    MeetupRead readOne (Long id);
    MeetupRead create (@Valid MeetupCreate dto);
    MeetupRead update (Long id, @Valid MeetupUpdate dto, Long version);
    void delete (Long id, Long version);

}
