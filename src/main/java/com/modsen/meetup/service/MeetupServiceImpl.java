package com.modsen.meetup.service;

import com.modsen.meetup.dto.MeetupRead;
import com.modsen.meetup.dto.filter.Filter;
import com.modsen.meetup.dto.filter.FilterString;
import com.modsen.meetup.repository.api.MeetupRepository;
import com.modsen.meetup.dto.MeetupCreate;
import com.modsen.meetup.dto.MeetupUpdate;
import com.modsen.meetup.entity.Meetup;
import com.modsen.meetup.service.api.MeetupService;
import com.modsen.meetup.validation.api.FilterValidation;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<MeetupRead> readAll(FilterString filter) {

        if (filterValidation.isEmpty(filter)) {
            return repository.findAll().stream()
                    .map(meetup -> conversionService.convert(meetup, MeetupRead.class))
                    .collect(Collectors.toList());
        }

        return repository.findAllFiltered(conversionService.convert(filter, Filter.class)).stream()
                .map(meetup -> conversionService.convert(meetup, MeetupRead.class))
                .collect(Collectors.toList());
    }

    public MeetupRead readOne(Long id) {

        return conversionService.convert(repository.findOne(id), MeetupRead.class);

    }

    public MeetupRead create(MeetupCreate dto) {

        return conversionService.convert(repository.createNew(conversionService.convert(dto, Meetup.class)), MeetupRead.class);

    }

    public MeetupRead update(Long id, MeetupUpdate dto, Long version) {

        readOne(id);
        repository.updateByUuid(id, dto, version);
        return readOne(id);

    }

    public void delete(Long id, Long version) {

        readOne(id);
        repository.deleteByUuid(id, version);

    }
}
