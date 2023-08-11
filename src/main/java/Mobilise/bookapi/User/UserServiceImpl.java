package Mobilise.bookapi.User;


import Mobilise.bookapi.Enums.RoleEnum;
import Mobilise.bookapi.User.Dto.CreateUserDto;
import Mobilise.bookapi.User.Dto.UpdateUserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl  implements UserService{

    @Autowired
    UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public User createUser(CreateUserDto createUserPayload) {
        User user = User.builder()
                .firstName(createUserPayload.getFirstName())
                .lastName(createUserPayload.getLastName())
                .email(createUserPayload.getEmail())
                .password(createUserPayload.getPassword())
                .isActive(false)
                .build();

        if(createUserPayload.getRole() == null) user.setRole(RoleEnum.USER);
        user.setConfirmToken(UUID.randomUUID().toString());

        return userRepository.save(user);
    }

    public List<User> findAllUsers() {
        return null;
    }

    public User findUserById(UUID id) {
        return null;
    }

    public User updateUserById(UpdateUserDto updateUserPayload, UUID id) {
        return null;
    }

    public Object deleteUserById(UUID id) {
        return null;
    }
}
