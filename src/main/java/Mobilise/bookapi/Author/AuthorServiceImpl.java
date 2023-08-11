package Mobilise.bookapi.Author;

import Mobilise.bookapi.Author.Dto.CreateAuthorDto;
import Mobilise.bookapi.Author.Dto.UpdateAuthorDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AuthorServiceImpl implements AuthorService {
    public Author createAuthor(CreateAuthorDto createAuthorPayload) {
        return null;
    }

    public List<Author> findAllAuthors() {
        return null;
    }

    public Author findAuthorById(UUID id) {
        return null;
    }

    public List<Author> findAuthorsBookById(UUID id) {
        return null;
    }

    public Author updateAuthorById(UpdateAuthorDto updateAuthorPayload, UUID id) {
        return null;
    }

    public Object deleteAuthorById(UUID id) {
        return null;
    }
}
