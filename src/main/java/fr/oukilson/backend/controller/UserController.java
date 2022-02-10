package fr.oukilson.backend.controller;

import fr.oukilson.backend.dto.user.UserCreationDTO;
import fr.oukilson.backend.dto.user.UserDTO;
import fr.oukilson.backend.dto.user.UserGameDTO;
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

    /**
     * Method to save a user in the database
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
     * Add the user "nickname2" in the friend list of user "nickname1"
     * @param nickname1 The user who want to remove a friend
     * @param nickname2 The friend to remove
     * @return True if added
     */
    @PutMapping("/friend/add/{friend}")
    public ResponseEntity<Boolean> addUserToFriendList(@RequestAttribute(name = "username") String nickname1,
                                                       @PathVariable(name = "friend") String nickname2) {
        return ResponseEntity.ok(this.userService.addUserToFriendList(nickname1, nickname2));
    }

    /**
     * Remove the user "nickname2" in the friend list of user "nickname1"
     * @param nickname1 The user who want to remove a friend
     * @param nickname2 The friend to remove
     * @return True if removed
     */
    @PutMapping("/friend/remove/{friend}")
    public ResponseEntity<Boolean> removeUserFromFriendList(@RequestAttribute(name = "username") String nickname1,
                                                            @PathVariable(name = "friend") String nickname2) {
        return ResponseEntity.ok(this.userService.removeUserFromFriendList(nickname1, nickname2));
    }

    /**
     * Empty the friend list of the user "nickname"
     * @param nickname Name of the user
     * @return True if emptied
     */
    @PutMapping("/friend/empty")
    public ResponseEntity<Boolean> emptyFriendList(@RequestAttribute(name = "username") String nickname) {
        return ResponseEntity.ok(this.userService.emptyFriendList(nickname));
    }

    /**
     * Add a game into the owned game list
     * @param nickname User's nickname
     * @param game DTO holding the game's uuid
     * @return True if removed
     */
    @PutMapping("/games/owned/add")
    public ResponseEntity<Boolean> addOwnedGame(@RequestBody UserGameDTO game,
                                                @RequestAttribute(name = "username") String nickname) {
        return ResponseEntity.ok(this.userService.addOwnedGame(nickname, game));
    }

    /**
     * Add a game into the liked game list
     * @param nickname User's nickname
     * @param game DTO holding the game's uuid
     * @return True if removed
     */
    @PutMapping("/games/liked/add")
    public ResponseEntity<Boolean> addLikedGame(@RequestBody UserGameDTO game,
                                                @RequestAttribute(name = "username") String nickname) {
        return ResponseEntity.ok(this.userService.addLikedGame(nickname, game));
    }

    /**
     * Remove a game from the owned game list
     * @param nickname User's nickname
     * @param game DTO holding the game's uuid
     * @return True if removed
     */
    @PutMapping("/games/owned/remove")
    public ResponseEntity<Boolean> removeOwnedGame(@RequestBody UserGameDTO game,
                                                @RequestAttribute(name = "username") String nickname) {
        return ResponseEntity.ok(this.userService.removeOwnedGame(nickname, game));
    }
    /**
     * Remove a game from the liked game list
     * @param nickname User's nickname
     * @param game DTO holding the game's uuid
     * @return True if removed
     */
    @PutMapping("/games/liked/remove")
    public ResponseEntity<Boolean> removeLikedGame(@RequestBody UserGameDTO game,
                                                @RequestAttribute(name = "username") String nickname) {
        return ResponseEntity.ok(this.userService.removeLikedGame(nickname, game));
    }
}