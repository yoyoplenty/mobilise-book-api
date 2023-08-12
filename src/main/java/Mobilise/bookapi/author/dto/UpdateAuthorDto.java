package Mobilise.bookapi.author.dto;

import Mobilise.bookapi.user.dto.UpdateUserDto;
import lombok.Data;

import java.time.LocalDate;


@Data
public class UpdateAuthorDto extends UpdateUserDto {
    private String specialization;

    private LocalDate dateOfBirth;
}
