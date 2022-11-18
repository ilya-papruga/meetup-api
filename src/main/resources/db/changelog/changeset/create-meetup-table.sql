--liquibase formatted sql

--changeset ilya-papruga:1
CREATE SEQUENCE meetup_api.meetup_id_seq
        START WITH 1
        INCREMENT BY 1
        NO MINVALUE
        NO MAXVALUE
        CACHE 1;

--changeset ilya-papruga:2
CREATE TABLE meetup_api.meetup (
        id bigint DEFAULT nextval('meetup_api.meetup_id_seq'::regclass) NOT NULL,
        topic character varying,
        description text,
        organizer character varying,
        date_time timestamp without time zone,
        place character varying,
        version bigint
    );