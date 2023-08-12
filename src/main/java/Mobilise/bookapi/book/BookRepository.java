package Mobilise.bookapi.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public interface BookRepository extends JpaRepository<Book, UUID> {
    @Query("SELECT b FROM Book b JOIN b.authors a WHERE a.id = :authorId")
    List<Book> findAllBooksByAuthorId(@Param("authorId") UUID authorId);

    Optional<Book>findByTitleAndPublicationYear(String title, int publicationYear);

    Optional<Book> findByTitle(String title);
}
