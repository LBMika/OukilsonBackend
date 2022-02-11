package fr.oukilson.backend.service;

import fr.oukilson.backend.dto.game.GameDTO;
import fr.oukilson.backend.dto.game.GameSearchResultDTO;
import fr.oukilson.backend.dto.game.GameUuidDTO;
import fr.oukilson.backend.entity.Game;
import fr.oukilson.backend.repository.GameRepository;
import org.modelmapper.ModelMapper;
import java.util.*;

public class GameService {
    private final GameRepository repository;
    private final ModelMapper mapper;

    public GameService(GameRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Return all the game info by providing its uuid.
     * @param uuid String uuid of the game
     * @return Optional<GameUuidDTO>
     */
    public GameDTO findByUuid(String uuid) {
        GameDTO result = null;
        Optional<Game> game = this.repository.findByUuid(uuid);
        if (game.isPresent()) result = this.mapper.map(game.get(), GameDTO.class);
        return result;
    }

    /**
     * Return a list of all games sharing the same name or part of it.
     * @param name The string to search
     * @return List of GameUuidDTO
     */
    public List<GameSearchResultDTO> findByName(String name) {
        List<GameSearchResultDTO> result = new LinkedList<>();
        if (name!=null && name.length()>=3) {
            repository.findAllByNameContainingIgnoreCase(name).forEach(
                    g -> result.add(this.mapper.map(g, GameSearchResultDTO.class))
            );
        }
        return result;
    }

    /**
     * Find the 10 games used to create an event lately
     * @return List<GameUuidDTO>
     */
    public List<GameSearchResultDTO> findLastPlayedGame() {
        List<GameSearchResultDTO> result = new LinkedList<>();
        this.repository.findDistinctTop10ByOrderByEventsCreationDateAsc()
                        .forEach(g -> result.add(this.mapper.map(g, GameSearchResultDTO.class)));
        return result;
    }
}
