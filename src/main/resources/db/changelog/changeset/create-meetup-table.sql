--liquibase formatted sql

--changeset ilya-papruga:1
CREATE TABLE meetup_api.meetup (
    uuid uuid NOT NULL,
    dt_create timestamp(3) without time zone,
    dt_update timestamp(3) without time zone,
    topic character varying,
    description text,
    organizer character varying,
    date_time timestamp(0) without time zone,
    place character varying
);

--changeset ilya-papruga:2
ALTER TABLE ONLY meetup_api.meetup
    ADD CONSTRAINT meetup_pkey PRIMARY KEY (uuid);