package Mobilise.bookapi.book.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class CreateBookDto extends BookDto{
    @Size(min = 2, max = 100, message = "book title should have 2 -100 characters")
    @NotEmpty(message = "book title cannot be empty")
    private String title;

    @Size(min = 2, max = 512, message = "book title should have 2 -512 characters")
    @NotEmpty(message = "book description cannot be empty")
    private String description;

    @Min(value = 1900, message = "Year must be at least 1900")
    @Max(value = 2100, message = "Year must not exceed 2100")
    private int publicationYear;

    @Size(min = 1, max = 20, message = "author id's should be between 1 and 20")
    @NotEmpty(message = "author value cannot be empty")
    @NotNull(message = "author id's must not be null")
    private List<UUID> authorId;
}
