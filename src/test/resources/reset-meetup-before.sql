
DELETE FROM meetup_api.meetup;

ALTER SEQUENCE meetup_api.meetup_id_seq restart with 1;

INSERT INTO meetup_api.meetup(
	topic, description, organizer, date_time, place, version)
	VALUES ('test1', 'test1', 'test1', now(), 'test1', 0);
INSERT INTO meetup_api.meetup(
	topic, description, organizer, date_time, place, version)
	VALUES ('test2', 'test2', 'test2', now(), 'test2', 0);