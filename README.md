// README.md

# Mobilise Book API

### Introduction

A Simple Book management system. This system allows users sign up to come read books, however authors are created and also books 
are the attached to different authors.

### Project Support Features

- Users can signup and login to their accounts
- Authors can sign up and sign in as well
- Public (non-authenticated) users can access all some several resourses
- Authenticated admin has the ability to perform all operations

### Installation Guide

- Clone this repository [here](https://github.dev/yoyoplenty/mobilise-book-api).
- The main branch is the most stable branch at any given time, ensure you're working from it.
- Run mvn clean install
- You are to work with postgresql database, do well to configure the application.properties file.
- run mvn spring-boot:run to start your application.


### Usage

- Run mvn spring-boot:run to start application.
- Head to your post man with configure PORT/api/v1/books to get all books and test if your application is running.

### API Endpoints
// prettier-ignore
| HTTP Verbs | Endpoints                          |     Action                                 |
| ---------- | ---------------------------------- | --------------------------------------     |
| POST       | /api/v1/permissions                | To create a new permission                 |
| GET        | /api/v1/permissions                | To get all avaiable permissions            |
| GET        | /api/v1/permissions/:id            | To get a single permission by id           |
| PATCH      | /api/v1/permissions/:id            | To update a single permission by id        |
| DELETE     | /api/v1/permissions/:id            | To delete a single permission by id        |
| POST       | /api/v1/roles                      | To create a new role                       |
| GET        | /api/v1/roles                      | To get all avaiable role                   |
| GET        | /api/v1/roles/:id                  | To get a single role by id                 |
| PATCH      | /api/v1/roles/:id                  | To update a single role by id              |
| DELETE     | /api/v1/roles/:id                  | To delete a single role by id              |
| POST       | /api/v1/auth/signup                | To sign up a new user account              |
| GET        | /api/v1/auth/confirm_mail/:token   | To confirm up a new user account email     |
| POST       | /api/v1/auth/signin                | To login an existing user account          |
| POST       | /api/v1/auth/forgot_password       | To get a reset password for user account   |
| POST       | /api/v1/auth/reset_password/:token | To reset a user password                   |
| GET        | /api/v1/auth/resend_mail/:email    | To resend a user acct confirmation email   |
| GET        | /api/v1/users                      | To get all avaiable users                  |
| GET        | /api/v1/users/:id                  | To get a single user by id                 |
| PATCH      | /api/v1/users/:id                  | To update a single user details by id      |
|            |                                    | also users can upload thier profile        |
|            |                                    | image and change existing password         |
| DELETE     | /api/v1/users/:id                  | To delete a single user by id              |


### Technologies Used

- [JAVA JDK]() This is a java runtime environment that allows java run on our machine.
- [Spring Boot]() This is a Spring generated framework.
- [Spring Data JPA]() A Object relational mapper used with the Java Persistent API.
- [PostGRESQL](https://postgresql.org/)

### Authors

- [yoyoplenty](https://github.com/yoyoplenty)


### License

This project is available for use under the MIT License.
