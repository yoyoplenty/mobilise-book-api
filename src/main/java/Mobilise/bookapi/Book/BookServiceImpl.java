package Mobilise.bookapi.Book;

import Mobilise.bookapi.Book.Dto.CreateBookDto;
import Mobilise.bookapi.Book.Dto.UpdateBookDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class BookServiceImpl implements BookService{
    public Book createBook(CreateBookDto createBookPayload) {
        return null;
    }

    public List<Book> findAllBooks() {
        return null;
    }

    public Book findBookById(UUID id) {
        return null;
    }

    public List<Book> findBookByAuthorsId(UUID authorId) {
        return null;
    }

    public Book updateBookById(UpdateBookDto updateBookPayload, UUID id) {
        return null;
    }

    public Object deleteBookById(UUID id) {
        return null;
    }
}
