package Mobilise.bookapi.User.Dto;

import lombok.Data;

@Data
public class UpdateUserDto {
    private String firstName;

    private String lastName;

    private String password;

    private Boolean isActive;
}
