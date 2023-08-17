package Mobilise.bookapi.book;

import Mobilise.bookapi.author.Author;
import Mobilise.bookapi.author.AuthorService;
import Mobilise.bookapi.book.dto.BookDto;
import Mobilise.bookapi.book.dto.CreateBookDto;
import Mobilise.bookapi.book.dto.UpdateBookDto;
import Mobilise.bookapi.utils.handlers.Exceptions.ConflictException;
import Mobilise.bookapi.utils.handlers.Exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    /**
     * Inject all needed dependencies using the lombok constructor injection
     */
    private final AuthorService authorService;
    private final BookRepository bookRepository;

    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    public Book createBook(CreateBookDto createBookPayload) {
        // Checks if book with publication year and title already exists
        Optional<Book> bookPresent = bookRepository.findByTitleAndPublicationYear(createBookPayload.getTitle(),
                createBookPayload.getPublicationYear());
        if (bookPresent.isPresent())
            throw new ConflictException("Book with title and publication year exists");
        // Instantiate object using the lombok builder
        Book book = Book.builder()
                .title(createBookPayload.getTitle())
                .description(createBookPayload.getDescription())
                .publicationYear(createBookPayload.getPublicationYear())
                .build();

        // Set the authors who wrote the book to be saved
        book.setAuthors(addAuthorsToBook(createBookPayload, book));

        return bookRepository.save(book);
    }

    /**
     * Return all paginated book documents
     * 
     * @param pageable
     * @return
     */
    public Page<Book> findAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public Book findBookById(UUID id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found"));
    }

    public Book findBookByTitle(String title) {
        return bookRepository.findByTitle(title)
                .orElseThrow(() -> new NotFoundException("Book not found"));
    }

    public List<Book> findBookByAuthorsId(UUID authorId) {
        return bookRepository.findAllBooksByAuthorId(authorId);
    }

    /**
     * Updates an existing book and also all authors to books
     * 
     * @param updateBookPayload
     * @param id
     * @return
     */
    public Book updateBookById(UpdateBookDto updateBookPayload, UUID id) {
        // Checks if book exists
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found"));

        if (updateBookPayload.getAuthorId() != null && updateBookPayload.getAuthorId().size() > 0)
            book.setAuthors(addAuthorsToBook(updateBookPayload, book));
        // Update books fields
        book.setTitle(updateBookPayload.getTitle());
        book.setDescription(updateBookPayload.getDescription());
        book.setPublicationYear(updateBookPayload.getPublicationYear());

        return bookRepository.save(book);
    }

    public Book removeAuthorInBook(UUID authorId, UUID id) {
        // TODO Check if author exists in book
        // Create a Book repository for that
        // Remove Book;
        return null;
    }

    /**
     * Deletes a book from the books table
     * 
     * @param id
     * @return
     */
    public Object deleteBookById(UUID id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found"));

        bookRepository.delete(book);
        return null;
    }

    /**
     * Custom method that checks for a valid author and adds the author to a book
     * 
     * @param createBookPayload
     * @param book
     * @return
     */
    public Set<Author> addAuthorsToBook(BookDto createBookPayload, Book book) {
        List<UUID> AuthorIds = createBookPayload.getAuthorId();
        Set<Author> authors = book.getAuthors() != null ? book.getAuthors() : new HashSet<>();

        AuthorIds.forEach(authorId -> {
            Author author = authorService.findAuthorById(authorId);

            Author authorPresent = authorService.findAuthorInBook(author.getId(), book.getId());
            if (authorPresent != null)
                throw new ConflictException("Author already present in book " + book.getId());

            authors.add(author);
        });

        return authors;
    }

}
