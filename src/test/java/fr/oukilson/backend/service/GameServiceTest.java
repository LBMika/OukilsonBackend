package fr.oukilson.backend.service;

import fr.oukilson.backend.dto.game.GameDTO;
import fr.oukilson.backend.dto.game.GameSearchResultDTO;
import fr.oukilson.backend.entity.Game;
import fr.oukilson.backend.repository.GameRepository;
import fr.oukilson.backend.util.TestingToolBox;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GameServiceTest {
    @MockBean
    private GameRepository repository;
    @Autowired
    private ModelMapper mapper;
    private GameService service;

    @BeforeAll
    void init() {
        this.service = new GameService(repository, mapper);
    }

    // Method findByUuid

    /**
     * Test findByUuid with null parameter
     */
    @Test
    @DisplayName("Test findByUuid : null parameter")
    void testFindByUuidNullParameter() {
        Assertions.assertNull(this.service.findByUuid(null));
    }

    /**
     * Test findByUuid with game presents in database
     */
    @DisplayName("Test findByUuid : game is in database")
    @Test
    void testFindByUuidGameInDatabase() {
        Game game = TestingToolBox.createValidFullGame(1L, "Root");
        BDDMockito.when(this.repository.findByUuid(game.getUuid())).thenReturn(Optional.of(game));
        GameDTO dto = this.service.findByUuid(game.getUuid());
        Assertions.assertNotNull(dto);
        Assertions.assertEquals(this.mapper.map(game, GameDTO.class), dto);
    }

    /**
     * Test findByUuid with game not in database
     */
    @DisplayName("Test findByUuid : game is not in database")
    @Test
    void testFindByUuidGameNotInDatabase() {
        Game game = TestingToolBox.createValidFullGame(1L, "Root");
        GameDTO dto = this.service.findByUuid(game.getUuid());
        Assertions.assertNull(dto);
    }

    // Method findByName

    /**
     * Test findByName with null parameter
     */
    @Test
    @DisplayName("Test findByName : null parameter")
    void testFindByNameNullParameter() {
        List<GameSearchResultDTO> list = this.service.findByName(null);
        Assertions.assertNotNull(list);
        Assertions.assertEquals(0, list.size());
    }

    /**
     * Test findByName with the look-up string is null
     */
    @Test
    @DisplayName("Test findByName : null given name")
    void testFindByNameNullName() {
        List<GameSearchResultDTO> list = this.service.findByName(null);
        Assertions.assertNotNull(list);
        Assertions.assertEquals(0, list.size());
    }

    /**
     * Test findByName with no result found
     */
    @Test
    @DisplayName("Test findByName : no result found")
    void testFindByNameWithNoResultFound() {
        String name = "7 Wonders";
        BDDMockito.when(this.repository.findAllByNameContainingIgnoreCase(name)).thenReturn(new LinkedList<>());
        List<GameSearchResultDTO> list = this.service.findByName(name);
        Assertions.assertNotNull(list);
        Assertions.assertEquals(0, list.size());
    }

    /**
     * Test findByName with some result found
     */
    @Test
    @DisplayName("Test findByName : result found")
    void testFindByNameWithResultFound() {
        String name = "7 Wonders";
        List<Game> games = new LinkedList<>();
        int size = 4;
        for (int i=0; i<size; i++) {
            games.add(TestingToolBox.createValidFullGame((long) i, "Jeux n°"+i));
        }
        BDDMockito.when(this.repository.findAllByNameContainingIgnoreCase(name)).thenReturn(games);
        List<GameSearchResultDTO> list = this.service.findByName(name);
        Assertions.assertNotNull(list);
        Assertions.assertEquals(size, list.size());
        for (int i=0; i<size; i++) {
            Assertions.assertEquals(this.mapper.map(games.get(i), GameSearchResultDTO.class), list.get(i));
        }
    }

    /**
     * Test findByName with a name's length below 3 characters
     */
    @Test
    @DisplayName("Test findByName : name's length below 3 characters")
    void testFindByNameTooShortName() {
        String name = "a";
        List<Game> games = new LinkedList<>();
        int size = 4;
        for (int i=0; i<size; i++) {
            games.add(TestingToolBox.createValidFullGame((long) i, "Jeux n°"+i));
        }
        BDDMockito.when(this.repository.findAllByNameContainingIgnoreCase(name)).thenReturn(games);
        List<GameSearchResultDTO> list = this.service.findByName(name);
        Assertions.assertNotNull(list);
        Assertions.assertEquals(0, list.size());
    }
}
