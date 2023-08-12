package Mobilise.bookapi.author;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public interface AuthorRepository extends JpaRepository<Author, UUID> {
    @Query("SELECT a FROM Author a JOIN a.books b WHERE b.id = :bookId")
    List<Author> findAuthorsByBookId(@Param("bookId") UUID bookId);

    @Query("SELECT a FROM Author a " +
            "JOIN a.books b " +
            "WHERE a.id = :authorId " +
            "AND b.id = :bookId " )
    Author findAuthorInBook(@Param("authorId") UUID authorId, @Param("bookId") UUID bookId);
}

