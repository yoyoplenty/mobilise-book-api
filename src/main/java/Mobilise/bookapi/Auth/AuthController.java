package Mobilise.bookapi.Auth;


import Mobilise.bookapi.User.Dto.CreateUserDto;
import Mobilise.bookapi.User.User;
import Mobilise.bookapi.User.UserServiceImpl;
import Mobilise.bookapi.Utils.Handlers.Responses.ResponseHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    @Autowired
    UserServiceImpl userService;

    @PostMapping("register")
    public ResponseEntity<Object> Register(@Valid @RequestBody CreateUserDto createUserPayload) {
        try {
            User user = userService.createUser(createUserPayload);

            return ResponseHandler.generateResponse("Successfully created user!", HttpStatus.OK, user);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        }
    }
}
