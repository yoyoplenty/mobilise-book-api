package Mobilise.bookapi.user;

import Mobilise.bookapi.enums.RoleEnum;
import Mobilise.bookapi.user.dto.CreateUserDto;
import Mobilise.bookapi.user.dto.UpdateUserDto;
import Mobilise.bookapi.utils.handlers.Exceptions.ConflictException;
import Mobilise.bookapi.utils.handlers.Exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl  implements UserService{

    /**
     * Inject all needed dependencies using the lombok constructor injection
     */
   private final UserRepository userRepository;
   private final PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * This service method create a new user
     * @param createUserPayload
     * @return
     */
    public  User createUser(CreateUserDto createUserPayload) {
        //Validate if the email of user already exists
        Optional<User> userWithEmail = userRepository.findByEmail(createUserPayload.getEmail());
        if(userWithEmail.isPresent()) throw new ConflictException("user already exists");

        //This is a lombok annotation to create a user instance before its been saved
        User user = User.builder()
                .firstName(createUserPayload.getFirstName())
                .lastName(createUserPayload.getLastName())
                .email(createUserPayload.getEmail())
                .role(createUserPayload.getRole())
                .password(passwordEncoder.encode(createUserPayload.getPassword()))
                .isActive(false)
                .build();

        //Assign a default role if role is not provided and generate an email verification token
        if(user.getRole() == null) user.setRole(RoleEnum.USER);
        //Generate confirm token
        String confirmToken = UUID.randomUUID().toString();
        user.setConfirmToken(confirmToken);

        //Save user in the database table
        return userRepository.save(user);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User Not Found"));
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User Not Found"));
    }

    public User findUserByConfirmToken(String token) {
        return userRepository.findByConfirmToken(token)
                .orElseThrow(() -> new NotFoundException("User Not Found"));
    }

    public User findUserByResetToken(String token) {
        return userRepository.findByResetToken(token)
                .orElseThrow(() -> new NotFoundException("User Not Found"));
    }


    public User updateUserById(UpdateUserDto updateUserPayload, UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User Not Found"));

        user.setFirstName(updateUserPayload.getFirstName());
        user.setLastName(updateUserPayload.getLastName());
        user.setIsActive(updateUserPayload.getIsActive());
        user.setResetToken(updateUserPayload.getResetToken());

        return userRepository.save(user);
    }

    public User updateUser(UUID id, User user) {

        user.setResetToken(user.getResetToken());
        user.setIsActive(user.getIsActive());
        user.setConfirmToken(null);

        return userRepository.save(user);
    }

    public User updateUserPassword(UUID id, User user, String password) {

        user.setPassword(passwordEncoder.encode(password));

        return userRepository.save(user);
    }

    public Object deleteUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        userRepository.delete(user);
        return null;
    }
}
