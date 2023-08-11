package Mobilise.bookapi.Book;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
    List<Book> findByAuthors_Id(UUID authorId);

    Optional<Book> findByTitle(String title);
}
