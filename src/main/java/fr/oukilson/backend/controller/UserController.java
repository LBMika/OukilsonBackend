package fr.oukilson.backend.controller;

import fr.oukilson.backend.dto.user.UserCreationDTO;
import fr.oukilson.backend.dto.user.UserDTO;
import fr.oukilson.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Find a user and send its info back
     * @param nickname User's nickname
     * @return UserDTO
     */
    @GetMapping("{nickname}")
    public ResponseEntity<UserDTO> findUserByNickname(@PathVariable String nickname) {
        ResponseEntity<UserDTO> result;
        try {
            UserDTO userDTO = this.userService.findUserByNickname(nickname);
            if (userDTO!=null)
                result = ResponseEntity.ok(userDTO);
            else
                result = ResponseEntity.notFound().build();
        }
        catch (Exception e) {
            result = ResponseEntity.badRequest().build();
        }
        return result;
    }

    /* Method to save a user in the database
     * @param userCreationDTO the user object to be saved
     * @return ResponseEntity<UserDTO>
     */
    @PostMapping()
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserCreationDTO userCreationDTO){
        ResponseEntity<UserDTO> result;
        try {
            UserDTO event = this.userService.createUser(userCreationDTO);
            if (event!=null)
                result = ResponseEntity.status(HttpStatus.CREATED).body(event);
            else
                result = ResponseEntity.badRequest().build();
        }
        catch(Exception e) {
            result = ResponseEntity.badRequest().build();
        }
        return result;
    }
  
    /**
     * Add a user to another user's friend list, asking for both users' nickname
     * @param nickname1 Nickname of the main user
     * @param nickname2 Nickname of the friend to add
     * @return ResponseEntity<Boolean>
     */
    @PutMapping("/friend/add/{id}")
    public ResponseEntity<Boolean> addUserToFriendList(@RequestAttribute(name = "username") String nickname1,
                                                       @PathVariable(name = "id") String nickname2) {
        return ResponseEntity.ok(this.userService.addUserToFriendList(nickname1, nickname2));
    }

    /**
     * Remove a user from another user's friend list
     * @param nickname1 Nickname of the main user
     * @param nickname2 Nickname of the friend to remove
     * @return ResponseEntity<Boolean>
     */
    @PutMapping("/friend/remove/{id}")
    public ResponseEntity<Boolean> removeUserFromFriendList(@RequestAttribute(name = "username") String nickname1,
                                                            @PathVariable(name = "id") String nickname2) {
        return ResponseEntity.ok(this.userService.removeUserFromFriendList(nickname1, nickname2));
    }

    /**
     * Empties a user's friend list
     * @param nickname User's nickname
     * @return ResponseEntity<Boolean>
     */
    @PutMapping("/friend/empty")
    public ResponseEntity<Boolean> emptyFriendList(@RequestAttribute(name = "username") String nickname) {
        return ResponseEntity.ok(this.userService.emptyFriendList(nickname));
    }
}