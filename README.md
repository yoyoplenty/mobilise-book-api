// README.md

# Mobilise Book API

### Introduction

A Simple Book management system. This system allows users sign up to come read books, however authors are created and also books 
are the attached to different authors.

### Project Support Features

- Users can signup and login to their accounts
- Authors can sign up and sign in as well
- Public (non-authenticated) users can access all some several resources
- Authenticated admin has the ability to perform all operations and mostly admin creates Authors and books

### Installation Guide

- Clone this repository [here](https://github.dev/yoyoplenty/mobilise-book-api).
- The main branch is the most stable branch at any given time, ensure you're working from it.
- Run mvn clean install
- You are to work with postgresql database, do well to configure the application.properties file.
- run mvn spring-boot:run to start your application.


### Usage

- Run mvn spring-boot:run to start application.
- Head to your post man with configure PORT/api/v1/books to get all books and test if your application is running.

### Test

- Run mvn test, to run all test file available in the application.

### API Endpoints
// prettier-ignore
| HTTP Verbs | Endpoints                          |     Action                                 |
| ---------- | ---------------------------------- | --------------------------------------     |
| POST       | /api/v1/auth/register              | To sign up as a guest user                 |
| POST       | /api/v1/auth/login                 | To sign in as a guest user                 |
| GET        | /api/v1/auth/verify-email/:token   | To verify signed up user email address     |
| GET        | /api/v1/auth/resend-email/:email   | To resend verification email               |
| GET        | /api/v1/auth/forget-password/:email| To implement forget password               |
| PATCH      | /api/v1/auth/reset-password        | To reset users password                    |
| GET        | /api/v1/users                      | To get all available users                 |
| GET        | /api/v1/users/:id                  | To get a single user by id                 |
| PATCH      | /api/v1/users/:id                  | To update a single user details by id      |
| DELETE     | /api/v1/users/:id                  | To delete a single user by id              |
| GET        | /api/v1/books                      | To get all books (paginated)               |
| POST       | /api/v1/books                      | To create a new book                       |
| GET        | /api/v1/books/:id                  | To get a single book by id                 |
| PATCH      | /api/v1/books/:id                  | To update a single book by id              |
| GET        | /api/v1/books/authors/:authorId     | To get all authors who wrote a book        |
| DELETE     | /api/v1/books/:id                  | To delete a book   user by id              |
| GET        | /api/v1/authors                    | To get all authors                         |
| POST       | /api/v1/authors                    | To create a new author(by Admin)           |
| GET        | /api/v1/authors/:id                | To get a single author by id               |
| PATCH      | /api/v1/authors/:id                | To update a single author by id            |
| GET        | /api/v1/books/authors/:authorId     | To get all books written by a author       |
| DELETE     | /api/v1/authors/:id                | To delete a single author by id            |


### Technologies Used

- [JAVA JDK]() This is a java runtime environment that allows java run on our machine.
- [Spring Boot]() This is a Spring generated framework.
- [Spring Data JPA]() A Object relational mapper used with the Java Persistent API.
- [PostGRESQL](https://postgresql.org/)

### Authors

- [yoyoplenty](https://github.com/yoyoplenty)


### License

This project is available for use under the MIT License.
