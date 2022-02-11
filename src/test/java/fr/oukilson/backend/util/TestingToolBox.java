package fr.oukilson.backend.util;

import com.google.common.hash.Hashing;
import com.google.gson.*;
import fr.oukilson.backend.entity.Event;
import fr.oukilson.backend.entity.Game;
import fr.oukilson.backend.entity.Location;
import fr.oukilson.backend.entity.User;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.UUID;

public class TestingToolBox {
    /**
     * Create a basic user
     * @param id Id
     * @param username Username
     * @param passwordHash sha256 hash
     * @return User
     */
    public static User generateUser(long id, String username, String passwordHash) {
        User result = new User();
        result.setId(id);
        result.setNickname(username);
        result.setPassword(passwordHash);
        return result;
    }

    /**
     * Generate a sha256 hash from the provided string
     * @param password String
     * @return String Hash
     */
    public static String generatePasswordHash(String password) {
        return Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
    }

    /**
     * Return a valid Game entity with all attributes set to valid data.
     * @param id Game's id in database
     * @param name Game's name
     * @return Game
     */
    public static Game createValidFullGame(Long id, String name) {
        Game game = new Game();
        game.setId(id);
        game.setName(name);
        game.setUuid(UUID.randomUUID().toString());
        game.setMinPlayer(2);
        game.setMaxPlayer(5);
        game.setMinAge(6);
        game.setCreatorName("Le créateur d'un jeu");
        game.setMinPlayingTime(30);
        game.setMaxPlayingTime(120);
        game.setDescription("Une description basique.");
        game.setSynopsis("Résumé");
        return game;
    }

    /**
     * Create a valid User entity with all attributes set to valid data.
     * @param id User's id in database
     * @param nickname User's unique nickname
     * @return User
     */
    public static User createValidFullUser(Long id, String nickname) {
        User user = new User();
        user.setId(id);
        user.setNickname(nickname);
        user.setPassword("d1e8a70b5ccab1dc2f56bbf7e99f064a660c08e361a35751b9c483c88943d082");
        user.setEmail("email@test.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        return user;
    }

    /**
     * Create a valid Event entity with all attributes set to valid data.
     * @param id Event's id in database
     * @param game Game for the event; must use method createValidFullGame
     * @param user User who created the event; must use method createValidFullUser
     * @return Event
     */
    public static Event createValidEvent(Long id, Game game, User user, Location location) {
        Event event = new Event();
        event.setId(id);
        event.setUuid(UUID.randomUUID().toString());
        event.setTitle("Valid event's title.");
        event.setMinPlayer(2);
        event.setMaxPlayer(5);
        event.setPrivate(false);
        event.setDescription("Une description plus que valide. YEAAAAAHHHHHHHHHHHHHH !!!!!!!!!!!!");
        event.setLocation(location);
        event.setGame(game);
        LocalDateTime localDateTime = LocalDateTime.now();
        event.setCreationDate(localDateTime);
        event.setLimitDate(localDateTime.plusDays(1L));
        event.setStartingDate(localDateTime.plusDays(2L));
        event.setEndingDate(event.getStartingDate().plusHours(5L));
        event.setCreator(user);
        return event;
    }

    /**
     * Return a Gson's instance which can deserialize and serialize LocalDateTime
     * @return Gson
     */
    public static Gson getInitializedGSON() {
        return new GsonBuilder().registerTypeAdapter(LocalDateTime.class,
                        (JsonDeserializer<LocalDateTime>) (json, type, context)
                                -> LocalDateTime.parse(json.getAsJsonPrimitive().getAsString()))
                .registerTypeAdapter(LocalDateTime.class,
                        (JsonSerializer<LocalDateTime>) (date, type, context)
                                -> new JsonPrimitive(date.toString()))
                .create();
    }
}
