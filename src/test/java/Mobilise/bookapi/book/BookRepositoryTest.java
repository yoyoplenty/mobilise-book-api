package Mobilise.bookapi.book;

import Mobilise.bookapi.author.Author;
import Mobilise.bookapi.enums.RoleEnum;
import Mobilise.bookapi.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookRepositoryTest {

    @Mock
    private BookRepository bookRepository;

    @Test
    void itShouldFindAllBooksByAuthorId() {
        //Give or Arrange
        UUID userId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        UUID bookId = UUID.randomUUID();

        User user = User.builder().id(userId).email("example@goal.com").role(RoleEnum.USER).build();
        Author author = Author.builder().id(authorId).specialization("horror").dateOfBirth(LocalDate.now()).user(user).build();

        Set<Author> authors = new HashSet<Author>();
        authors.add(author);

        Book book1 = Book.builder().id(bookId).title("Book 1").description("Book 1 Description").publicationYear(2020).authors(authors).build();
        Book book2 = Book.builder().id(bookId).title("Book 2").description("Book 2 Description").publicationYear(2021).authors(authors).build();

        //When or Act
        List<Book> expectedBooks = List.of(book1, book2);
        when(bookRepository.findAllBooksByAuthorId(authorId)).thenReturn(expectedBooks);
        List<Book> result = bookRepository.findAllBooksByAuthorId(authorId);

        // Then or Assert
        assertNotNull(result);
        assertEquals(expectedBooks.size(), result.size());
        assertEquals(expectedBooks, result);
    }
}