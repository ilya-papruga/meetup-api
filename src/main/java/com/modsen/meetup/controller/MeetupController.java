package com.modsen.meetup.controller;

import com.modsen.meetup.dto.MeetupCreate;
import com.modsen.meetup.dto.MeetupRead;
import com.modsen.meetup.dto.MeetupUpdate;
import com.modsen.meetup.service.api.MeetupService;
import com.modsen.meetup.validation.api.PathVariableValidator;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/meetup")
public class MeetupController {

    private final MeetupService meetupService;
    private final ConversionService conversionService;
    private final PathVariableValidator validator;


    public MeetupController(MeetupService meetupService, ConversionService conversionService, PathVariableValidator validator) {
        this.meetupService = meetupService;
        this.conversionService = conversionService;
        this.validator = validator;
    }

    @GetMapping
    public ResponseEntity<List<MeetupRead>> getAllMeetups() {

        return ResponseEntity.ok(meetupService.readAll().stream()
                        .map(meetup -> conversionService.convert(meetup, MeetupRead.class))
                        .collect(Collectors.toList()));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<MeetupRead> getOneMeetup(@PathVariable String uuid) {

        UUID validUUID = validator.validUUID(uuid);

        return ResponseEntity.ok(conversionService.convert(meetupService.readOne(validUUID), MeetupRead.class));

    }

    @PostMapping
    public ResponseEntity<MeetupRead> createMeetup(@RequestBody MeetupCreate dto) {
        return new ResponseEntity<>(conversionService.convert((meetupService.create(dto)), MeetupRead.class), HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<MeetupRead> updateMeetup(@PathVariable String uuid, @RequestBody MeetupUpdate dto, @PathVariable Long dt_update) {
        UUID validUUID = validator.validUUID(uuid);
        validator.validUnixTime(dt_update);

        LocalDateTime lastKnowDtUpdate = LocalDateTime.ofInstant(Instant.ofEpochMilli(dt_update), ZoneId.systemDefault());

        meetupService.update(validUUID, dto, lastKnowDtUpdate);

        return getOneMeetup(uuid);

    }

    @DeleteMapping("/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<Void> deleteMeetup(@PathVariable String uuid, @PathVariable Long dt_update) {

        UUID validUUID = validator.validUUID(uuid);
        validator.validUnixTime(dt_update);

        LocalDateTime lastKnowDtUpdate = LocalDateTime.ofInstant(Instant.ofEpochMilli(dt_update), ZoneId.systemDefault());

        meetupService.delete(validUUID, lastKnowDtUpdate);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }


}
