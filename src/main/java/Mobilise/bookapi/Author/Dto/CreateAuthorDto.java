package Mobilise.bookapi.Author.Dto;

import Mobilise.bookapi.User.Dto.CreateUserDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Data
public class CreateAuthorDto extends CreateUserDto {
    @NotEmpty(message = "Authors specialization cannot be empty")
    private String specialization;

    @Past(message = "Date of birth must be in the past")
    @NotNull(message = "Date of birth cannot be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd") // Specify the expected date format
    private LocalDate dateOfBirth;
}
