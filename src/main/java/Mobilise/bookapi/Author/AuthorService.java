package Mobilise.bookapi.Author;

import Mobilise.bookapi.Author.Dto.CreateAuthorDto;
import Mobilise.bookapi.Author.Dto.UpdateAuthorDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorService {
    Author createAuthor(CreateAuthorDto createAuthorPayload);

    List<Author> findAllAuthors();

    Author findAuthorById(UUID id);

    List<Author> findAuthorsBookById(UUID id);

    Author updateAuthorById(UpdateAuthorDto updateAuthorPayload, UUID id);

    Object deleteAuthorById(UUID id);
}
