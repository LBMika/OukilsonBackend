package fr.oukilson.backend.dto.user;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserConnection {
    private String nickname;
    private String password;
}
