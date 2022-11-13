package com.modsen.meetup.service;

import com.modsen.meetup.dto.filter.Filter;
import com.modsen.meetup.repository.api.MeetupRepository;
import com.modsen.meetup.dto.MeetupCreate;
import com.modsen.meetup.dto.MeetupUpdate;
import com.modsen.meetup.entity.Meetup;
import com.modsen.meetup.service.api.MeetupService;
import com.modsen.meetup.validation.api.FilterValidation;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class MeetupServiceImpl implements MeetupService {

    private final MeetupRepository repository;
    private final ConversionService conversionService;
    private final FilterValidation filterValidation;

    public MeetupServiceImpl(MeetupRepository repository, ConversionService conversionService, FilterValidation filterValidation) {
        this.repository = repository;
        this.conversionService = conversionService;
        this.filterValidation = filterValidation;
    }

    public List<Meetup> readAll(Filter filter) {

        if (filterValidation.isEmpty(filter))
        {
            return repository.findAll();
        }
        return repository.findAllSorted(filter);
    }

    public Meetup readOne(UUID uuid) {

        return this.repository.findOne(uuid);

    }

    public Meetup create(MeetupCreate dto) {

        return repository.createNew(conversionService.convert(dto, Meetup.class));

    }

    public Meetup update(UUID uuid, MeetupUpdate dto, LocalDateTime dtUpdate) {

        readOne(uuid);

        repository.updateByUuid(uuid, dto, dtUpdate);

        return readOne(uuid);

    }

    public void delete(UUID uuid, LocalDateTime dtUpdate) {

        readOne(uuid);

        repository.deleteByUuid(uuid, dtUpdate);

    }
}
