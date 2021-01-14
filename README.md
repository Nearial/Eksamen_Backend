# Exam

[![Build Status](https://travis-ci.com/Nearial/Eksamen_Backend.svg?branch=master)](https://travis-ci.com/Nearial/Eksamen_Backend)

## Backend

This is a Exam project for [Computer Science AP](https://www.cphbusiness.dk/uddannelser/erhvervsakademiuddannelser/datamatiker) students in the 3rd semester at [Copenhagen Business Academy](https://www.cphbusiness.dk/).

## Frontend

Are you interested in the frontend for this project? Then you can find it [here](https://github.com/Nearial/Eksamen_Frontend).

# Documentation

To see all the documentation about a specific endpoint, access the documentation by clicking the link next to the endpoint in question.

## Open Endpoints

Open endpoints require no Authentication.

* [Login](docs/auth/login.md) : `POST /api/auth/login/`
* [Register](docs/auth/register.md) : `POST / api/auth/register/`

## Closed Endpoints

Closed endpoints require a valid Token to be included in the header of the request. A Token can be acquired from the open endpoints above.

The token must be provided under header-key `x-access-token`.

### User related

* [Get all users](docs/info/allUsers.md) : `GET /api/info/allUsers/`
* [Get info about user by username](docs/info/infoByUsername.md) : `GET /api/info/{username}/`

### Joke related

* [Get random jokes](docs/fun/jokes.md) : `GET /api/fun/jokes/`
