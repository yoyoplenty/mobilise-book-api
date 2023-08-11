package Mobilise.bookapi.Book.Dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class UpdateBookDto extends  BookDto  {
    private String title;

    private String description;

    private int publicationYear;

    private List<UUID> authorId;
}
