package Mobilise.bookapi.author;

import Mobilise.bookapi.author.dto.CreateAuthorDto;
import Mobilise.bookapi.author.dto.UpdateAuthorDto;
import Mobilise.bookapi.utils.handlers.Exceptions.CustomException;
import Mobilise.bookapi.utils.handlers.Responses.ResponseHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/authors")
@Validated
public class AuthorController {

    private final AuthorService authorService;

    /**
     * This method creates a new author, and this is done by the Admin
     * 
     * @param createAuthorPayload
     * @return
     */
    @PostMapping()
    public ResponseEntity<Object> createAuthor(@Valid @RequestBody CreateAuthorDto createAuthorPayload) {
        try {
            Object data = authorService.createAuthor(createAuthorPayload);

            return ResponseHandler.generateResponse("Successfully created author", HttpStatus.OK, data);
        } catch (CustomException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), ex.getErrorCode(), null);
        }
    }

    /**
     * This method get all authors available on the system
     * 
     * @return
     */
    @GetMapping()
    public ResponseEntity<Object> findAllAuthors() {
        try {
            List<Author> authors = authorService.findAllAuthors();

            return ResponseHandler.generateResponse("Successfully fetched authors", HttpStatus.OK, authors);
        } catch (CustomException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), ex.getErrorCode(), null);
        }
    }

    /***
     * This method finds an author by the ID provided
     * 
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> findOneAuthor(@PathVariable("id") String id) {
        try {
            UUID authorId = UUID.fromString(id);
            Author author = authorService.findAuthorById(authorId);

            return ResponseHandler.generateResponse("Successfully fetched author", HttpStatus.OK, author);
        } catch (IllegalArgumentException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    /**
     * This method gets all authors by the book id provided
     * 
     * @param bookId
     * @return
     */
    @GetMapping("/books/{bookId}")
    public ResponseEntity<Object> findAllAuthorsByBookId(@PathVariable String bookId) {
        try {
            UUID BookUuid = UUID.fromString(bookId);
            List<Author> authors = authorService.findAuthorsByBookId(BookUuid);

            return ResponseHandler.generateResponse("Successfully fetched authors", HttpStatus.OK, authors);
        } catch (IllegalArgumentException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    /**
     * This method updates an author by their id
     * 
     * @param updateAuthorPayload
     * @param id
     * @return
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateAuthor(@Valid @RequestBody UpdateAuthorDto updateAuthorPayload,
            @PathVariable String id) {
        try {
            UUID authorId = UUID.fromString(id);
            Author author = authorService.updateAuthorById(updateAuthorPayload, authorId);

            return ResponseHandler.generateResponse("Successfully updated author", HttpStatus.OK, author);
        } catch (IllegalArgumentException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    /**
     * This method deletes a author by their ID
     * 
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAuthor(@PathVariable String id) {
        try {
            UUID authorId = UUID.fromString(id);
            Object author = authorService.deleteAuthorById(authorId);

            return ResponseHandler.generateResponse("Successfully deleted author", HttpStatus.OK, author);
        } catch (IllegalArgumentException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }
}
