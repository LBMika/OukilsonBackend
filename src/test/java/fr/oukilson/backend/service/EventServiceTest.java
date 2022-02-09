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
import fr.oukilson.backend.util.TestingToolBox;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.ArgumentMatchers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.time.LocalDateTime;
import java.util.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class EventServiceTest {
    @MockBean
    private EventRepository repository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private GameRepository gameRepository;
    @MockBean
    private LocationRepository locationRepository;
    @Autowired
    private ModelMapper mapper;
    private EventService service;

    @BeforeAll
    public void init() {
        service = new EventService(repository, userRepository, gameRepository, locationRepository, mapper);
    }

    // Method findByUuid

    /**
     * Testing when providing an unknown uuid for the event
     */
    @DisplayName("Test : find an event with an invalid UUID")
    @Test
    public void testFindByUuidWithWrongUuid() {
        Assertions.assertNull(this.service.findByUuid("00000000000000"));
    }

    /**
     * Testing when providing a correct uuid for the search
     */
    @DisplayName("Test : find an event in database by its uuid")
    @Test
    public void testFindByUuid() {
        // Mock event
        Game game = TestingToolBox.createValidFullGame(1L, "Inis");
        User user = TestingToolBox.createValidFullUser(1L, "toto");
        Location location = new Location(1L, "Euralille", "59777", "1 Place François Mitterrand", null);
        Event event = TestingToolBox.createValidEvent(1L, game, user, location);
        location.setEvent(event);
        BDDMockito.when(this.repository.findByUuid(event.getUuid())).thenReturn(Optional.of(event));

        // Get the event
        EventDTO eventDTO = this.service.findByUuid(event.getUuid());

        // Assert
        Assertions.assertNotNull(eventDTO);
        EventDTO eventInDB = this.mapper.map(event, EventDTO.class);
        Assertions.assertEquals(eventInDB, eventDTO);
    }

    // Method findByFilter

    /**
     * Testing for search event method by giving a town filter.
     * Should return all events in the given town.
     */
    @DisplayName("Test : find all events, town only, no date")
    @Test
    public void testFindAllEventsByTownOnly() {
        // Setting up
        List<Event> events = new LinkedList<>();
        int size = 4;
        String town = "Lyon";
        for (int i=0; i<size; i++) {
            User user = TestingToolBox.createValidFullUser((long)i, "Nom"+i);
            Game game = TestingToolBox.createValidFullGame((long)i, "Jeu "+i);
            Location loc = new Location((long)i, town, null, null, null);
            Event event = TestingToolBox.createValidEvent((long)i, game, user, loc);
            loc.setEvent(event);
            events.add(event);
        }
        BDDMockito.when(this.repository.findAllByLocationTownContaining(town)).thenReturn(events);
        BDDMockito.when(this.repository.findAllByStartingDateAfter(ArgumentMatchers.any(LocalDateTime.class)))
                .thenReturn(new LinkedList<>());

        List<EventDTO> result = this.service.findByFilter("", town);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(events.size(), result.size());
        for (int i=0; i<size; i++) {
            Assertions.assertEquals(this.mapper.map(events.get(i), EventDTO.class), result.get(i));
        }
    }

    /**
     * Testing for search event method by giving a date filter.
     * Must return all events after the given date.
     */
    @DisplayName("Test : find all events, date only, no town")
    @Test
    public void testFindAllEventsByDateAfterOnly() {
        // Setting up
        List<Event> events = new LinkedList<>();
        int size = 8;
        for (int i=0; i<size; i++) {
            User user = TestingToolBox.createValidFullUser((long)i, "Nom"+i);
            Game game = TestingToolBox.createValidFullGame((long)i, "Jeu "+i);
            Location loc = new Location((long)i, "Ville "+i, null, null, null);
            Event event = TestingToolBox.createValidEvent((long)i, game, user, loc);
            loc.setEvent(event);
            events.add(event);
        }
        LocalDateTime date = events.get(0).getStartingDate().minusYears(1);
        BDDMockito.when(this.repository.findAllByLocationTownContaining(ArgumentMatchers.anyString()))
                .thenReturn(new LinkedList<>());
        BDDMockito.when(this.repository.findAllByStartingDateAfter(date)).thenReturn(events);

        List<EventDTO> result = this.service.findByFilter(date.toString(), "");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(events.size(), result.size());
        for (int i=0; i<size; i++) {
            Assertions.assertEquals(this.mapper.map(events.get(i), EventDTO.class), result.get(i));
        }
    }

    /**
     * Testing for search event method by giving empty filters.
     * Must return an empty list.
     */
    @DisplayName("Test : find all events, empty date & town")
    @Test
    public void testFindAllEventsWithEmptyFilters() {
        EventSearchDTO toSearch = new EventSearchDTO();
        List<EventDTO> result = this.service.findByFilter("", "");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());
    }

    /**
     * Testing for search event method by giving no filters.
     * Should return an empty list
     */
    @DisplayName("Test : search when date is null")
    @Test
    public void testFindAllEventsWithNullDate() {
        EventSearchDTO toSearch = new EventSearchDTO();
        List<EventDTO> result = this.service.findByFilter(null, null);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());
    }

    /**
     * Testing for search event method by giving null filters.
     * Should return an empty list
     */
    @DisplayName("Test : search when all filters are null")
    @Test
    public void testFindAllEventsWithNullDateAndNullTown() {
        EventSearchDTO toSearch = new EventSearchDTO();
        List<EventDTO> result = this.service.findByFilter(null, null);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());
    }

    /**
     * Testing for search event method by giving both town and date filters.
     * If both are present, then the date filter takes priority.
     */
    @DisplayName("Test : find events when town & date filters are initialized")
    @Test
    public void testFindAllEventsWithBothDateAndTownGiven() {
        String town = "Nancy";
        List<Event> townEvents = new LinkedList<>();
        List<Event> dateEvents = new LinkedList<>();
        int size = 3;
        for (int i=0; i<size; i++) {
            User user = TestingToolBox.createValidFullUser((long)i, "Nom"+i);
            Game game = TestingToolBox.createValidFullGame((long)i, "Jeu "+i);
            Location loc = new Location((long)i, "Ville "+i, null, null, null);
            Event event = TestingToolBox.createValidEvent((long)i, game, user, loc);
            loc.setEvent(event);
            dateEvents.add(event);
        }
        LocalDateTime date = dateEvents.get(0).getStartingDate().minusYears(1);
        size += 2;
        Location loc = new Location((long) size, town, null, null, null);
        for (int i=0; i<size; i++) {
            User user = TestingToolBox.createValidFullUser(2L *size+i, "NomBis"+i);
            Game game = TestingToolBox.createValidFullGame(2L *size+i, "JeuBis"+i);
            Event event = TestingToolBox.createValidEvent(2L *size+i, game, user, loc);
            loc.setEvent(event);
            townEvents.add(event);
        }

        BDDMockito.when(this.repository.findAllByLocationTownContaining(town)).thenReturn(townEvents);
        BDDMockito.when(this.repository.findAllByStartingDateAfter(date)).thenReturn(dateEvents);

        List<EventDTO> result = this.service.findByFilter(date.toString(), town);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(dateEvents.size(), result.size());
        Assertions.assertNotEquals(townEvents.size(), result.size());
        for (int i=0; i<dateEvents.size(); i++) {
            Assertions.assertEquals(this.mapper.map(dateEvents.get(i), EventDTO.class), result.get(i));
        }
    }

    // Method save

    /**
     * Test save : data is null
     */
    @DisplayName("Test event creation : null data")
    @Test
    public void testSaveNullData() {
        Assertions.assertThrows(NullPointerException.class, () -> this.service.save(null, ""));
    }

    /**
     * Test save : username is null
     */
    @DisplayName("Test event creation : null username")
    @Test
    public void testSaveNullUsername() {
        // Mocking
        Game game = TestingToolBox.createValidFullGame(10L, "Innovation");
        User user = TestingToolBox.createValidFullUser(10L, "SuperAlbert");
        Location location = new Location(10L, "Gan", "64290", "123 Rue d'Ossau", null);
        Event event = TestingToolBox.createValidEvent(10L, game, user, location);
        location.setEvent(event);
        BDDMockito.when(this.repository.save(ArgumentMatchers.any(Event.class))).thenReturn(event);
        BDDMockito.when(this.userRepository.findByNickname(user.getNickname())).thenReturn(Optional.of(user));
        BDDMockito.when(this.gameRepository.findByUuid(game.getUuid())).thenReturn(Optional.of(game));
        BDDMockito.when(this.locationRepository.save(ArgumentMatchers.any(Location.class))).thenReturn(location);

        // Testing
        EventCreateDTO data = this.mapper.map(event, EventCreateDTO.class);
        Assertions.assertThrows(NoSuchElementException.class, () -> this.service.save(data, null));
    }

    /**
     * Test save : data is invalid
     */
    @DisplayName("Test event creation : invalid data")
    @Test
    public void testSaveInvalidData() {
        // Create a valid data
        Game game = TestingToolBox.createValidFullGame(10L, "Innovation");
        User user = TestingToolBox.createValidFullUser(10L, "SuperAlbert");
        Location location = new Location(10L, "Gan", "64290", "123 Rue d'Ossau", null);
        Event event = TestingToolBox.createValidEvent(10L, game, user, location);
        location.setEvent(event);
        EventCreateDTO data = this.mapper.map(event, EventCreateDTO.class);

        // Testing
        this.testInvalidTitle(data);
        this.testInvalidDescription(data);
        this.testInvalidPlayerCount(data);
        this.testInvalidLocation(data);
        this.testInvalidDate(data);
        this.testInvalidGame(data);
    }

    /**
     * Check if the event creation failed with a IllegalArgumentException when the title is null.
     * The parameter is a valid EventCreateDTO which will be altered according to the test purposes
     * @param data A valid EventCreateDTO
     */
    public void testInvalidTitle(EventCreateDTO data) {
        data.setTitle(null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.service.save(data, ""));
    }

    /**
     * Check if the event creation failed with a IllegalArgumentException when the game object is null
     * or if the game's uuid is null.
     * The parameter is a valid EventCreateDTO which will be altered according to the test purposes
     * @param data A valid EventCreateDTO
     */
    public void testInvalidGame(EventCreateDTO data) {
        data.getGame().setUuid(null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.service.save(data, ""));
        data.setGame(null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.service.save(data, ""));
    }

    /**
     * Check if the event creation failed with a IllegalArgumentException when the description is null.
     * The parameter is a valid EventCreateDTO which will be altered according to the test purposes
     * @param data A valid EventCreateDTO
     */
    public void testInvalidDescription(EventCreateDTO data) {
        data.setDescription(null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.service.save(data, ""));
    }

    /**
     * Check if the event creation failed with a IllegalArgumentException when the player count is invalid;
     * meaning to low minimal player count or the minimal player count is higher than the maximal player count.
     * The parameter is a valid EventCreateDTO which will be altered according to the test purposes
     * @param data A valid EventCreateDTO
     */
    public void testInvalidPlayerCount(EventCreateDTO data) {
        data.setMinPlayer(1);
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.service.save(data, ""));
        data.setMinPlayer(data.getMaxPlayer()+1);
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.service.save(data, ""));
    }

    /**
     * Check if the event creation failed with a IllegalArgumentException when the location object is null
     * or the town's location is null.
     * The parameter is a valid EventCreateDTO which will be altered according to the test purposes
     * @param data A valid EventCreateDTO
     */
    public void testInvalidLocation(EventCreateDTO data) {
        data.getLocation().setTown(null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.service.save(data, ""));
        data.setLocation(null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.service.save(data, ""));
    }

    /**
     * Check if the event creation failed with a IllegalArgumentException when any provided dates are invalid.
     * Limit date must be before creation date.
     * Starting date must be after limit date.
     * Ending date must be after starting date.
     * Ending date can be null
     * The parameter is a valid EventCreateDTO which will be altered according to the test purposes
     * @param data A valid EventCreateDTO
     */
    public void testInvalidDate(EventCreateDTO data) {
        LocalDateTime temp;

        // Limit data
        temp = data.getLimitDate();
        data.setLimitDate(null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.service.save(data, ""));
        data.setLimitDate(LocalDateTime.now().plusDays(1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.service.save(data, ""));
        data.setLimitDate(data.getStartingDate().plusDays(1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.service.save(data, ""));
        data.setLimitDate(temp);

        // Starting date
        temp = data.getStartingDate();
        data.setStartingDate(null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.service.save(data, ""));
        data.setStartingDate(data.getEndingDate().plusDays(1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.service.save(data, ""));
        data.setStartingDate(temp);
    }

    /**
     * Test save : can't find user in database
     */
    @DisplayName("Test event creation : unknown user")
    @Test
    public void testSaveUnknownUser() {
        // Mocking
        Game game = TestingToolBox.createValidFullGame(10L, "Innovation");
        User user = TestingToolBox.createValidFullUser(10L, "SuperAlbert");
        Location location = new Location(10L, "Gan", "64290", "123 Rue d'Ossau", null);
        Event event = TestingToolBox.createValidEvent(10L, game, user, location);
        location.setEvent(event);
        BDDMockito.when(this.repository.save(ArgumentMatchers.any(Event.class))).thenReturn(event);
        BDDMockito.when(this.userRepository.findByNickname(user.getNickname())).thenReturn(Optional.empty());
        BDDMockito.when(this.gameRepository.findByUuid(game.getUuid())).thenReturn(Optional.of(game));
        BDDMockito.when(this.locationRepository.save(ArgumentMatchers.any(Location.class))).thenReturn(location);

        // Testing
        EventCreateDTO toCreate = this.mapper.map(event, EventCreateDTO.class);
        Assertions.assertThrows(NoSuchElementException.class, () -> this.service.save(toCreate, user.getNickname()));
    }

    /**
     * Test save : can't find game in database
     */
    @DisplayName("Test event creation : unknown game")
    @Test
    public void testSaveUnknownGame() {
        // Mocking
        Game game = TestingToolBox.createValidFullGame(10L, "Innovation");
        User user = TestingToolBox.createValidFullUser(10L, "SuperAlbert");
        Location location = new Location(10L, "Gan", "64290", "123 Rue d'Ossau", null);
        Event event = TestingToolBox.createValidEvent(10L, game, user, location);
        location.setEvent(event);
        BDDMockito.when(this.repository.save(ArgumentMatchers.any(Event.class))).thenReturn(event);
        BDDMockito.when(this.userRepository.findByNickname(user.getNickname())).thenReturn(Optional.of(user));
        BDDMockito.when(this.gameRepository.findByUuid(game.getUuid())).thenReturn(Optional.empty());
        BDDMockito.when(this.locationRepository.save(ArgumentMatchers.any(Location.class))).thenReturn(location);

        // Testing
        EventCreateDTO toCreate = this.mapper.map(event, EventCreateDTO.class);
        Assertions.assertThrows(NoSuchElementException.class, () -> this.service.save(toCreate, user.getNickname()));
    }

    /**
     * Test save : everything is ok
     */
    @DisplayName("Test event creation : everything is ok")
    @Test
    public void testSave() {
        // Mocking
        Game game = TestingToolBox.createValidFullGame(10L, "Innovation");
        User user = TestingToolBox.createValidFullUser(10L, "SuperAlbert");
        Location location = new Location(10L, "Gan", "64290", "123 Rue d'Ossau", null);
        Event event = TestingToolBox.createValidEvent(10L, game, user, location);
        location.setEvent(event);
        BDDMockito.when(this.repository.save(ArgumentMatchers.any(Event.class))).thenReturn(event);
        BDDMockito.when(this.userRepository.findByNickname(user.getNickname())).thenReturn(Optional.of(user));
        BDDMockito.when(this.gameRepository.findByUuid(game.getUuid())).thenReturn(Optional.of(game));
        BDDMockito.when(this.locationRepository.save(ArgumentMatchers.any(Location.class))).thenReturn(location);

        // Testing
        EventCreateDTO toCreate = this.mapper.map(event, EventCreateDTO.class);
        EventDTO result = null;
        try {
            result = this.service.save(toCreate, user.getNickname());
        }
        finally {
            Assertions.assertNotNull(result);
            event.setUuid(result.getUuid());
            event.setCreationDate(result.getCreationDate());
            Assertions.assertEquals(this.mapper.map(event, EventDTO.class), result);
        }
    }

    // Method update

    /**
     * Test update : data is null
     */
    @DisplayName("Test event update : null data")
    @Test
    public void testUpdateNullData() {
        Assertions.assertThrows(NullPointerException.class, () -> this.service.update(null, ""));
    }

    /**
     * Test update : data is invalid
     */
    @DisplayName("Test event update : data is invalid")
    @Test
    public void testUpdateInvalidData() {
        // Mocking
        Game game = TestingToolBox.createValidFullGame(10L, "Innovation");
        User user = TestingToolBox.createValidFullUser(10L, "SuperAlbert");
        Location location = new Location(10L, "Gan", "64290", "123 Rue d'Ossau", null);
        Event event = TestingToolBox.createValidEvent(10L, game, user, location);
        location.setEvent(event);
        BDDMockito.when(this.repository.save(ArgumentMatchers.any(Event.class))).thenReturn(event);
        BDDMockito.when(this.userRepository.findByNickname(user.getNickname())).thenReturn(Optional.of(user));
        BDDMockito.when(this.gameRepository.findByUuid(game.getUuid())).thenReturn(Optional.of(game));
        BDDMockito.when(this.locationRepository.save(ArgumentMatchers.any(Location.class))).thenReturn(location);
        BDDMockito.when(this.repository.findByUuid(event.getUuid())).thenReturn(Optional.of(event));

        // Testing
        EventUpdateDTO data = this.mapper.map(event, EventUpdateDTO.class);
        this.testInvalidDate(data);
        this.testInvalidGame(data);
        this.testInvalidDescription(data);
        this.testInvalidLocation(data);
        this.testInvalidTitle(data);
        this.testInvalidPlayerCount(data);
    }

    /**
     * Check if the event creation failed with a IllegalArgumentException when the title is null.
     * The parameter is a valid EventCreateDTO which will be altered according to the test purposes
     * @param data A valid EventCreateDTO
     */
    public void testInvalidTitle(EventUpdateDTO data) {
        data.setTitle(null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.service.update(data, ""));
    }

    /**
     * Check if the event creation failed with a IllegalArgumentException when the game object is null
     * or if the game's uuid is null.
     * The parameter is a valid EventCreateDTO which will be altered according to the test purposes
     * @param data A valid EventCreateDTO
     */
    public void testInvalidGame(EventUpdateDTO data) {
        data.getGame().setUuid(null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.service.update(data, ""));
        data.setGame(null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.service.update(data, ""));
    }

    /**
     * Check if the event creation failed with a IllegalArgumentException when the description is null.
     * The parameter is a valid EventCreateDTO which will be altered according to the test purposes
     * @param data A valid EventCreateDTO
     */
    public void testInvalidDescription(EventUpdateDTO data) {
        data.setDescription(null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.service.update(data, ""));
    }

    /**
     * Check if the event creation failed with a IllegalArgumentException when the player count is invalid;
     * meaning to low minimal player count or the minimal player count is higher than the maximal player count.
     * The parameter is a valid EventCreateDTO which will be altered according to the test purposes
     * @param data A valid EventCreateDTO
     */
    public void testInvalidPlayerCount(EventUpdateDTO data) {
        data.setMinPlayer(1);
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.service.update(data, ""));
        data.setMinPlayer(data.getMaxPlayer()+1);
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.service.update(data, ""));
    }

    /**
     * Check if the event creation failed with a IllegalArgumentException when the location object is null
     * or the town's location is null.
     * The parameter is a valid EventCreateDTO which will be altered according to the test purposes
     * @param data A valid EventCreateDTO
     */
    public void testInvalidLocation(EventUpdateDTO data) {
        data.getLocation().setTown(null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.service.update(data, ""));
        data.setLocation(null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.service.update(data, ""));
    }

    /**
     * Check if the event creation failed with a IllegalArgumentException when any provided dates are invalid.
     * Limit date must be before creation date.
     * Starting date must be after limit date.
     * Ending date must be after starting date.
     * Ending date can be null
     * The parameter is a valid EventCreateDTO which will be altered according to the test purposes
     * @param data A valid EventCreateDTO
     */
    public void testInvalidDate(EventUpdateDTO data) {
        LocalDateTime temp;

        // Limit data
        temp = data.getLimitDate();
        data.setLimitDate(null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.service.update(data, ""));
        data.setLimitDate(LocalDateTime.now().plusDays(1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.service.update(data, ""));
        data.setLimitDate(data.getStartingDate().plusDays(1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.service.update(data, ""));
        data.setLimitDate(temp);

        // Starting date
        temp = data.getStartingDate();
        data.setStartingDate(null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.service.update(data, ""));
        data.setStartingDate(data.getEndingDate().plusDays(1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.service.update(data, ""));
        data.setStartingDate(temp);
    }

    /**
     * Test update : creatorName is null
     */
    @DisplayName("Test event update : creatorName is null")
    @Test
    public void testUpdateNullCreatorName() {
        // Mocking
        Game game = TestingToolBox.createValidFullGame(10L, "Innovation");
        User user = TestingToolBox.createValidFullUser(10L, "SuperAlbert");
        Location location = new Location(10L, "Gan", "64290", "123 Rue d'Ossau", null);
        Event event = TestingToolBox.createValidEvent(10L, game, user, location);
        location.setEvent(event);
        BDDMockito.when(this.repository.save(ArgumentMatchers.any(Event.class))).thenReturn(event);
        BDDMockito.when(this.userRepository.findByNickname(user.getNickname())).thenReturn(Optional.of(user));
        BDDMockito.when(this.gameRepository.findByUuid(game.getUuid())).thenReturn(Optional.of(game));
        BDDMockito.when(this.locationRepository.save(ArgumentMatchers.any(Location.class))).thenReturn(location);
        BDDMockito.when(this.repository.findByUuid(event.getUuid())).thenReturn(Optional.of(event));

        // Testing
        EventUpdateDTO data = this.mapper.map(event, EventUpdateDTO.class);
        Assertions.assertThrows(NullPointerException.class, () -> this.service.update(data, null));
    }

    /**
     * Test update : username is not the same as event's creator
     */
    @DisplayName("Test event update : username is not the same as event's creator")
    @Test
    public void testUpdateUnknownCreator() {
        // Mocking
        Game game = TestingToolBox.createValidFullGame(10L, "Innovation");
        User user = TestingToolBox.createValidFullUser(10L, "SuperAlbert");
        Location location = new Location(10L, "Gan", "64290", "123 Rue d'Ossau", null);
        Event event = TestingToolBox.createValidEvent(10L, game, user, location);
        location.setEvent(event);
        BDDMockito.when(this.repository.save(ArgumentMatchers.any(Event.class))).thenReturn(event);
        BDDMockito.when(this.userRepository.findByNickname(user.getNickname())).thenReturn(Optional.of(user));
        BDDMockito.when(this.gameRepository.findByUuid(game.getUuid())).thenReturn(Optional.of(game));
        BDDMockito.when(this.locationRepository.save(ArgumentMatchers.any(Location.class))).thenReturn(location);
        BDDMockito.when(this.repository.findByUuid(event.getUuid())).thenReturn(Optional.of(event));

        // Testing
        EventUpdateDTO data = this.mapper.map(event, EventUpdateDTO.class);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> this.service.update(data, user.getNickname()+"t"));
    }

    /**
     * Test update : new game is not in database
     */
    @DisplayName("Test event update : new game is not in database")
    @Test
    public void testUpdateUnknownGame() {
        // Mocking
        Game game = TestingToolBox.createValidFullGame(10L, "Innovation");
        User user = TestingToolBox.createValidFullUser(10L, "SuperAlbert");
        Location location = new Location(10L, "Gan", "64290", "123 Rue d'Ossau", null);
        Event event = TestingToolBox.createValidEvent(10L, game, user, location);
        location.setEvent(event);
        BDDMockito.when(this.repository.save(ArgumentMatchers.any(Event.class))).thenReturn(event);
        BDDMockito.when(this.userRepository.findByNickname(user.getNickname())).thenReturn(Optional.of(user));
        BDDMockito.when(this.locationRepository.save(ArgumentMatchers.any(Location.class))).thenReturn(location);
        BDDMockito.when(this.repository.findByUuid(event.getUuid())).thenReturn(Optional.of(event));
        BDDMockito.when(this.gameRepository.findByUuid(ArgumentMatchers.anyString())).thenReturn(Optional.empty());

        // Testing
        EventUpdateDTO data = this.mapper.map(event, EventUpdateDTO.class);
        data.getGame().setUuid(UUID.randomUUID().toString());
        Assertions.assertThrows(NoSuchElementException.class, () -> this.service.update(data, user.getNickname()));
    }

    /**
     * Test update : event is not in database
     */
    @DisplayName("Test event update : event is not in database")
    @Test
    public void testUpdateUnknownEvent() {
        // Mocking
        Game game = TestingToolBox.createValidFullGame(10L, "Innovation");
        User user = TestingToolBox.createValidFullUser(10L, "SuperAlbert");
        Location location = new Location(10L, "Gan", "64290", "123 Rue d'Ossau", null);
        Event event = TestingToolBox.createValidEvent(10L, game, user, location);
        location.setEvent(event);
        BDDMockito.when(this.repository.save(ArgumentMatchers.any(Event.class))).thenReturn(event);
        BDDMockito.when(this.userRepository.findByNickname(user.getNickname())).thenReturn(Optional.of(user));
        BDDMockito.when(this.gameRepository.findByUuid(game.getUuid())).thenReturn(Optional.of(game));
        BDDMockito.when(this.locationRepository.save(ArgumentMatchers.any(Location.class))).thenReturn(location);

        // Testing
        EventUpdateDTO data = this.mapper.map(event, EventUpdateDTO.class);
        Assertions.assertThrows(NoSuchElementException.class, () -> this.service.update(data, user.getNickname()));
    }

    /**
     * Test update : everything is ok
     */
    @DisplayName("Test event update : everything is ok")
    @Test
    public void testUpdate() {
        // Mocking
        Game game = TestingToolBox.createValidFullGame(10L, "Innovation");
        User user = TestingToolBox.createValidFullUser(10L, "SuperAlbert");
        Location location = new Location(10L, "Gan", "64290", "123 Rue d'Ossau", null);
        Event event = TestingToolBox.createValidEvent(10L, game, user, location);
        location.setEvent(event);
        BDDMockito.when(this.repository.save(ArgumentMatchers.any(Event.class))).thenReturn(event);
        BDDMockito.when(this.userRepository.findByNickname(user.getNickname())).thenReturn(Optional.of(user));
        BDDMockito.when(this.gameRepository.findByUuid(game.getUuid())).thenReturn(Optional.of(game));
        BDDMockito.when(this.locationRepository.save(ArgumentMatchers.any(Location.class))).thenReturn(location);
        BDDMockito.when(this.repository.findByUuid(event.getUuid())).thenReturn(Optional.of(event));

        // Testing
        EventUpdateDTO data = this.mapper.map(event, EventUpdateDTO.class);
        EventDTO result = this.service.update(data, user.getNickname());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(this.mapper.map(event, EventDTO.class), result);
    }

    /**
     * Test update : everything is ok when modifying game
     */
    @DisplayName("Test event update : everything is ok when modifying game")
    @Test
    public void testUpdateWithNewGame() {
        // Mocking
        Game game = TestingToolBox.createValidFullGame(10L, "Innovation");
        User user = TestingToolBox.createValidFullUser(10L, "SuperAlbert");
        Location location = new Location(10L, "Gan", "64290", "123 Rue d'Ossau", null);
        Event event = TestingToolBox.createValidEvent(10L, game, user, location);
        location.setEvent(event);
        Game newGame = TestingToolBox.createValidFullGame(50L, "7 Wonders");
        BDDMockito.when(this.repository.save(ArgumentMatchers.any(Event.class))).thenReturn(event);
        BDDMockito.when(this.userRepository.findByNickname(user.getNickname())).thenReturn(Optional.of(user));
        BDDMockito.when(this.locationRepository.save(ArgumentMatchers.any(Location.class))).thenReturn(location);
        BDDMockito.when(this.repository.findByUuid(event.getUuid())).thenReturn(Optional.of(event));
        BDDMockito.when(this.gameRepository.findByUuid(game.getUuid())).thenReturn(Optional.of(game));
        BDDMockito.when(this.gameRepository.findByUuid(newGame.getUuid())).thenReturn(Optional.of(newGame));

        // Testing
        EventUpdateDTO data = this.mapper.map(event, EventUpdateDTO.class);
        data.getGame().setUuid(newGame.getUuid());
        EventDTO result = this.service.update(data, user.getNickname());
        Assertions.assertNotNull(result);
        event.setGame(newGame);
        Assertions.assertEquals(this.mapper.map(event, EventDTO.class), result);
    }

    // Method addUserInEvent

    /**
     * Test addUserInEvent : uuid is null
     */
    @DisplayName("Test addUserInEvent : uuid is null")
    @Test
    public void testAddUserInEventNullUuid() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () ->this.service.addUserInEvent(null, "Toto"));
    }

    /**
     * Test addUserInEvent : username is null
     */
    @DisplayName("Test addUserInEvent : username is null")
    @Test
    public void testAddUserInEventNullUsername() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () ->this.service.addUserInEvent(UUID.randomUUID().toString(), null));
    }

    /**
     * Test addUserInEvent : event can't be found in database
     */
    @DisplayName("Test addUserInEvent : event can't be found in database")
    @Test
    public void testAddUserInEventUnknownEvent() {
        User user = TestingToolBox.createValidFullUser(3L, "Alpha");
        BDDMockito.when(this.userRepository.findByNickname(user.getNickname())).thenReturn(Optional.of(user));
        Assertions.assertThrows(IllegalArgumentException.class,
                () ->this.service.addUserInEvent(UUID.randomUUID().toString(), user.getNickname()));
    }

    /**
     * Test addUserInEvent : user can't be found in database
     */
    @DisplayName("Test addUserInEvent : user can't be found in database")
    @Test
    public void testAddUserInEventUnknownUser() {
        User user = TestingToolBox.createValidFullUser(3L, "Alpha");
        Game game = TestingToolBox.createValidFullGame(3L, "Mafia de Cuba");
        Location location = new Location(10L, "Brest", null, null, null);
        Event event = TestingToolBox.createValidEvent(10L, game, user, location);
        location.setEvent(event);
        BDDMockito.when(this.repository.findByUuid(event.getUuid())).thenReturn(Optional.of(event));
        Assertions.assertThrows(IllegalArgumentException.class,
                () ->this.service.addUserInEvent(event.getUuid(), "Titi"));
    }

    /**
     * Test addUserInEvent : user added to attending list
     */
    @DisplayName("Test addUserInEvent : user added to attending list")
    @Test
    public void testAddUserInEventUserAddedAttendingList() {
        // Mocking
        User user = TestingToolBox.createValidFullUser(3L, "Alpha");
        Game game = TestingToolBox.createValidFullGame(3L, "Mafia de Cuba");
        Location location = new Location(10L, "Brest", null, null, null);
        Event event = TestingToolBox.createValidEvent(10L, game, user, location);
        location.setEvent(event);
        for (int i=1; i<event.getMaxPlayer(); i++) {
            User tempUser = TestingToolBox.createValidFullUser(user.getId()+i, "User n°"+i);
            event.addUser(tempUser);
        }
        BDDMockito.when(this.userRepository.findByNickname(user.getNickname())).thenReturn(Optional.of(user));
        BDDMockito.when(this.repository.findByUuid(event.getUuid())).thenReturn(Optional.of(event));

        // Testing
        EventAddingResultDTO result = null;
        Assertions.assertNotEquals(event.getRegisteredUsers().size(), event.getMaxPlayer());
        Assertions.assertFalse(event.getRegisteredUsers().stream()
                                    .anyMatch(u -> u.getNickname().equals(user.getNickname())));
        try {
            result = this.service.addUserInEvent(event.getUuid(), user.getNickname());
        }
        catch (Exception e) {}
        Assertions.assertNotNull(result);
        Assertions.assertEquals("OK", result.getStatus());
        Assertions.assertEquals(event.getRegisteredUsers().size(), event.getMaxPlayer());
        Assertions.assertTrue(event.getRegisteredUsers().stream()
                                    .anyMatch(u -> u.getNickname().equals(user.getNickname())));
    }

    /**
     * Test addUserInEvent : user added to waiting list
     */
    @DisplayName("Test addUserInEvent : user added to waiting list")
    @Test
    public void testAddUserInEventUserAddedWaitingList() {
        // Mocking
        User user = TestingToolBox.createValidFullUser(3L, "Alpha");
        Game game = TestingToolBox.createValidFullGame(3L, "Mafia de Cuba");
        Location location = new Location(10L, "Brest", null, null, null);
        Event event = TestingToolBox.createValidEvent(10L, game, user, location);
        location.setEvent(event);
        for (int i=0; i<event.getMaxPlayer(); i++) {
            User tempUser = TestingToolBox.createValidFullUser(user.getId()+i, "User n°"+i);
            event.addUser(tempUser);
        }
        BDDMockito.when(this.userRepository.findByNickname(user.getNickname())).thenReturn(Optional.of(user));
        BDDMockito.when(this.repository.findByUuid(event.getUuid())).thenReturn(Optional.of(event));

        // Testing
        EventAddingResultDTO result = null;
        Assertions.assertEquals(event.getRegisteredUsers().size(), event.getMaxPlayer());
        Assertions.assertNotEquals(event.getWaitingUsers().size(), event.getMaxPlayer());
        Assertions.assertFalse(event.getRegisteredUsers().stream()
                .anyMatch(u -> u.getNickname().equals(user.getNickname())));
        Assertions.assertFalse(event.getWaitingUsers().stream()
                .anyMatch(u -> u.getNickname().equals(user.getNickname())));
        try {
            result = this.service.addUserInEvent(event.getUuid(), user.getNickname());
        }
        catch (Exception e) {}
        Assertions.assertNotNull(result);
        Assertions.assertEquals("WT", result.getStatus());
        Assertions.assertFalse(event.getRegisteredUsers().stream()
                .anyMatch(u -> u.getNickname().equals(user.getNickname())));
        Assertions.assertTrue(event.getWaitingUsers().stream()
                .anyMatch(u -> u.getNickname().equals(user.getNickname())));
    }

    /**
     * Test addUserInEvent : can't add user in event (no more room)
     */
    @DisplayName("Test addUserInEvent : can't add user in event (no more room)")
    @Test
    public void testAddUserInEventFullEvent() {
        // Mocking
        User user = TestingToolBox.createValidFullUser(3L, "Alpha");
        Game game = TestingToolBox.createValidFullGame(3L, "Mafia de Cuba");
        Location location = new Location(10L, "Brest", null, null, null);
        Event event = TestingToolBox.createValidEvent(10L, game, user, location);
        location.setEvent(event);
        for (int i=0; i<event.getMaxPlayer(); i++) {
            User tempUser1 = TestingToolBox.createValidFullUser(user.getId()+i, "User n°"+i);
            event.addUser(tempUser1);
            User temptUser2 = TestingToolBox.createValidFullUser(((long) event.getMaxPlayer() *i), "User n°"+event.getMaxPlayer()*i);
            event.addUserInWaitingQueue(temptUser2);
        }
        BDDMockito.when(this.userRepository.findByNickname(user.getNickname())).thenReturn(Optional.of(user));
        BDDMockito.when(this.repository.findByUuid(event.getUuid())).thenReturn(Optional.of(event));

        // Testing
        EventAddingResultDTO result = null;
        Assertions.assertEquals(event.getRegisteredUsers().size(), event.getMaxPlayer());
        Assertions.assertEquals(event.getWaitingUsers().size(), event.getMaxPlayer());
        try {
            result = this.service.addUserInEvent(event.getUuid(), user.getNickname());
        }
        catch (Exception e) {}
        Assertions.assertNotNull(result);
        Assertions.assertEquals("KO", result.getStatus());
    }

    // Method RemoveUserInEvent

    /**
     * Test removeUserInEvent : uuid is null
     */
    @DisplayName("Test removeUserInEvent : uuid is null")
    @Test
    public void testRemoveUserInEventNullUuid() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () ->this.service.removeUserInEvent(null, "Toto"));
    }

    /**
     * Test removeUserInEvent : username is null
     */
    @DisplayName("Test removeUserInEvent : username is null")
    @Test
    public void testRemoveUserInEventNullUsername() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () ->this.service.removeUserInEvent(UUID.randomUUID().toString(), null));
    }

    /**
     * Test removeUserInEvent : event not found in database
     */
    @DisplayName("Test removeUserInEvent : event not found in database")
    @Test
    public void testRemoveUserInEventUnknownEvent() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () ->this.service.removeUserInEvent(UUID.randomUUID().toString(), "Truc"));
    }

    /**
     * Test removeUserInEvent : user not found in database
     */
    @DisplayName("Test removeUserInEvent : user not found in database")
    @Test
    public void testRemoveUserInEventUnknownUser() {
        // Mocking
        User user = TestingToolBox.createValidFullUser(3L, "Alpha");
        Game game = TestingToolBox.createValidFullGame(3L, "Mafia de Cuba");
        Location location = new Location(10L, "Brest", null, null, null);
        Event event = TestingToolBox.createValidEvent(10L, game, user, location);
        location.setEvent(event);
        BDDMockito.when(this.repository.findByUuid(event.getUuid())).thenReturn(Optional.of(event));

        // Testing
        Assertions.assertThrows(IllegalArgumentException.class,
                () ->this.service.removeUserInEvent(event.getUuid(), "Truc"));
    }

    /**
     * Test removeUserInEvent : user removed from attending list
     */
    @DisplayName("Test removeUserInEvent : user removed from attending list")
    @Test
    public void testRemoveUserInEventUserRemovedAttendingList() {
        // Mocking
        User user = TestingToolBox.createValidFullUser(3L, "Alpha");
        Game game = TestingToolBox.createValidFullGame(3L, "Mafia de Cuba");
        Location location = new Location(10L, "Brest", null, null, null);
        Event event = TestingToolBox.createValidEvent(10L, game, user, location);
        location.setEvent(event);
        Assertions.assertTrue(event.addUser(user));
        User nextInLine = TestingToolBox.createValidFullUser(500L, "Le suivant");
        Assertions.assertTrue(event.addUserInWaitingQueue(nextInLine));
        BDDMockito.when(this.repository.findByUuid(event.getUuid())).thenReturn(Optional.of(event));
        BDDMockito.when(this.userRepository.findByNickname(user.getNickname())).thenReturn(Optional.of(user));

        // Testing
        boolean result = false;
        Assertions.assertTrue(event.getRegisteredUsers().stream().anyMatch(u -> u.getNickname().equals(user.getNickname())));
        Assertions.assertTrue(event.getWaitingUsers().stream().anyMatch(u -> u.getNickname().equals(nextInLine.getNickname())));
        try { result = this.service.removeUserInEvent(event.getUuid(), user.getNickname()); } catch (Exception e) {}
        Assertions.assertTrue(result);
        Assertions.assertFalse(event.getRegisteredUsers().stream().anyMatch(u -> u.getNickname().equals(user.getNickname())));
        Assertions.assertFalse(event.getWaitingUsers().stream().anyMatch(u -> u.getNickname().equals(nextInLine.getNickname())));
        Assertions.assertTrue(event.getRegisteredUsers().stream().anyMatch(u -> u.getNickname().equals(nextInLine.getNickname())));
    }

    /**
     * Test removeUserInEvent : user removed from waiting list
     */
    @DisplayName("Test removeUserInEvent : user removed from waiting list")
    @Test
    public void testRemoveUserInEventUserRemovedWaitingList() {
        // Mocking
        User user = TestingToolBox.createValidFullUser(3L, "Alpha");
        Game game = TestingToolBox.createValidFullGame(3L, "Mafia de Cuba");
        Location location = new Location(10L, "Brest", null, null, null);
        Event event = TestingToolBox.createValidEvent(10L, game, user, location);
        location.setEvent(event);
        Assertions.assertTrue(event.addUserInWaitingQueue(user));
        BDDMockito.when(this.repository.findByUuid(event.getUuid())).thenReturn(Optional.of(event));
        BDDMockito.when(this.userRepository.findByNickname(user.getNickname())).thenReturn(Optional.of(user));

        // Testing
        boolean result = false;
        Assertions.assertTrue(event.getWaitingUsers().stream().anyMatch(u -> u.getNickname().equals(user.getNickname())));
        try { result = this.service.removeUserInEvent(event.getUuid(), user.getNickname()); } catch (Exception e) {}
        Assertions.assertTrue(result);
        Assertions.assertFalse(event.getWaitingUsers().stream().anyMatch(u -> u.getNickname().equals(user.getNickname())));
    }

    /**
     * Test removeUserInEvent : user was not in any event's list
     */
    @DisplayName("Test removeUserInEvent : user was not in any event's list")
    @Test
    public void testRemoveUserInEventUserNotInAnyEventList() {
        // Mocking
        User user = TestingToolBox.createValidFullUser(3L, "Alpha");
        Game game = TestingToolBox.createValidFullGame(3L, "Mafia de Cuba");
        Location location = new Location(10L, "Brest", null, null, null);
        Event event = TestingToolBox.createValidEvent(10L, game, user, location);
        location.setEvent(event);
        BDDMockito.when(this.repository.findByUuid(event.getUuid())).thenReturn(Optional.of(event));
        BDDMockito.when(this.userRepository.findByNickname(user.getNickname())).thenReturn(Optional.of(user));

        // Testing
        boolean result = true;
        Assertions.assertTrue(result);
        try { result = this.service.removeUserInEvent(event.getUuid(), user.getNickname()); } catch (Exception e) {}
        Assertions.assertFalse(result);
    }
}