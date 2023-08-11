package Mobilise.bookapi.Book;


import Mobilise.bookapi.Book.Dto.CreateBookDto;
import Mobilise.bookapi.Book.Dto.UpdateBookDto;
import Mobilise.bookapi.Utils.Handlers.Responses.ResponseHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/books")
public class BookController {
    @Autowired
    BookService bookService;

    /**
     * This method creates new book and attach all authors to the books created
     * @param createBookPayload
     * @return
     */
    @PostMapping()
    public ResponseEntity<Object> createBook(@RequestBody @Valid CreateBookDto createBookPayload) {
        try {
            Book book = bookService.createBook(createBookPayload);

            return ResponseHandler.generateResponse("Successfully created book", HttpStatus.OK, book);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        }
    }

    /**
     * This method get all books with their respective authors on the system
     * @return
     */
    @GetMapping()
    public ResponseEntity<Object> findAllBooks() {
        try {
            List<Book> books = bookService.findAllBooks();

            return ResponseHandler.generateResponse("Successfully fetched books", HttpStatus.OK, books);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        }
    }

    /***
     * This method finds a book by the ID provided
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> findOneBook(@PathVariable UUID id) {
        try {
            Book book = bookService.findBookById(id);

            return ResponseHandler.generateResponse("Successfully fetched book", HttpStatus.OK, book);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        }
    }

    /**
     * This method gets all book by the author id provided
     * @param authorId
     * @return
     */
    @GetMapping("/authors/{authorId}")
    public ResponseEntity<Object> findAllAuthorsByBookId(@PathVariable UUID authorId) {
        try {
            List<Book> books = bookService.findBookByAuthorsId(authorId);

            return ResponseHandler.generateResponse("Successfully fetched books", HttpStatus.OK, books);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        }
    }

    /**
     * This method updates a book by their id
     * @param updateBookPayload
     * @param id
     * @return
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateBook(@RequestBody @Valid UpdateBookDto updateBookPayload, @PathVariable UUID id) {
        try {
            Book book = bookService.updateBookById(updateBookPayload, id);

            return ResponseHandler.generateResponse("Successfully updated book", HttpStatus.OK, book);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        }
    }

    /**
     * This method deletes a book by their ID
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable UUID id) {
        try {
            Object book = bookService.deleteBookById(id);

            return ResponseHandler.generateResponse("Successfully deleted book", HttpStatus.OK, book);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        }
    }
}
