package Mobilise.bookapi.book;

import Mobilise.bookapi.book.dto.CreateBookDto;
import Mobilise.bookapi.book.dto.UpdateBookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface BookService {
    Book createBook(CreateBookDto createBookPayload);

    Page<Book> findAllBooks(Pageable pageable);

    Book findBookById(UUID id);

     Book findBookByTitle(String title);

     List<Book> findBookByAuthorsId(UUID authorId);

    Book updateBookById(UpdateBookDto updateBookPayload, UUID id);

    Object deleteBookById(UUID id);
}
