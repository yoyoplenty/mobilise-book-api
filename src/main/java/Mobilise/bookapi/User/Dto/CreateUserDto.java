package Mobilise.bookapi.User.Dto;


import Mobilise.bookapi.Enums.RoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserDto {
    @Size(min = 3, max = 30, message = "first name should have 3 -30 characters")
    @NotEmpty(message = "firstname cannot be empty")
    private String firstName;

    @Size(min = 3, max = 15, message = "last name should have 3 -15 characters")
    @NotBlank(message = "lastname cannot be empty")
    private String lastName;

    @Email
    @NotEmpty(message = "email should be a valid email address")
    private String email;

    @Size(min = 5, max = 50, message = "last name should have 3 -15 characters")
    @NotEmpty(message = "password cannot be empty")
    private String password;

    //TODO Role is only to be provided when the logged in user is an Admin
    private RoleEnum role;
}
