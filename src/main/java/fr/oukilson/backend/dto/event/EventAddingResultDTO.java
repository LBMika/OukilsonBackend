package fr.oukilson.backend.dto.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventAddingResultDTO {
    // "OK" -> user added in event's attending list
    // "WT" -> user added in event's waiting list
    // "KO" -> user not added in the event
    private String status;
}
