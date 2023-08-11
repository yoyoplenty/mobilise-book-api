package Mobilise.bookapi.Book;

import Mobilise.bookapi.Author.Author;
import Mobilise.bookapi.Author.AuthorService;
import Mobilise.bookapi.Book.Dto.BookDto;
import Mobilise.bookapi.Book.Dto.CreateBookDto;
import Mobilise.bookapi.Book.Dto.UpdateBookDto;
import Mobilise.bookapi.Utils.Handlers.Exceptions.ConflictException;
import Mobilise.bookapi.Utils.Handlers.Exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{

    /**
     * Inject all needed dependencies using the lombok constructor injection
     */
    private final AuthorService authorService;
    private final BookRepository bookRepository;

    public Book createBook(CreateBookDto createBookPayload) {
        Optional<Book> bookPresent = bookRepository.findByTitleAndPublicationYear(createBookPayload.getTitle(),
                createBookPayload.getPublicationYear());
        if(bookPresent.isPresent()) throw new ConflictException("Book with title and publication year exists");

      Book book = Book.builder()
        .title(createBookPayload.getTitle())
        .description(createBookPayload.getDescription())
        .publicationYear(createBookPayload.getPublicationYear())
        .build();

      book.setAuthors(addAuthorsToBook(createBookPayload, book));

      return bookRepository.save(book);
    }

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

    public Book updateBookById(UpdateBookDto updateBookPayload, UUID id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found"));

        if(updateBookPayload.getAuthorId() != null && updateBookPayload.getAuthorId().size() > 0 )
            book.setAuthors(addAuthorsToBook(updateBookPayload, book));

        book.setTitle(updateBookPayload.getTitle());
        book.setDescription(updateBookPayload.getDescription());
        book.setPublicationYear(updateBookPayload.getPublicationYear());

        return bookRepository.save(book);
    }

    public Object deleteBookById(UUID id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found"));

        bookRepository.delete(book);
        return null;
    }

    public Set<Author> addAuthorsToBook(BookDto createBookPayload, Book book) {
        List<UUID> AuthorIds = createBookPayload.getAuthorId();
        Set<Author> authors = book.getAuthors() != null ? book.getAuthors() : new HashSet<>();

        AuthorIds.forEach(authorId -> {
            Author author = authorService.findAuthorById(authorId);

            Author authorPresent = authorService.findAuthorInBook(author.getId(), book.getId());
            if (authorPresent != null) throw new ConflictException("Author already present in book " + book.getId());

            authors.add(author);
        });

        return authors;
    }
}
