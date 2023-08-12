package Mobilise.bookapi.author;

import Mobilise.bookapi.author.dto.CreateAuthorDto;
import Mobilise.bookapi.author.dto.UpdateAuthorDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface AuthorService {
    Map<String, Object> createAuthor(CreateAuthorDto createAuthorPayload);

    List<Author> findAllAuthors();

    Author findAuthorById(UUID id);

    Author findAuthorInBook(UUID id, UUID bookId);

    List<Author> findAuthorsByBookId(UUID id);

    Author updateAuthorById(UpdateAuthorDto updateAuthorPayload, UUID id);

    Object deleteAuthorById(UUID id);
}
