package fr.oukilson.backend.service;

import fr.oukilson.backend.dto.event.*;
import fr.oukilson.backend.entity.Event;
import fr.oukilson.backend.entity.Game;
import fr.oukilson.backend.entity.Location;
import fr.oukilson.backend.entity.User;
import fr.oukilson.backend.repository.EventRepository;
import fr.oukilson.backend.repository.GameRepository;
import fr.oukilson.backend.repository.LocationRepository;
import fr.oukilson.backend.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;

public class EventService {
    private final EventRepository repository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final LocationRepository locationRepository;
    private final ModelMapper mapper;

    public EventService(EventRepository repository, UserRepository userRepository, GameRepository gameRepository,
                        LocationRepository locationRepository, ModelMapper mapper) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.locationRepository = locationRepository;
        this.mapper = mapper;
    }

    /**
     * Find an event by its uuid and return all its info as a EventDTO
     * @param uuid Event's uuid
     * @return EventDTO
     */
    public EventDTO findByUuid(String uuid) {
        Event event =  this.repository.findByUuid(uuid).orElse(null);
        EventDTO result;
        if (event==null)
            result = null;
        else
            result = this.mapper.map(event, EventDTO.class);
        return result;
    }

    /**
     * Delete an event by its uuid
     * @param uuid Event's uuid
     */
    @Transactional
    public void deleteByUuid(String uuid, String username) {
        // TODO soft delete
        this.repository.deleteByUuid(uuid);
    }

    /**
     * Add a new event in DB
     * @param toCreate The event to add
     * @return The created event
     */
    public EventDTO save(EventCreateDTO toCreate, String creatorName)
            throws NoSuchElementException, IllegalArgumentException, NullPointerException {
        // Check data
        LocalDateTime rightNow = LocalDateTime.now();
        if (!toCreate.isValid(rightNow))
            throw new IllegalArgumentException("Event creation : Invalid parameter data.");

        // Get the user creator and the game
        Event event = this.mapper.map(toCreate, Event.class);
        event.setCreationDate(rightNow);
        event.setUuid(UUID.randomUUID().toString());
        try {
            Optional<User> user = this.userRepository.findByNickname(creatorName);
            event.setCreator(user.get());
            Optional<Game> game = this.gameRepository.findByUuid(toCreate.getGame().getUuid());
            event.setGame(game.get());
        }
        catch (Exception e) {
            throw new NoSuchElementException("Event creation : Unknown user/game");
        }

        // Save and return
        event.getLocation().setEvent(event);
        Location location = this.locationRepository.save(event.getLocation());
        event.setLocation(location);
        return this.mapper.map(event, EventDTO.class);
    }

    /**
     * Update an existing event.
     * @param toUpdate The event to update
     * @return The updated event
     */
    public EventDTO update(EventUpdateDTO toUpdate, String creatorName)
            throws NoSuchElementException, IllegalArgumentException, NullPointerException {
        // Find the event to update
        Event event = this.repository.findByUuid(toUpdate.getUuid()).orElse(null);
        if (event==null)
            throw new NoSuchElementException("Event update : Unknown event");

        // Check data
        if (!creatorName.equals(event.getCreator().getNickname()) || !toUpdate.isValid(event.getCreationDate()))
            throw new IllegalArgumentException("Event update : Invalid parameter data.");

        // Update attribute
        String oldGameUuid = event.getGame().getUuid();
        this.mapper.map(toUpdate, event);

        // If the event's game has been modified, updated it
        if (!oldGameUuid.equals(toUpdate.getGame().getUuid())) {
            Optional<Game> optionalGame = this.gameRepository.findByUuid(toUpdate.getGame().getUuid());
            if (optionalGame.isPresent()) {
                Game game = optionalGame.get();
                event.setGame(game);
                this.repository.save(event);
            }
            else
                throw new NoSuchElementException("Event update : Unknown game");
        }
        else
            this.repository.save(event);

        return this.mapper.map(event, EventDTO.class);
    }

    /**
     * Search for events by one of this two options :
     * - date after the provided date
     * - happening in a town
     * If both filters are used, the date will be default choice.
     * @param date Date in a string format
     * @param town Town's name
     * @return List<EventDTO>
     */
    public List<EventDTO> findByFilter(String date, String town) {
        // Get events
        List<Event> events;
        if (date!=null && !date.isBlank()) {
            LocalDateTime pointInTime;
            try {
                pointInTime = LocalDateTime.parse(date);
                events = this.repository.findAllByStartingDateAfter(pointInTime);
            }
            catch (Exception e) {
                if (town!=null && !town.isBlank())
                    events = this.repository.findAllByLocationTownContaining(town);
                else
                    events = new ArrayList<>();
            }
        } else if (town!=null && !town.isBlank()) {
            events = this.repository.findAllByLocationTownContaining(town);
        } else
            events = new ArrayList<>();

        // Construct result
        List<EventDTO> result = new ArrayList<>();
        events.forEach(e -> result.add(this.mapper.map(e, EventDTO.class)));
        return result;
    }

    /**
     * Try to add a user in the correct event's list (attending list or waiting list).
     * @param eventUuid Uuid of the vent
     * @param username Username of the user to add to the event
     * @return EventAddingResultDTO
     * @throws IllegalArgumentException Throw when any data are invalid or not found in database
     */
    public EventAddingResultDTO addUserInEvent(String eventUuid, String username)
            throws IllegalArgumentException {
        EventAddingResultDTO result = new EventAddingResultDTO();
        // Check data
        if (eventUuid==null || username==null )
            throw new IllegalArgumentException("Event attending list : Invalid parameter data.");

        // Find user and event
        Event event = this.repository.findByUuid(eventUuid).orElse(null);
        if (event==null) throw new IllegalArgumentException("Event attending list : Invalid parameter data.");
        User user = this.userRepository.findByNickname(username).orElse(null);
        if (user == null) throw new IllegalArgumentException("Event attending list : Invalid parameter data.");

        // Try to add the user in the attending list or waiting list
        if (event.addUser(user)) {
            result.setStatus("OK");
            this.repository.save(event);
        }
        else if (event.addUserInWaitingQueue(user)) {
            result.setStatus("WT");
            this.repository.save(event);
        }
        else result.setStatus("KO");
        return result;
    }

    /**
     * Try to remove a user who is registered in an event
     * @param eventUuid The uuid of the event
     * @param username The username of the user
     * @return True if remove
     * @throws IllegalArgumentException Throw when any data are invalid or not found in database
     */
    public boolean removeUserInEvent(String eventUuid, String username) throws IllegalArgumentException {
        boolean result;
        // Check data
        if (eventUuid==null || username==null )
            throw new IllegalArgumentException("Event attending list : Invalid parameter data.");

        // Find user and event
        Event event = this.repository.findByUuid(eventUuid).orElse(null);
        if (event==null) throw new IllegalArgumentException("Event attending list : Invalid parameter data.");
        User user = this.userRepository.findByNickname(username).orElse(null);
        if (user == null) throw new IllegalArgumentException("Event attending list : Invalid parameter data.");

        // Try to remove the user in the attending list or waiting list
        result = event.removeUser(user);
        if (result) this.repository.save(event);
        return result;
    }
}