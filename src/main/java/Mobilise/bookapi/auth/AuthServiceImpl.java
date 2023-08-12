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
import org.aspectj.bridge.MessageUtil;
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

    private final JwtService jwtService;
    private final TokenService tokenService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);


    public Map<String, Object> register(CreateUserDto createUserPayload) {
        return userService.createUser(createUserPayload);
    }

    public Map<String, Object> login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );

        User user = (User) authentication.getPrincipal();
        if (!user.getIsActive())
            throw new CustomException("user is inactive or deactivated", HttpStatus.BAD_REQUEST);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateToken(user);

        Map<String, Object> data = new HashMap<>();
        data.put("accessToken", jwt);
        data.put("user", authentication.getPrincipal());
        data.put("authorities", authentication.getAuthorities());

        return data;
    }

    public User verifyEmail(String token) {
        String confirmToken = tokenService.decodeToken(token);
        User user = userService.findUserByConfirmToken(confirmToken);

        if (user.getIsActive())
            throw new CustomException("user already active", HttpStatus.BAD_REQUEST);

        return userService.updateUserStatus(user.getId(), true);
    }

    public Map<String, Object> resendEmail(String email) {
        User user = userService.findUserByEmail(email);
        if (user.getIsActive())
            throw new IllegalStateException("user already active");

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
