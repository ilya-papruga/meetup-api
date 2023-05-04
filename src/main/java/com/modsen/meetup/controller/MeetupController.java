package com.modsen.meetup.controller;

import com.modsen.meetup.dto.MeetupCreate;
import com.modsen.meetup.dto.MeetupRead;
import com.modsen.meetup.dto.MeetupUpdate;
import com.modsen.meetup.dto.filter.FilterString;
import com.modsen.meetup.service.api.MeetupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/api/v1/meetup")
public class MeetupController {

    private final MeetupService meetupService;

    public MeetupController(MeetupService meetupService) {
        this.meetupService = meetupService;
    }

    @GetMapping
    public ResponseEntity<List<MeetupRead>> getAllMeetups(@RequestParam(required = false) String topic,
                                                          @RequestParam(required = false) String organizer,
                                                          @RequestParam(required = false) String date_time,
                                                          @RequestParam(required = false) String sorting_field,
                                                          @RequestParam(required = false) String sorting_type) {

        return ResponseEntity.ok(meetupService.readAll(new FilterString(topic, organizer, date_time, sorting_field, sorting_type)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeetupRead> getOneMeetup(@PathVariable Long id) {

        return ResponseEntity.ok(meetupService.readOne(id));

    }

    @PostMapping
    public ResponseEntity<MeetupRead> createMeetup(@RequestBody MeetupCreate dto) {

        return new ResponseEntity<>((meetupService.create(dto)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/version/{version}")
    public ResponseEntity<MeetupRead> updateMeetup(@PathVariable Long id, @RequestBody MeetupUpdate dto, @PathVariable Long version) {

        meetupService.update(id, dto, version);

        return getOneMeetup(id);
    }

    @DeleteMapping("/{id}/version/{version}")
    public ResponseEntity<Void> deleteMeetup(@PathVariable Long id, @PathVariable Long version) {

        meetupService.delete(id, version);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
