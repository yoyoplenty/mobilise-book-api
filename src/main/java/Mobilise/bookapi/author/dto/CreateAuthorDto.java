package Mobilise.bookapi.author.dto;

import Mobilise.bookapi.user.dto.CreateUserDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Data
public class CreateAuthorDto extends CreateUserDto {
    @Size(min = 2, max = 50, message = "specialization should be between 2 - 50 characters")
    @NotEmpty(message = "Authors specialization cannot be empty")
    private String specialization;

    @Past(message = "Date of birth must be in the past")
    @NotNull(message = "Date of birth cannot be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd") // Specify the expected date format
    private LocalDate dateOfBirth;
}
