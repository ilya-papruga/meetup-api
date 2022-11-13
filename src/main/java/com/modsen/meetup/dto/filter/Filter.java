package com.modsen.meetup.dto.filter;

import java.time.LocalDateTime;

public class Filter {

    private String topic;
    private String organizer;
    private LocalDateTime dateTime;
    private String sortingField;
    private String sortingType;

    public Filter() {
    }

    public Filter(String topic, String organizer, LocalDateTime dateTime, String sortingField, String sortingType) {
        this.topic = topic;
        this.organizer = organizer;
        this.dateTime = dateTime;
        this.sortingField = sortingField;
        this.sortingType = sortingType;
    }


    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
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

    public String getSortingField() {
        return sortingField;
    }

    public void setSortingField(String sortingField) {
        this.sortingField = sortingField;
    }

    public String getSortingType() {
        return sortingType;
    }

    public void setSortingType(String sortingType) {
        this.sortingType = sortingType;
    }
}
