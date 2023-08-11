package Mobilise.bookapi.User;

import Mobilise.bookapi.Enums.RoleEnum;
import Mobilise.bookapi.User.Dto.CreateUserDto;
import Mobilise.bookapi.User.Dto.UpdateUserDto;
import Mobilise.bookapi.Utils.Handlers.Exceptions.ConflictException;
import Mobilise.bookapi.Utils.Handlers.Exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl  implements UserService{

    /**
     * Inject all needed dependencies using the lombok constructor injection
     */
   private final UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * This service method create a new user
     * @param createUserPayload
     * @return
     */
    public User createUser(CreateUserDto createUserPayload) {
        //TODO Check if user is an admin, so a default role can be provided

        //Validate if the email of user already exists
        Optional<User> userWithEmail = userRepository.findByEmail(createUserPayload.getEmail());
        if(userWithEmail.isPresent()) throw new ConflictException("user already exists");

        //This is a lombok annotation to create a user instance before its been saved
        User user = User.builder()
                .firstName(createUserPayload.getFirstName())
                .lastName(createUserPayload.getLastName())
                .email(createUserPayload.getEmail())
                .password(createUserPayload.getPassword())
                .isActive(false)
                .build();

         //Assign a default role and generate an email verification token
        if(createUserPayload.getRole() == null) user.setRole(RoleEnum.USER);
        user.setConfirmToken(UUID.randomUUID().toString());
        //TODO Hash password before saving;

        return userRepository.save(user);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User Not Found with"));
    }

    public User updateUserById(UpdateUserDto updateUserPayload, UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User Not Found with"));

        user.setFirstName(updateUserPayload.getFirstName());
        user.setLastName(updateUserPayload.getLastName());
        user.setIsActive(updateUserPayload.getIsActive());

        return userRepository.save(user);
    }

    public Object deleteUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        userRepository.delete(user);
        return null;
    }
}
