package Mobilise.bookapi.book;

import Mobilise.bookapi.book.dto.CreateBookDto;
import Mobilise.bookapi.book.dto.UpdateBookDto;
import Mobilise.bookapi.utils.handlers.Exceptions.CustomException;
import Mobilise.bookapi.utils.handlers.Responses.ResponseHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    /**
     * This method creates new book and attach all authors to the books created
     * @param createBookPayload
     * @return
     */
    @PostMapping()
    public ResponseEntity<Object> createBook(@Valid @RequestBody  CreateBookDto createBookPayload) {
        try {
            Book book = bookService.createBook(createBookPayload);

            return ResponseHandler.generateResponse("Successfully created book", HttpStatus.OK, book);
        } catch (CustomException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), ex.getErrorCode(), null);
        }
    }

    /**
     * This method get all books with their respective authors on the system
     * @return
     */
    @GetMapping()
    public ResponseEntity<Object> findAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Book> books = bookService.findAllBooks(pageable);

            return ResponseHandler.generateResponse("Successfully fetched books", HttpStatus.OK, books);
        } catch (CustomException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), ex.getErrorCode(), null);
        }
    }

    /***
     * This method finds a book by the ID provided
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> findOneBook(@PathVariable String id) {
        try {
            UUID bookId = UUID.fromString(id);
            Book book = bookService.findBookById(bookId);

            return ResponseHandler.generateResponse("Successfully fetched book", HttpStatus.OK, book);
        } catch (IllegalArgumentException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    /**
     * This method gets all book by the author id provided
     * @param authorId
     * @return
     */
    @GetMapping("/authors/{authorId}")
    public ResponseEntity<Object> findAllAuthorsByBookId(@PathVariable String authorId) {
        try {
            UUID authorUuid = UUID.fromString(authorId);
            List<Book> books = bookService.findBookByAuthorsId(authorUuid);

            return ResponseHandler.generateResponse("Successfully fetched books", HttpStatus.OK, books);
        } catch (IllegalArgumentException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    /**
     * This method updates a book by their id
     * @param updateBookPayload
     * @param id
     * @return
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateBook(@RequestBody @Valid UpdateBookDto updateBookPayload, @PathVariable String id) {
        try {
            UUID bookId = UUID.fromString(id);
            Book book = bookService.updateBookById(updateBookPayload, bookId);

            return ResponseHandler.generateResponse("Successfully updated book", HttpStatus.OK, book);
        } catch (IllegalArgumentException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchBooks(@RequestParam(required = false) String title, @RequestParam(required = false) String authorId) {
        try {
            UUID authorUuid = authorId != null? UUID.fromString(authorId):null;
            Object book = title != null ? bookService.findBookByTitle(title): bookService.findBookByAuthorsId(authorUuid);

            return ResponseHandler.generateResponse("Successfully fetched books", HttpStatus.OK, book);
        } catch (IllegalArgumentException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    /**
     * This method deletes a book by their ID
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable String id) {
        try {
            UUID bookId = UUID.fromString(id);
            Object book = bookService.deleteBookById(bookId);

            return ResponseHandler.generateResponse("Successfully deleted book", HttpStatus.OK, book);
        } catch (IllegalArgumentException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }
}
