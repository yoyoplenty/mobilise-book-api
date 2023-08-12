package Mobilise.bookapi.user;

import Mobilise.bookapi.user.dto.CreateUserDto;
import Mobilise.bookapi.user.dto.UpdateUserDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface UserService {
    User createUser(CreateUserDto createUserPayload);

    List<User> findAllUsers();

    User findUserById(UUID id);

    User findUserByEmail(String email);

    User updateUserById(UpdateUserDto updateUserPayload, UUID id);

    User updateUserStatus(UUID id, Boolean status);

    Object deleteUserById(UUID id);

    User findUserByConfirmToken(String confirmToken);
}
