package fr.oukilson.backend.entity;

import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
@Table(name="user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    // DB id
    private String nickname;            // Unique username; also used to access from the client
    private String password;            // Encrypted version of the password
    private String email;               // Email of the user
    @Column(name = "first_name")
    private String firstName;           // First name of the user
    @Column(name = "last_name")
    private String lastName;            // Last name of the user

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "friend_list",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private List<User> friendList = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "denied_list",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "denied_id"))
    private List<User> deniedList = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "user_game_list",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id"))
    private List<Game> ownedGame = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "user_like_list",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id"))
    private List<Game> likedGame = new ArrayList<>();

    /**
     * Add a game into the owned game list
     * @param game Game
     * @return True if removed
     */
    public boolean addOwnedGame(Game game) {
        return this.addGame(this.ownedGame, game);
    }

    /**
     * Add a game into the liked game list
     * @param game Game
     * @return True if removed
     */
    public boolean addLikedGame(Game game) {
        return this.addGame(this.likedGame, game);
    }

    /**
     * Add a game into a game list
     * @param list A Game list
     * @param game Game
     * @return True if removed
     */
    private boolean addGame(List<Game> list, Game game) {
        boolean result = true;
        Iterator<Game> it = list.iterator();
        while (it.hasNext()) {
            Game g = it.next();
            if (game.getId().equals(g.getId())) {
                result = false;
                break;
            }
        }
        if (result) list.add(game);
        return result;
    }

    /**
     * Remove a game from the owned game list
     * @param game Game
     * @return True if removed
     */
    public boolean removeOwnedGame(Game game) {
        return this.removeGame(this.ownedGame, game);
    }

    /**
     * Remove a game from the liked game list
     * @param game Game
     * @return True if removed
     */
    public boolean removeLikedGame(Game game) {
        return this.removeGame(this.likedGame, game);
    }

    /**
     * Remove a game from a game list
     * @param list A Game list
     * @param game Game
     * @return True if removed
     */
    private boolean removeGame(List<Game> list, Game game) {
        boolean result = false;
        for(Iterator<Game> iter = list.iterator(); iter.hasNext();) {
            Game data = iter.next();
            if (data.getId().equals(game.getId())) {
                iter.remove();
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}