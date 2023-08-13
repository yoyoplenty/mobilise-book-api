package Mobilise.bookapi.auth.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginDto {
    @Email(message = "email must be a valid email")
    @NotBlank(message = "email cannot be empty")
    private String email;

    @Size(min = 5, max = 50, message = "last name should have 3 -15 characters")
    @NotBlank(message = "password cannot be empty")
    private String password;
}
