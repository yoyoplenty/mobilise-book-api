package Mobilise.bookapi.user;

import Mobilise.bookapi.enums.RoleEnum;
import Mobilise.bookapi.user.dto.CreateUserDto;
import Mobilise.bookapi.user.dto.UpdateUserDto;
import Mobilise.bookapi.utils.handlers.Exceptions.ConflictException;
import Mobilise.bookapi.utils.handlers.Exceptions.CustomException;
import Mobilise.bookapi.utils.handlers.Exceptions.NotFoundException;
import Mobilise.bookapi.utils.services.TokenService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
   private final TokenService tokenService;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * This service method create a new user
     * @param createUserPayload
     * @return
     */
    public  Map<String, Object> createUser(CreateUserDto createUserPayload) {
        //This Checks if user is an admin, so a default role can be provided, so we need to get logged in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(!Objects.equals((String) auth.getPrincipal(), "anonymousUser") && createUserPayload.getRole() == null)
            throw new CustomException("Role must be provided", HttpStatus.BAD_REQUEST);
        else createUserPayload.setRole(createUserPayload.getRole());

        //Validate if the email of user already exists
        Optional<User> userWithEmail = userRepository.findByEmail(createUserPayload.getEmail());
        if(userWithEmail.isPresent()) throw new ConflictException("user already exists");

        //This is a lombok annotation to create a user instance before its been saved
        User user = User.builder()
                .firstName(createUserPayload.getFirstName())
                .lastName(createUserPayload.getLastName())
                .email(createUserPayload.getEmail())
                .password(passwordEncoder.encode(createUserPayload.getPassword()))
                .isActive(false)
                .build();

         //Assign a default role and generate an email verification token
        if(createUserPayload.getRole() == null) user.setRole(RoleEnum.USER);

        //Generate tokens(confirm and verification tokens)
        String confirmToken = UUID.randomUUID().toString();
        String verificationToken = tokenService.encodeToken(confirmToken);

        user.setConfirmToken(confirmToken);
        User savedUser = userRepository.save(user);

        Map<String, Object> data = new HashMap<>();

        data.put("verification_token", verificationToken);
        data.put("user", savedUser);
        data.put("authorities", savedUser.getAuthorities());

        return data;
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

    public User updateUserById(UpdateUserDto updateUserPayload, UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User Not Found"));

        user.setFirstName(updateUserPayload.getFirstName());
        user.setLastName(updateUserPayload.getLastName());
        user.setIsActive(updateUserPayload.getIsActive());
        user.setResetToken(updateUserPayload.getResetToken());

        return userRepository.save(user);
    }

    public User updateUserStatus(UUID id, Boolean status) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User Not Found"));

        user.setIsActive(status);
        user.setConfirmToken(null);

        return userRepository.save(user);
    }

    public Object deleteUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        userRepository.delete(user);
        return null;
    }
}
