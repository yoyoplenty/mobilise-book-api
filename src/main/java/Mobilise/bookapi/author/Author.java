package Mobilise.bookapi.author;

import Mobilise.bookapi.book.Book;
import Mobilise.bookapi.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * This are required annotations, mainly a lombok package to reduce
 * boilerplate's
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authors")
public class Author {
        @Id
        @GeneratedValue
        private UUID id = UUID.randomUUID();

        @Column(name = "specialization")
        private String specialization;

        @Column(name = "date_of_birth")
        private LocalDate dateOfBirth;

        @OneToOne(cascade = { CascadeType.MERGE,
                        CascadeType.PERSIST, CascadeType.REFRESH,
                        CascadeType.ALL }, orphanRemoval = true, fetch = FetchType.EAGER)
        @JoinColumn(name = "user_id")
        private User user;

        @JsonIgnore
        @ManyToMany(mappedBy = "authors", cascade = { CascadeType.MERGE, CascadeType.PERSIST,
                        CascadeType.REFRESH }, fetch = FetchType.LAZY)
        private Set<Book> books = new HashSet<>();
}
