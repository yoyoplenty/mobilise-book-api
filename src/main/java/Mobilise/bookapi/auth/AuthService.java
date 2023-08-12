package Mobilise.bookapi.auth;

import Mobilise.bookapi.auth.dto.LoginDto;
import Mobilise.bookapi.auth.dto.ResetPasswordDto;
import Mobilise.bookapi.user.User;
import Mobilise.bookapi.user.dto.CreateUserDto;

import java.util.Map;

public interface AuthService {
    public Object login(LoginDto loginDto) ;

    public Map<String, Object>register(CreateUserDto createUserPayload);

    public User verifyEmail(String token);

    public Map<String, Object> resendEmail(String email);

    public String forgetPassword(String email);

    public String resetPassword(String token, ResetPasswordDto resetPasswordPayload);
}
