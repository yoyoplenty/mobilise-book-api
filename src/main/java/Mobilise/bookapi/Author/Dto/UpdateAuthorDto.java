package Mobilise.bookapi.Author.Dto;

import Mobilise.bookapi.User.Dto.UpdateUserDto;

import java.time.LocalDateTime;

public class UpdateAuthorDto extends UpdateUserDto {

    private String specialization;

    private LocalDateTime dateOfBirth;
}
