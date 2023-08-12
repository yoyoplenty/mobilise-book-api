package Mobilise.bookapi.book.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class BookDto {
        private String title;
        private String description;
        private int publicationYear;
        private List<UUID> authorId;
}
