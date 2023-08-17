package Mobilise.bookapi.book;

import Mobilise.bookapi.author.Author;
import Mobilise.bookapi.author.AuthorServiceImpl;
import Mobilise.bookapi.book.dto.CreateBookDto;
import Mobilise.bookapi.book.dto.UpdateBookDto;
import Mobilise.bookapi.utils.handlers.Exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private AuthorServiceImpl authorService;

    @Mock
    private BookRepository bookRepository;

    @Test
    void testCreateBookWithValidData() {
        // Give or Arrange
        UUID authorId = UUID.randomUUID();
        Author author = Author.builder().id(authorId).build();

        List<UUID> authorIds = new ArrayList<>();
        Set<Author> newAuthor = new HashSet<>();

        newAuthor.add(author);
        authorIds.add(author.getId());
        CreateBookDto bookPayload = new CreateBookDto("Test Book", "Test book description", 2020, authorIds);

        Book book = Book.builder()
                .title(bookPayload.getTitle())
                .description(bookPayload.getDescription())
                .publicationYear(bookPayload.getPublicationYear())
                .authors(newAuthor)
                .build();

        // When or Act
        when(bookRepository.save(book)).thenReturn(book);
        when(authorService.findAuthorById(authorId)).thenReturn(author);
        Book result = bookService.createBook(bookPayload);

        // Then or Assert
        assertNotNull(result);
        assertEquals(author, result.getAuthors().iterator().next());
    }

    @Test
    void testCreateBookWithInvalidAuthorId() {
        // Give or Arrange
        UUID authId1 = UUID.randomUUID();
        List<UUID> authorId = new ArrayList<>();

        authorId.add(authId1);
        CreateBookDto bookPayload = new CreateBookDto("Test Book", "Test book description", 2020, authorId);
        // Act and Assert
        when(authorService.findAuthorById(any(UUID.class))).thenThrow(new NotFoundException("Author not found"));

        try {
            bookService.createBook(bookPayload);
        } catch (NotFoundException e) {
            // Expected behavior
            return;
        }
    }

    @Test
    void testFindAllBooks() {
        // Give or Arrange
        List<Book> books = Arrays.asList(
                new Book(),
                new Book());
        Page<Book> Pagedbooks = new PageImpl<>(books);
        Pageable pageable = PageRequest.of(0, 10); // Example pageable

        // When or Act
        when(bookRepository.findAll(pageable)).thenReturn(Pagedbooks);
        Page<Book> result = bookService.findAllBooks(pageable);

        // Then or Assert
        assertNotNull(result);
        assertEquals(books, result.getContent());
    }

    @Test
    void testFindOneBook() {
        // Give or Arrange
        UUID bookId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();

        Author newAuthor = Author.builder().id(authorId).build();

        Set<Author> authors = new HashSet<>();
        authors.add(newAuthor);

        Book book = new Book(bookId, "Test Book", "Test Book description", 2017, authors);

        // When or Act
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        Book result = bookService.findBookById(book.getId());

        // Then or Assert
        assertNotNull(result);
        assertEquals(result, book);
    }

    @Test
    void testFindOneBookNotFound() {
        // Give or Arrange
        Book book = new Book();

        // Act & Assert
        when(bookRepository.findById(book.getId())).thenThrow(new NotFoundException("Book not found"));

        try {
            bookService.findBookById(book.getId());
        } catch (NotFoundException e) {
            // Expected behavior
            return;
        }
    }

    @Test
    void testFindBookByTitle() {
        // Give or Arrange
        UUID bookId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();

        Author newAuthor = Author.builder().id(authorId).build();

        Set<Author> authors = new HashSet<>();
        authors.add(newAuthor);

        Book book = new Book(bookId, "Test Book", "Test Book description", 2017, authors);

        // When or Act
        when(bookRepository.findByTitle(book.getTitle())).thenReturn(Optional.of(book));
        Book result = bookService.findBookByTitle(book.getTitle());

        // Then or Assert
        assertNotNull(result);
        assertEquals(result, book);
    }

    @Test
    void testUpdateBookById() {
        // Give or Arrange
        UUID bookId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        Author newAuthor = Author.builder().id(authorId).build();

        Set<Author> authors = new HashSet<>();
        authors.add(newAuthor);

        Book book = new Book(bookId, "Test Book", "Test Book description", 2017, authors);
        UpdateBookDto updatedBook = new UpdateBookDto();
        updatedBook.setTitle("Updated Book");
        // When or Act

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);
        Book result = bookService.updateBookById(updatedBook, book.getId());

        // Then or Assert
        assertNotNull(result);
        assertEquals(result.getTitle(), updatedBook.getTitle());
    }

    @Test
    void testDeleteBookById() {
        // Give or Arrange
        Book book = new Book();

        // When or Act
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        Object result = bookService.deleteBookById(book.getId());

        assertNull(result);
    }
}