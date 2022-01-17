package fr.oukilson.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String email;
    private String nickname;
    private List<UserOnListDTO> friendList;
    private List<UserOnListDTO> deniedList;

    public UserDTO(Long id, String email, String nickname) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.friendList = new ArrayList<>();
        this.deniedList = new ArrayList<>();
    }
}
