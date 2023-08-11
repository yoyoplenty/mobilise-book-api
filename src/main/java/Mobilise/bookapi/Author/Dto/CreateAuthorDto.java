package Mobilise.bookapi.Author.Dto;

import Mobilise.bookapi.User.Dto.CreateUserDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;

import java.time.LocalDateTime;

public class CreateAuthorDto extends CreateUserDto {
    @NotEmpty(message = "Authors specialization cannot be empty")
    private String specialization;

    @Past(message = "Date of birth must be in the past")
    private LocalDateTime dateOfBirth;
}
