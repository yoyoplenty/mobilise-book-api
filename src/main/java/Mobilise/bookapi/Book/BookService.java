package Mobilise.bookapi.Book;

import Mobilise.bookapi.Book.Dto.CreateBookDto;
import Mobilise.bookapi.Book.Dto.UpdateBookDto;

import java.util.List;
import java.util.UUID;

public interface BookService {
    Book createBook(CreateBookDto createBookPayload);

    List<Book> findAllBooks();

    Book findBookById(UUID id);

    List<Book> findBookByAuthorsId(UUID authorId);

    Book updateBookById(UpdateBookDto updateBookPayload, UUID id);

    Object deleteBookById(UUID id);
}
