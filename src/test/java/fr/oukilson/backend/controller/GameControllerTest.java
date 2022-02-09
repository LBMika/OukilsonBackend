package fr.oukilson.backend.controller;

import com.google.gson.Gson;
import fr.oukilson.backend.dto.GameDTO;
import fr.oukilson.backend.dto.GameUuidDTO;
import fr.oukilson.backend.entity.Game;
import fr.oukilson.backend.security.SecurityEnabledSetup;
import fr.oukilson.backend.service.GameService;
import fr.oukilson.backend.util.TestingToolBox;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

@WebMvcTest(controllers = GameController.class)
public class GameControllerTest extends SecurityEnabledSetup {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GameService service;
    private final String route = "/games";

    // Route findByUuid GET

    /**
     * Test findByUuid when the given uuid corresponds to an actual game
     */
    @DisplayName("Test findByUuid : uuid corresponding to an actual game")
    @Test
    public void testFindByUuidWhenGameFound() throws Exception {
        // Mocking
        Game game = TestingToolBox.createValidFullGame(1L, "Lords of Waterdeep");
        ModelMapper mapper = new ModelMapper();
        GameDTO dto = mapper.map(game, GameDTO.class);
        Mockito.when(this.service.findByUuid(game.getUuid())).thenReturn(dto);

        // Send request
        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.get(route+"/"+game.getUuid()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Assert
        Gson gson = new Gson();
        GameDTO resultDTO = gson.fromJson(
                result.getResponse().getContentAsString(StandardCharsets.UTF_8),
                GameDTO.class);
        Assertions.assertNotNull(resultDTO);
        Assertions.assertEquals(dto, resultDTO);
    }

    /**
     * Test findByUuid when the given uuid do not correspond to an actual game
     */
    @DisplayName("Test findByUuid : uuid not corresponding to an actual game")
    @Test
    public void testFindByUuidWhenGameNotFound() throws Exception {
        // Mocking
        Game game = TestingToolBox.createValidFullGame(1L, "Lords of Waterdeep");
        Mockito.when(this.service.findByUuid(game.getUuid())).thenReturn(null);

        // Send request
        this.mockMvc.perform(MockMvcRequestBuilders.get(route+"/"+game.getUuid()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    // Route findByName POST

    /**
     * Test findByName with the search gives results
     */
    @DisplayName("Test findByName : result found")
    @Test
    public void testFindByNameReturnResults() throws Exception {
        // Mocking
        String name = "Jeux";
        List<GameUuidDTO> games = new LinkedList<>();
        ModelMapper mapper = new ModelMapper();
        int size = 3;
        for (int i=0; i<size; i++) {
            games.add(mapper.map(TestingToolBox.createValidFullGame((long) i, "Jeux n°"+i), GameUuidDTO.class));
        }
        BDDMockito.when(this.service.findByName(name)).thenReturn(games);

        // Send request
        Gson gson = new Gson();
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get(route+"/search?name="+name))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andReturn();

        // Assert
        GameUuidDTO[] resultDTO = gson.fromJson(
                result.getResponse().getContentAsString(StandardCharsets.UTF_8),
                GameUuidDTO[].class);
        Assertions.assertNotNull(resultDTO);
        Assertions.assertEquals(size, resultDTO.length);
        for (int i=0; i<size; i++) {
            Assertions.assertEquals(games.get(i), resultDTO[i]);
        }
    }

    /**
     * Test findByName with special characters in the string to search
     */
    @DisplayName("Test findByName : special character in url")
    @Test
    public void testFindByNameSpecialCharInURL() throws Exception {
        // Mock
        String name = "Les échos de Fäfnir !";
        List<GameUuidDTO> games = new LinkedList<>();
        ModelMapper mapper = new ModelMapper();
        games.add(mapper.map(TestingToolBox.createValidFullGame(1L, name), GameUuidDTO.class));
        BDDMockito.when(this.service.findByName(name)).thenReturn(games);

        // Request
        Gson gson = new Gson();
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get(route+"/search?name="+name))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andReturn();

        // Assert
        GameUuidDTO[] resultDTO = gson.fromJson(
                result.getResponse().getContentAsString(StandardCharsets.UTF_8),
                GameUuidDTO[].class);
        Assertions.assertNotNull(resultDTO);
        Assertions.assertEquals(1, resultDTO.length);
        Assertions.assertEquals(games.get(0), resultDTO[0]);
    }

    /**
     * Test findByName with the search gives no result
     */
    @DisplayName("Test findByName : no result found")
    @Test
    public void testFindByNameNoResultFound() throws Exception {
        String name = "o";
        BDDMockito.when(this.service.findByName(name)).thenReturn(new LinkedList<>());
        this.mockMvc.perform(MockMvcRequestBuilders.get(route+"/search?name="+name))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }

    /**
     * Test findByName with an empty search string
     */
    @DisplayName("Test findByName : empty search string")
    @Test
    public void testFindByNameWithEmptySearchString() throws Exception {
        String name = "";
        this.mockMvc.perform(MockMvcRequestBuilders.get(route+"/search?name="+name))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }
}