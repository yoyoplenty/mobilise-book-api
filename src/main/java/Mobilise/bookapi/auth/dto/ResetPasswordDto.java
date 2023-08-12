package Mobilise.bookapi.auth.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class ResetPasswordDto {

    @Size(min = 5, max = 50, message = "last name should have 3 -15 characters")
    @NotEmpty(message = "password cannot be empty")
    private String password;
}

