package Mobilise.bookapi.User;

import Mobilise.bookapi.User.Dto.CreateUserDto;
import Mobilise.bookapi.User.Dto.UpdateUserDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User createUser(CreateUserDto createUserPayload);

    List<User> findAllUsers();

    User findUserById(UUID id);

    User updateUserById(UpdateUserDto updateUserPayload, UUID id);

    Object deleteUserById(UUID id);
}
