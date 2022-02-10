package fr.oukilson.backend.controller;

import com.google.gson.*;
import fr.oukilson.backend.dto.event.*;
import fr.oukilson.backend.entity.Event;
import fr.oukilson.backend.entity.Game;
import fr.oukilson.backend.entity.Location;
import fr.oukilson.backend.entity.User;
import fr.oukilson.backend.security.SecurityEnabledSetup;
import fr.oukilson.backend.service.EventService;
import fr.oukilson.backend.util.TestingToolBox;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@WebMvcTest(controllers = EventController.class)
public class EventControllerTest extends SecurityEnabledSetup {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EventService service;
    private final String route = "/events";

    // Test findByUuid route

    /**
     * Test the method findByUuid when given an uuid which doesn't exist in the database
     */
    @DisplayName("Test : find a user who doesn't exist in database.")
    @Test
    public void testFindByUuidWhenEventDoesntExist() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(route+"/12345"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Test the method findByUuid when given a valid uuid
     */
    @DisplayName("Test : find a user who exists in database.")
    @Test
    public void testFindByUuid() throws Exception {
        // Mocking
        User user = TestingToolBox.createValidFullUser(3L, "toto");
        Game game = TestingToolBox.createValidFullGame(23L, "7 Wonders");
        Location location = new Location(620L, "Euralille", "59777", "1 Place François Mitterrand", null);
        Event event = TestingToolBox.createValidEvent(465L, game, user, location);
        location.setEvent(event);
        ModelMapper mapper = new ModelMapper();
        EventDTO eventDTO = mapper.map(event, EventDTO.class);
        Mockito.when(service.findByUuid(eventDTO.getUuid())).thenReturn(eventDTO);

        // Send request
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get(route+"/"+eventDTO.getUuid()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Assert
        Gson gson = TestingToolBox.getInitializedGSON();
        EventDTO resultDTO = gson.fromJson(result.getResponse().getContentAsString(StandardCharsets.UTF_8),
                EventDTO.class);
        Assertions.assertEquals(eventDTO, resultDTO);
    }

    // Test FindAllByFilters route

    /**
     * Test when all attributes of EventSearchDTO are null
     */
    @DisplayName("Test : find all events by filters but all filters are null")
    @Test
    public void testFindAllByFiltersWhenNoParamGiven() throws Exception {
        Gson gson = TestingToolBox.getInitializedGSON();
        this.mockMvc.perform(MockMvcRequestBuilders.get(route+"/search"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }

    /**
     * Test when the attribute startingDate of EventSearchDTO is null.
     * EventSearchDTO.town will be used for the search.
     */
    @DisplayName("Test : find all events by filters but the date filter is null")
    @Test
    public void testFindAllByFiltersWhenStartingDateIsNull() throws Exception {
        // Mocking
        String town = "Paris";
        int size = 2;
        Game game = TestingToolBox.createValidFullGame(1L, "The game");
        User user = TestingToolBox.createValidFullUser(1L, "tata");
        List<EventDTO> events = new LinkedList<>();
        ModelMapper mapper = new ModelMapper();
        for (int i=0; i<size; i++) {
            Location location =
                new Location(45L, "Paris", "75008", "Avenue des Champs Elysée", null);
            Event event = TestingToolBox.createValidEvent((long)i, game, user, location);
            event.setLocation(location);
            location.setEvent(event);
            events.add(mapper.map(event, EventDTO.class));
        }
        Mockito.when(this.service.findByFilter("", town)).thenReturn(events);

        // Send Request
        Gson gson = TestingToolBox.getInitializedGSON();
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get(route + "/search?date=&town="+town))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andReturn();

        // Assert
        EventDTO[] array = gson.fromJson(result.getResponse().getContentAsString(StandardCharsets.UTF_8),
                EventDTO[].class);
        Assertions.assertEquals(size, array.length);
        for (int i=0; i<size; i++) {
            Assertions.assertEquals(events.get(i), array[i]);
        }
    }

    /**
     * Test when the attribute town of EventSearchDTO is null.
     * EventSearchDTO.startingDate will be used for the search.
     */
    @DisplayName("Test : find all events by filters but the town filter is null")
    @Test
    public void testFindAllByFiltersWhenTownIsNull() throws Exception {
        // Setting up
        LocalDateTime mytime = LocalDateTime.now().minusMonths(2);
        List<EventDTO> events = new LinkedList<>();
        ModelMapper mapper = new ModelMapper();
        int size = 5;
        for (int i=0; i<size; i++) {
            Location location =
                    new Location((long)i, "Paris "+i, "75008", "Avenue des Champs Elysée", null);
            Game game = TestingToolBox.createValidFullGame((long)i, "Un jeu random "+i);
            User user = TestingToolBox.createValidFullUser((long)i, "un user "+i);
            Event event = TestingToolBox.createValidEvent((long)i, game, user, location);
            location.setEvent(event);
            event.setStartingDate(mytime.plusMonths(8));
            events.add(mapper.map(event, EventDTO.class));
        }
        Mockito.when(this.service.findByFilter(mytime.toString(), "")).thenReturn(events);

        // Request
        Gson gson = TestingToolBox.getInitializedGSON();
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                        .get(route + "/search?date="+ mytime +"&town="))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andReturn();

        // Assertions
        EventDTO[] array = gson.fromJson(result.getResponse().getContentAsString(StandardCharsets.UTF_8),
                EventDTO[].class);
        Assertions.assertEquals(size, array.length);
        for (int i=0; i<size; i++) {
            Assertions.assertEquals(events.get(i), array[i]);
        }
    }

    /**
     * Test when all attributes of EventSearchDTO are initialized.
     * EventSearchDTO.startingDate will be used for the search.
     */
    @DisplayName("Test : find all events by filters with all filters initialized")
    @Test
    public void testFindAllByFilters() throws Exception {
        // Setting up
        ModelMapper mapper = new ModelMapper();
        List<EventDTO> townEvents = new LinkedList<>();
        int townEventsSize = 3;
        List<EventDTO> dateEvents = new LinkedList<>();
        int dateEventsSize = townEventsSize+1;
        Game game = TestingToolBox.createValidFullGame(1L, "The game");
        User user = TestingToolBox.createValidFullUser(1L, "tata");
        String town = "Pau";
        for (int i=0; i<townEventsSize; i++) {
            Location location =
                new Location((long)i, town, "64000", "Boulevard des Pyrénées", null);
            Event event = TestingToolBox.createValidEvent((long)i, game, user, location);
            location.setEvent(event);
            townEvents.add(mapper.map(event, EventDTO.class));
        }
        for (int i=0; i<dateEventsSize; i++) {
            Location location = new Location(10L *i, "Ville "+i, null, null, null);
            Event event = TestingToolBox.createValidEvent(10L*i, game, user, location);
            location.setEvent(event);
            dateEvents.add(mapper.map(event, EventDTO.class));
        }
        String date = dateEvents.get(0).getStartingDate().minusDays(10).toString();
        Mockito.when(this.service.findByFilter(date, town)).thenReturn(dateEvents);
        Mockito.when(this.service.findByFilter(date, "")).thenReturn(dateEvents);
        Mockito.when(this.service.findByFilter("", town)).thenReturn(townEvents);

        // Request
        Gson gson = TestingToolBox.getInitializedGSON();
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                        .get(route + "/search?date="+date+"&town="+town))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andReturn();

        // Assertions
        EventDTO[] array = gson.fromJson(result.getResponse().getContentAsString(StandardCharsets.UTF_8),
                EventDTO[].class);
        Assertions.assertEquals(dateEventsSize, array.length);
        for (int i=0; i<dateEventsSize; i++) {
            Assertions.assertEquals(dateEvents.get(i), array[i]);
        }
    }

    // Test delete route

    /**
     * Test when deleting an existing event
     */
    @DisplayName("Test : delete an existing event.")
    @Test
    @WithMockUser(username = "Toto", password = "b41419df9bdaa5cd16d4766696bc486c8eca5fbcaa99a0e06bb034504f93f71a", roles = "")
    public void testDeleteByUuid() throws Exception {
        EventDeleteDTO eventDTO = new EventDeleteDTO(UUID.randomUUID().toString());
        this.mockMvc.perform(MockMvcRequestBuilders.delete(route)
                        .requestAttr("username", "Toto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(eventDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isBoolean())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("true"));
    }

    /**
     * Test when deleting a non-existing event
     */
    @DisplayName("Test : delete a non-existing event")
    @Test
    @WithMockUser(username = "Toto", password = "b41419df9bdaa5cd16d4766696bc486c8eca5fbcaa99a0e06bb034504f93f71a", roles = "")
    public void testDeleteByUuidWhenUuidNotValid() throws Exception {
        EventDeleteDTO eventDTO = new EventDeleteDTO("0");
        this.mockMvc.perform(MockMvcRequestBuilders.delete(route)
                        .requestAttr("username", "Toto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(eventDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isBoolean())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("true"));
    }

    /**
     * Test when deleting null uuid
     */
    @DisplayName("Test : delete an event when the provided uuid is null")
    @Test
    @WithMockUser(username = "Toto", password = "b41419df9bdaa5cd16d4766696bc486c8eca5fbcaa99a0e06bb034504f93f71a", roles = "")
    public void testDeleteByUuidWhenUuidIsNull() throws Exception {
        EventDeleteDTO eventDTO = new EventDeleteDTO();
        this.mockMvc.perform(MockMvcRequestBuilders.delete(route)
                        .requestAttr("username", "Toto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(eventDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isBoolean())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("true"));
    }

    // Test save route

    /**
     * Test event creation with a null body
     */
    @DisplayName("Test : saving an event with a null body")
    @Test
    @WithMockUser(username = "Toto", password = "b41419df9bdaa5cd16d4766696bc486c8eca5fbcaa99a0e06bb034504f93f71a", roles = "")
    public void testSaveNullBody() throws Exception {
        Gson gson = TestingToolBox.getInitializedGSON();
        User user = TestingToolBox.createValidFullUser(12L, "Rodolf");
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post(route)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(gson.toJson(null)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * Test event creation when the creation method throws an exception
     */
    @DisplayName("Test : event creation when the save function throws an exception")
    @Test
    @WithMockUser(username = "Toto", password = "b41419df9bdaa5cd16d4766696bc486c8eca5fbcaa99a0e06bb034504f93f71a", roles = "")
    public void testSaveCatchBranch() throws Exception {
        Mockito.when(this.service.save(ArgumentMatchers.any(EventCreateDTO.class), ArgumentMatchers.eq("")))
                .thenThrow(new RuntimeException());
        Gson gson = TestingToolBox.getInitializedGSON();
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post(route)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(gson.toJson(null)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * Test event creation when everything is ok
     */
    @DisplayName("Test : create an event with valid fields")
    @Test
    @WithMockUser(username = "Toto", password = "b41419df9bdaa5cd16d4766696bc486c8eca5fbcaa99a0e06bb034504f93f71a", roles = "")
    public void testSave() throws Exception {
        // Mocking
        ModelMapper mapper = new ModelMapper();
        User user = TestingToolBox.createValidFullUser(1L, "toto");
        user.setPassword("b41419df9bdaa5cd16d4766696bc486c8eca5fbcaa99a0e06bb034504f93f71a");
        Game game = TestingToolBox.createValidFullGame(1L, "The game");
        Location location = new Location(45L, "Pau", "64000", "Boulevard des Pyrénées", null);
        Event event = TestingToolBox.createValidEvent(1L, game, user, location);
        location.setEvent(event);
        EventDTO eventDTO = mapper.map(event, EventDTO.class);
        EventCreateDTO eventCreateDTO = mapper.map(event, EventCreateDTO.class);
        Mockito.when(service.save(ArgumentMatchers.any(EventCreateDTO.class), ArgumentMatchers.eq(user.getNickname())))
                .thenReturn(eventDTO);

        // Send request
        Gson gson = TestingToolBox.getInitializedGSON();
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                        .post(route)
                        .requestAttr("username", user.getNickname())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(gson.toJson(eventCreateDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        // Assert
        EventDTO response = gson.fromJson(result.getResponse().getContentAsString(StandardCharsets.UTF_8),
                EventDTO.class);
        Assertions.assertEquals(eventDTO, response);
    }

    /**
     * Test event creation when there's one or several invalid fields
     */
    @DisplayName("Test : create event with invalid body")
    @Test
    @WithMockUser(username = "Toto", password = "b41419df9bdaa5cd16d4766696bc486c8eca5fbcaa99a0e06bb034504f93f71a", roles = "")
    public void testSaveWhenEventCreateDTOIsInvalid() throws Exception {
        // Create but don't mock anything, must expect 400
        ModelMapper mapper = new ModelMapper();
        User user = TestingToolBox.createValidFullUser(1L, "toto");
        Game game = TestingToolBox.createValidFullGame(1L, "The game");
        Location location = new Location(45L, "Pau", "64000", "Boulevard des Pyrénées", null);
        Event event = TestingToolBox.createValidEvent(1L, game, user, location);
        location.setEvent(event);
        EventCreateDTO eventCreateDTO = mapper.map(event, EventCreateDTO.class);
        eventCreateDTO.setStartingDate(null);

        // Request
        Gson gson = TestingToolBox.getInitializedGSON();
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post(route)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(gson.toJson(eventCreateDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    // Testing update route

    /**
     * Test event update with null body
     */
    @DisplayName("Test : updating an event with a null body")
    @Test
    @WithMockUser(username = "Toto", password = "b41419df9bdaa5cd16d4766696bc486c8eca5fbcaa99a0e06bb034504f93f71a", roles = "")
    public void testUpdateNullBody() throws Exception {
        Gson gson = TestingToolBox.getInitializedGSON();
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put(route)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(gson.toJson(null)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * Test event update when the update method throws an exception
     */
    @DisplayName("Test : event update when the update function throws an exception")
    @Test
    @WithMockUser(username = "Toto", password = "b41419df9bdaa5cd16d4766696bc486c8eca5fbcaa99a0e06bb034504f93f71a", roles = "")
    public void testUpdateCatchBranch() throws Exception {
        Mockito.when(this.service.update(ArgumentMatchers.any(EventUpdateDTO.class), ArgumentMatchers.anyString()))
                .thenThrow(new RuntimeException());
        Gson gson = TestingToolBox.getInitializedGSON();
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post(route)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(gson.toJson(null)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * Test event update when everything is ok
     */
    @DisplayName("Test : update an event with valid fields")
    @Test
    @WithMockUser(username = "Toto", password = "b41419df9bdaa5cd16d4766696bc486c8eca5fbcaa99a0e06bb034504f93f71a", roles = "")
    public void testUpdate() throws Exception {
        // Mocking
        ModelMapper mapper = new ModelMapper();
        User user = TestingToolBox.createValidFullUser(1L, "toto");
        Game game = TestingToolBox.createValidFullGame(1L, "The game");
        Location location = new Location(45L, "Pau", "64000", "Boulevard des Pyrénées", null);
        Event event = TestingToolBox.createValidEvent(1L, game, user, location);
        location.setEvent(event);
        EventDTO oldEvent = mapper.map(event, EventDTO.class);
        event.setMinPlayer(4);
        EventDTO newEvent = mapper.map(event, EventDTO.class);
        EventUpdateDTO toUpdate = mapper.map(event, EventUpdateDTO.class);
        Mockito.when(service.update(ArgumentMatchers.any(EventUpdateDTO.class), ArgumentMatchers.eq(user.getNickname())))
                .thenReturn(newEvent);

        // Send request
        Gson gson = TestingToolBox.getInitializedGSON();
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                        .put(route)
                        .requestAttr("username", user.getNickname())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(gson.toJson(toUpdate)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        // Assert
        EventDTO response = gson.fromJson(result.getResponse().getContentAsString(StandardCharsets.UTF_8),
                EventDTO.class);
        Assertions.assertEquals(newEvent, response);
        Assertions.assertNotEquals(oldEvent, response);
    }

    /**
     * Test event update when there's one or several invalid fields
     */
    @DisplayName("Test : update event with invalid body")
    @Test
    @WithMockUser(username = "Toto", password = "b41419df9bdaa5cd16d4766696bc486c8eca5fbcaa99a0e06bb034504f93f71a", roles = "")
    public void testUpdateWhenEventUpdateDTOIsInvalid() throws Exception {
        // Create but don't mock anything, must expect 400
        ModelMapper mapper = new ModelMapper();
        User user = TestingToolBox.createValidFullUser(1L, "toto");
        Game game = TestingToolBox.createValidFullGame(1L, "The game");
        Location location = new Location(45L, "Pau", "64000", "Boulevard des Pyrénées", null);
        Event event = TestingToolBox.createValidEvent(1L, game, user, location);
        location.setEvent(event);
        EventUpdateDTO toUpdate = mapper.map(event, EventUpdateDTO.class);

        // Request
        Gson gson = TestingToolBox.getInitializedGSON();
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put(route)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(gson.toJson(toUpdate)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    // Method addUserInEvent

    /**
     * Test addUserInEvent : service throws exception
     */
    @DisplayName("Test addUserInEvent : service throws exception")
    @Test
    @WithMockUser(username = "Toto", password = "b41419df9bdaa5cd16d4766696bc486c8eca5fbcaa99a0e06bb034504f93f71a", roles = "")
    public void testAddUserInEventServiceException() throws Exception {
        // Mock
        Mockito.when(this.service.addUserInEvent(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                .thenThrow(new RuntimeException());

        // Assert
        Gson gson = TestingToolBox.getInitializedGSON();
        EventAddUserDTO data = new EventAddUserDTO("jjjjjjjjjjjjjjjjjjjj");
        this.mockMvc.perform(MockMvcRequestBuilders
                            .put(route+"/add_user")
                            .requestAttr("username", "Toto")
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding(StandardCharsets.UTF_8)
                            .content(gson.toJson(data)))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andReturn();
    }

    /**
     * Test addUserInEvent : user added in event
     */
    @DisplayName("Test addUserInEvent : user added in event")
    @Test
    @WithMockUser(username = "Toto", password = "b41419df9bdaa5cd16d4766696bc486c8eca5fbcaa99a0e06bb034504f93f71a", roles = "")
    public void testAddUserInEventUserAdded() throws Exception {
        // Mock
        EventAddingResultDTO status = new EventAddingResultDTO("OK");
        Mockito.when(this.service.addUserInEvent(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                .thenReturn(status);

        // Assert
        Gson gson = TestingToolBox.getInitializedGSON();
        EventAddUserDTO data = new EventAddUserDTO("jjjjjjjjjjjjjjjjjjjj");
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                                                .put(route+"/add_user")
                                                .requestAttr("username", "Toto")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .characterEncoding(StandardCharsets.UTF_8)
                                                .content(gson.toJson(data)))
                                        .andExpect(MockMvcResultMatchers.status().isOk())
                                        .andReturn();
        EventAddingResultDTO response = gson.fromJson(result.getResponse().getContentAsString(StandardCharsets.UTF_8),
                EventAddingResultDTO.class);
        Assertions.assertEquals(status, response);
    }

    /**
     * Test addUserInEvent : user added in waiting list
     */
    @DisplayName("Test addUserInEvent : user added in waiting list")
    @Test
    @WithMockUser(username = "Toto", password = "b41419df9bdaa5cd16d4766696bc486c8eca5fbcaa99a0e06bb034504f93f71a", roles = "")
    public void testAddUserInEventUserAddedInWaiting() throws Exception {
        // Mock
        EventAddingResultDTO status = new EventAddingResultDTO("WT");
        Mockito.when(this.service.addUserInEvent(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                .thenReturn(status);

        // Assert
        Gson gson = TestingToolBox.getInitializedGSON();
        EventAddUserDTO data = new EventAddUserDTO("jjjjjjjjjjjjjjjjjjjj");
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                        .put(route+"/add_user")
                        .requestAttr("username", "Toto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(gson.toJson(data)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        EventAddingResultDTO response = gson.fromJson(result.getResponse().getContentAsString(StandardCharsets.UTF_8),
                EventAddingResultDTO.class);
        Assertions.assertEquals(status, response);
    }

    /**
     * Test addUserInEvent : user not add in event
     */
    @DisplayName("Test addUserInEvent :")
    @Test
    @WithMockUser(username = "Toto", password = "b41419df9bdaa5cd16d4766696bc486c8eca5fbcaa99a0e06bb034504f93f71a", roles = "")
    public void testAddUserInEventUserNotAdded() throws Exception {
        // Mock
        EventAddingResultDTO status = new EventAddingResultDTO("KO");
        Mockito.when(this.service.addUserInEvent(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                .thenReturn(status);

        // Assert
        Gson gson = TestingToolBox.getInitializedGSON();
        EventAddUserDTO data = new EventAddUserDTO("jjjjjjjjjjjjjjjjjjjj");
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                        .put(route+"/add_user")
                        .requestAttr("username", "Toto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(gson.toJson(data)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        EventAddingResultDTO response = gson.fromJson(result.getResponse().getContentAsString(StandardCharsets.UTF_8),
                EventAddingResultDTO.class);
        Assertions.assertEquals(status, response);
    }

    // Method removeUserInEvent

}