package fr.oukilson.backend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEventDTO {
    private String uuid;
    private String title;
    private String game;
    private LocalDateTime date;
}
