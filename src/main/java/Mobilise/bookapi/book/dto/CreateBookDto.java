package Mobilise.bookapi.book.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CreateBookDto extends BookDto{
    @NotEmpty(message = "book title cannot be empty")
    private String title;

    @NotEmpty(message = "book description cannot be empty")
    private String description;

    @Min(value = 1900, message = "Year must be at least 1900")
    @Max(value = 2100, message = "Year must not exceed 2100")
    private int publicationYear;

   @NotNull(message = "author id's must not be empty")
    private List<UUID> authorId;
}
