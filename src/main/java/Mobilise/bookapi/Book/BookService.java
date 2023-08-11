package Mobilise.bookapi.Book;

import Mobilise.bookapi.Book.Dto.CreateBookDto;
import Mobilise.bookapi.Book.Dto.UpdateBookDto;
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
