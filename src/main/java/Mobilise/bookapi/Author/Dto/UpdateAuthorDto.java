package Mobilise.bookapi.Author.Dto;

import Mobilise.bookapi.User.Dto.UpdateUserDto;
import lombok.Data;

import java.time.LocalDate;


@Data
public class UpdateAuthorDto extends UpdateUserDto {
    private String specialization;

    private LocalDate dateOfBirth;
}
