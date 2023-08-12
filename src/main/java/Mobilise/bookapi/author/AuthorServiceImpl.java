package Mobilise.bookapi.author;

import Mobilise.bookapi.author.dto.CreateAuthorDto;
import Mobilise.bookapi.author.dto.UpdateAuthorDto;
import Mobilise.bookapi.user.User;
import Mobilise.bookapi.user.UserService;
import Mobilise.bookapi.utils.handlers.Exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    /**
     * Inject all needed dependencies using the lombok constructor injection
     */
    private final UserService userService;
    private final AuthorRepository authorRepository;

    private static final Logger logger = LoggerFactory.getLogger(AuthorServiceImpl.class);


    public Author createAuthor(CreateAuthorDto createAuthorPayload) {
        //Using the lombok builder to instantiate objects
        Author author = Author.builder()
                .specialization(createAuthorPayload.getSpecialization())
                .dateOfBirth(createAuthorPayload.getDateOfBirth())
                .build();

        //Create a User account for the specified author
        Map<String, Object> userDetails = userService.createUser(createAuthorPayload);
        author.setUser((User) userDetails.get("user"));

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
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        author.setSpecialization(updateAuthorPayload.getSpecialization());
        author.setDateOfBirth(updateAuthorPayload.getDateOfBirth());

        userService.updateUserById(updateAuthorPayload, author.getUser().getId());

        return authorRepository.save(author);
    }

    public Object deleteAuthorById(UUID id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        authorRepository.delete(author);
        return null;
    }
}
