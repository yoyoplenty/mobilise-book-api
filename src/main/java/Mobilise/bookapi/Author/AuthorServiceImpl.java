package Mobilise.bookapi.Author;

import Mobilise.bookapi.Author.Dto.CreateAuthorDto;
import Mobilise.bookapi.Author.Dto.UpdateAuthorDto;
import Mobilise.bookapi.User.User;
import Mobilise.bookapi.User.UserService;
import Mobilise.bookapi.Utils.Handlers.Exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    /**
     * Inject all needed dependencies using the lombok constructor injection
     */
    private final UserService userService;
    private final AuthorRepository authorRepository;

    public Author createAuthor(CreateAuthorDto createAuthorPayload) {
        Author author = Author.builder()
                .specialization(createAuthorPayload.getSpecialization())
                .dateOfBirth(createAuthorPayload.getDateOfBirth())
                .build();

        //Create a User account for the specified author
        User user = userService.createUser(createAuthorPayload);
        author.setUser(user);

        return authorRepository.save(author);
    }

    //TODO Implement Pagination
    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    public Author findAuthorById(UUID id) {
        //Find the author by the UUID if not,throw an exception
       return authorRepository.findById(id)
               .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public List<Author> findAuthorsByBookId(UUID bookId) {
        return authorRepository.findAuthorsByBookId(bookId);
    }

    public Author findAuthorInBook(UUID authorId,  UUID bookId) {
        return authorRepository.findAuthorInBook(authorId, bookId);
    }
    public Author updateAuthorById(UpdateAuthorDto updateAuthorPayload, UUID id) {
        return null;
    }

    public Object deleteAuthorById(UUID id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        authorRepository.delete(author);
        return null;
    }
}
