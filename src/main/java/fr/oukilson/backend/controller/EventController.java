package fr.oukilson.backend.controller;

import fr.oukilson.backend.dto.event.*;
import fr.oukilson.backend.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/events")
@CrossOrigin(origins = "http://localhost:4200")
public class EventController {
    private final EventService service;

    public EventController(EventService service) {
        this.service = service;
    }

    /**
     * Route to get the info of the event from its uuid
     * @param uuid Event's uuid
     * @return EventDTO
     */
    @GetMapping("{uuid}")
    public ResponseEntity<EventDTO> findByUuid(@PathVariable String uuid) {
        EventDTO eventDTO = service.findByUuid(uuid);
        ResponseEntity<EventDTO> result;
        if (eventDTO==null)
            result = ResponseEntity.notFound().build();
        else
            result = ResponseEntity.ok(eventDTO);
        return result;
    }

    /**
     * Search for events by one of this two options :
     * - date after the provided date
     * - happening in a town
     * For now, if both parameter are included, only the date will be taken into consideration
     * @param date Date to look after
     * @param town Town to look
     * @return List<EventDTO>
     */
    @ResponseBody
    @GetMapping("/search")
    public List<EventDTO> findAllByFilters(@RequestParam(name="date", defaultValue = "") String date,
                                           @RequestParam(name="town", defaultValue = "") String town) {
        return this.service.findByFilter(date, town);
    }

    /**
     * Route to create a new event
     * @param toCreate Event to create
     * @return The created event
     */
    @PostMapping
    public ResponseEntity<EventDTO> save(@RequestBody EventCreateDTO toCreate,
                                         @RequestAttribute(name="username")String username) {
        ResponseEntity<EventDTO> result;
        try {
            EventDTO event = this.service.save(toCreate, username);
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
     * Route to update an existing event using its uuid
     * @param toUpdate The event to update
     * @return The updated event
     */
    @PutMapping
    public ResponseEntity<EventDTO> update(@RequestBody EventUpdateDTO toUpdate,
                                           @RequestAttribute(name="username")String username) {
        ResponseEntity<EventDTO> result;
        try {
            EventDTO event = this.service.update(toUpdate, username);
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
     * Route to delete the event by its uuid
     * @param toDelete EventDeleteDTO
     * @return Always true
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteByUuid(@RequestBody EventDeleteDTO toDelete,
                                                @RequestAttribute(name="username")String username) {
        // TODO soft delete
        this.service.deleteByUuid(toDelete.getUuid(), username);
        return ResponseEntity.ok(true);
    }

    /**
     * Route to add a user (with his nickname) in an event (with its uuid)
     * @param event EventAddUserDTO Event's info
     * @param username Username to add in the event
     * @return EventAddingResultDTO
     */
    @PutMapping("/add_user")
    public ResponseEntity<EventAddingResultDTO> addUserInEvent(@RequestBody EventAddUserDTO event,
                                                               @RequestAttribute(name="username") String username) {
        ResponseEntity<EventAddingResultDTO> result;
        EventAddingResultDTO dto;
        try {
            dto = this.service.addUserInEvent(event.getUuid(), username);
            result = ResponseEntity.ok(dto);
        }
        catch (Exception e) {
            result = ResponseEntity.badRequest().build();
        }
        return result;
    }
    /**
     * Route to remove a user (with his nickname) in an event (with its uuid)
     * @param event EventAddUserDTO Event's info
     * @param username Username to add in the event
     * @return ResponseEntity<Boolean> True if remove
     */
    @PutMapping("/remove_user")
    public ResponseEntity<Boolean> removeUserInEvent(@RequestBody EventRemoveUserDTO event,
                                                     @RequestAttribute(name="username") String username) {
        ResponseEntity<Boolean> result;
        try {
            result = ResponseEntity.ok(this.service.removeUserInEvent(event.getUuid(), username));
        }
        catch (Exception e) {
            result = ResponseEntity.badRequest().build();
        }
        return result;
    }
}
