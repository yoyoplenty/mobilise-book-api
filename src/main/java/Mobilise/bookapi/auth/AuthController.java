package Mobilise.bookapi.auth;


import Mobilise.bookapi.auth.dto.LoginDto;
import Mobilise.bookapi.auth.dto.ResetPasswordDto;
import Mobilise.bookapi.user.dto.CreateUserDto;
import Mobilise.bookapi.user.User;
import Mobilise.bookapi.utils.handlers.Exceptions.CustomException;
import Mobilise.bookapi.utils.handlers.Responses.ResponseHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor

public class AuthController {

    private final AuthService authService;

    @PostMapping("register")
    public ResponseEntity<Object> Register(@Valid @RequestBody CreateUserDto createUserPayload) {
        try {
            Object data = authService.register(createUserPayload);

            return ResponseHandler.generateResponse("Successfully created user!", HttpStatus.OK, data);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid LoginDto loginDto) {
        try {
            Object data = authService.login(loginDto);

            return ResponseHandler.generateResponse("Successfully signed in!", HttpStatus.OK, data);
        } catch (CustomException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), ex.getErrorCode(), null);
        }
    }

    @GetMapping("verify-email/{token}")
    public ResponseEntity<Object> confirmEmail(@PathVariable String token) {
        try {
            User user = authService.verifyEmail(token);

            return ResponseHandler.generateResponse("Successfully confirmed email!", HttpStatus.OK, user);
        } catch (CustomException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), ex.getErrorCode(), null);
        }
    }

    @GetMapping("resend-email/{email}")
    public ResponseEntity<Object> resendEmail(@PathVariable String email) {
        try {
            Object data = authService.resendEmail(email);

            return ResponseHandler.generateResponse("email successfully sent!", HttpStatus.OK, data);
        } catch (CustomException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), ex.getErrorCode(), null);
        }
    }

    @GetMapping("forget-password/{email}")
    public ResponseEntity<Object> forgetPassword(@PathVariable String email) {
        try {
            Object data = authService.forgetPassword(email);

            return ResponseHandler.generateResponse("forget password email sent!", HttpStatus.OK, data);
        } catch (CustomException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), ex.getErrorCode(), null);
        }
    }

    @PatchMapping("reset-password/{token}")
    public ResponseEntity<Object> resetPassword(@PathVariable String token, @RequestBody @Valid ResetPasswordDto resetPasswordPayload) {
        try {
            Object data = authService.resetPassword(token, resetPasswordPayload);

            return ResponseHandler.generateResponse("password reset successful", HttpStatus.OK, data);
        } catch (CustomException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), ex.getErrorCode(), null);
        }
    }
}
