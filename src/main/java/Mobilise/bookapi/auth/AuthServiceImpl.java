package Mobilise.bookapi.auth;

import Mobilise.bookapi.auth.dto.LoginDto;
import Mobilise.bookapi.auth.dto.ResetPasswordDto;
import Mobilise.bookapi.user.User;
import Mobilise.bookapi.user.UserService;
import Mobilise.bookapi.user.dto.CreateUserDto;
import Mobilise.bookapi.utils.handlers.Exceptions.CustomException;
import Mobilise.bookapi.utils.services.JwtService;
import Mobilise.bookapi.utils.services.TokenService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    /**
     * Injects all Needed Authentication service dependencies through the lombok constructor injection
     */
    private final JwtService jwtService;
    private final TokenService tokenService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    //CUSTOM LOGGER
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);


    public Map<String, Object> register(CreateUserDto createUserPayload) {
        //Calls the userService to create a user row/entity
        User user =  userService.createUser(createUserPayload);
        String verificationToken = tokenService.encodeToken(user.getConfirmToken());

        //Creates an object, so we can get the verification token, since we wont be getting a mail
        Map<String, Object> data = new HashMap<>();

        data.put("verification_token", verificationToken);
        data.put("user", user);

        return data;
    }

    public Map<String, Object> login(LoginDto loginDto) {
        //Method verifies login details(directly from spring security)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(),  loginDto.getPassword())
        );
        //checks if user is inactive or deactivated
        User user = (User) authentication.getPrincipal();
        if (!user.getIsActive())
            throw new CustomException("user is inactive or deactivated", HttpStatus.BAD_REQUEST);
        //Generate jwt authentication token
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateToken(user);

        Map<String, Object> data = new HashMap<>();
        data.put("accessToken", jwt);
        data.put("user", authentication.getPrincipal());

        return data;
    }

    public User verifyEmail(String token) {
        //Decodes the encoded verification token
        String confirmToken = tokenService.decodeToken(token);
        User user = userService.findUserByConfirmToken(confirmToken);
        //Checks is user is already activated
        if (user.getIsActive())
            throw new CustomException("user already active", HttpStatus.BAD_REQUEST);
        //Updates user status and deletes confirmation token
        return userService.updateUserStatus(user.getId(), true);
    }

    public Map<String, Object> resendEmail(String email) {
        //Checks is user is already activated
        User user = userService.findUserByEmail(email);
        if (user.getIsActive())
            throw new CustomException("user already active", HttpStatus.BAD_REQUEST);

        String verificationToken = tokenService.encodeToken(user.getConfirmToken());

        Map<String, Object> data = new HashMap<>();

        data.put("verification_token", verificationToken);
        data.put("user", user);

        return data;
    }

    public String forgetPassword(String email) {
        return null;
    }

    public String resetPassword(String token, ResetPasswordDto resetPasswordPayload) {
        return null;
    }
}
