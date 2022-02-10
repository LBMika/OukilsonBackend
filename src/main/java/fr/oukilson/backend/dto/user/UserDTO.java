package fr.oukilson.backend.dto.user;

import fr.oukilson.backend.dto.game.GameUuidDTO;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String nickname;
    private List<UserNameDTO> friendList;
    private List<UserEventDTO> eventList;
    private List<GameUuidDTO> ownedGame;
    private List<GameUuidDTO> likedGame;
}