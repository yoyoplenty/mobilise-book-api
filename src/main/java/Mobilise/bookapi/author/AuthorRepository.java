package Mobilise.bookapi.author;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {
    @Query("SELECT a FROM Author a JOIN a.books b WHERE b.id = :bookId")
    List<Author> findAuthorsByBookId(@Param("bookId") UUID bookId);

    @Query("SELECT a FROM Author a " +
            "JOIN a.books b " +
            "WHERE a.id = :authorId " +
            "AND b.id = :bookId " )
    Author findAuthorInBook(@Param("authorId") UUID authorId, @Param("bookId") UUID bookId);
}

