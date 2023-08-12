package Mobilise.bookapi.user;

import Mobilise.bookapi.user.dto.UpdateUserDto;
import Mobilise.bookapi.utils.handlers.Exceptions.CustomException;
import Mobilise.bookapi.utils.handlers.Responses.ResponseHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

      private final  UserService userService;

    /**
     * This method get all users on the system
     * @return
     */
    @GetMapping()
    public ResponseEntity<Object> findAllUsers() {
        try {
            List<User> users = userService.findAllUsers();

            return ResponseHandler.generateResponse("Successfully fetched users", HttpStatus.OK, users);
        } catch (CustomException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), ex.getErrorCode(), null);
        }
    }

    /***
     * This method finds a user by the ID provided
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> findOneUser(@PathVariable UUID id) {
        try {
            User user = userService.findUserById(id);

            return ResponseHandler.generateResponse("Successfully fetched user", HttpStatus.OK, user);
        } catch (CustomException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), ex.getErrorCode(), null);
        }
    }

    /**
     * This method updates a user by their id
     * @param updateUserPayload
     * @param id
     * @return
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateUser(@RequestBody @Valid UpdateUserDto updateUserPayload, @PathVariable UUID id) {
        try {
            User user = userService.updateUserById(updateUserPayload, id);

            return ResponseHandler.generateResponse("Successfully updated user", HttpStatus.OK, user);
        } catch (CustomException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), ex.getErrorCode(), null);
        }
    }

    /**
     * This method deletes a user by their ID
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable UUID id) {
        try {
            Object user = userService.deleteUserById(id);

            return ResponseHandler.generateResponse("Successfully deleted user", HttpStatus.OK, user);
        } catch (CustomException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), ex.getErrorCode(), null);
        }
    }
}
