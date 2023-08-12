package Mobilise.bookapi.author;

import Mobilise.bookapi.author.dto.CreateAuthorDto;
import Mobilise.bookapi.author.dto.UpdateAuthorDto;
import Mobilise.bookapi.enums.RoleEnum;
import Mobilise.bookapi.user.User;
import Mobilise.bookapi.user.UserService;
import Mobilise.bookapi.utils.handlers.Exceptions.NotFoundException;
import Mobilise.bookapi.utils.services.TokenService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
    private final TokenService tokenService;

    private static final Logger logger = LoggerFactory.getLogger(AuthorServiceImpl.class);


    public Map<String, Object> createAuthor(CreateAuthorDto createAuthorPayload) {
        //Using the lombok builder to instantiate objects
        Author author = Author.builder()
                .specialization(createAuthorPayload.getSpecialization())
                .dateOfBirth(createAuthorPayload.getDateOfBirth())
                .build();

        createAuthorPayload.setRole(RoleEnum.AUTHOR);

        //Create a User account for the specified author
        User user = userService.createUser(createAuthorPayload);
        String verificationToken = tokenService.encodeToken(user.getConfirmToken());

        author.setUser(user);
        Author savedAuthor = authorRepository.save(author);

        Map<String, Object> data = new HashMap<>();
        data.put("verification_token", verificationToken);
        data.put("user", savedAuthor);

        return data;
    }

    //TODO Implement Pagination
    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    public Author findAuthorById(UUID id) {
        //Find the author by the UUID if not,throw an exception
       return authorRepository.findById(id)
               .orElseThrow(() -> new NotFoundException("Author not found"));
    }

    public List<Author> findAuthorsByBookId(UUID bookId) {
        return authorRepository.findAuthorsByBookId(bookId);
    }

    public Author findAuthorInBook(UUID authorId,  UUID bookId) {
        return authorRepository.findAuthorInBook(authorId, bookId);
    }

    public Author updateAuthorById(UpdateAuthorDto updateAuthorPayload, UUID id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author not found"));

        author.setSpecialization(updateAuthorPayload.getSpecialization());
        author.setDateOfBirth(updateAuthorPayload.getDateOfBirth());

        userService.updateUserById(updateAuthorPayload, author.getUser().getId());

        return authorRepository.save(author);
    }

    public Object deleteAuthorById(UUID id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author not found"));

        authorRepository.delete(author);
        return null;
    }
}
