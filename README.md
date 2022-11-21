# Meetup API
Test task for Modsen

## How to build
1. Build project: mvn clean install
2. To run environment: docker compose up

# Endpoints:

|   HTTP Method   | URL                                            | Description                     |
|:---------------:|------------------------------------------------|---------------------------------|
|      `GET`      | localhost/api/v1/meetup/                       | Get All Meetups                 |
|      `GET`      | localhost/api/v1/meetup/{id}                   | Get Meetup by ID                |
|     `POST`      | localhost/api/v1/meetup/                       | Create new Meetup               |
|      `PUT`      | localhost/api/v1/meetup/{id}/version/{version} | Update Meetup by ID and Version |
|    `DELETE`     | localhost/api/v1/meetup/{id}/version/{version} | Delete Meetup by ID and Version |

## Get All meetups

Request parameters (not required):

- topic - "abc"
- organizer - "abc"
- date_time - "2022-11-11T00:00:00" 
- sorting_field - "topic" / "organizer" / "date_time"
- sorting_type - "asc" / "desc"


*Request:*

`localhost/api/v1/meetup/`

*Response:*
```json
[
  {
    "id": 1,
    "topic": "one",
    "description": "one",
    "organizer": "one",
    "date_time": "2022-11-11T00:00:00",
    "place": "one",
    "version": 0
  },
  {
    "id": 2,
    "topic": "two",
    "description": "two",
    "organizer": "two",
    "date_time": "2022-11-11T00:00:00",
    "place": "two",
    "version": 0
  }
]
```
>200 OK
## Get Meetup by ID

*Request:*

`localhost/api/v1/meetup/1`

*Response:*
```json
{
  "id": 1,
  "topic": "one",
  "description": "one",
  "organizer": "one",
  "date_time": "2022-11-11T00:00:00",
  "place": "one",
  "version": 0
}

```
> 200 OK
## Create new Meetup

*Request:*

`localhost/api/v1/meetup/`
> 


```json
{
  "topic": "three",
  "description": "three",
  "organizer": "three",
  "date_time": "2022-11-17T00:00:00.000",
  "place": "three"
}
```

*Response:*
```json
  {
    "id": 3,
    "topic": "three",
    "description": "three",
    "organizer": "three",
    "date_time": "2022-11-11T00:00:00",
    "place": "three",
    "version": 0
  }
```
>201 Created
## Update Meetup by ID and Version
*Request:*

`localhost/api/v1/meetup/3/version/0`

```json
{
  "topic": "three_fix",
  "description": "three_fix",
  "organizer": "three_fix",
  "date_time": "2022-11-18T00:00:00.000",
  "place": "three_fix"
}
```

*Response:*
```json
{
  "id": 3,
  "topic": "three_fix",
  "description": "three_fix",
  "organizer": "three_fix",
  "date_time": "2022-11-18T00:00:00",
  "place": "three_fix",
  "version": 1
}
```
> 200 OK
## Delete Meetup by ID and Version
*Request:*

`localhost/api/v1/meetup/3/version/1`

*Response:*

> 204 No Content 

