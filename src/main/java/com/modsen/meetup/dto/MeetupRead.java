package com.modsen.meetup.dto;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;


public class MeetupRead {

    private Long id;
    private String topic;
    private String description;
    private String organizer;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime dateTime;
    private String place;

    private Long version;

    public MeetupRead() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
