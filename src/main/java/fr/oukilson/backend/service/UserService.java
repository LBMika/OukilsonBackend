package fr.oukilson.backend.service;

import fr.oukilson.backend.dto.user.UserCreationDTO;
import fr.oukilson.backend.dto.user.UserDTO;
import fr.oukilson.backend.dto.user.UserEventDTO;
import fr.oukilson.backend.model.RegexCollection;
import fr.oukilson.backend.entity.User;
import fr.oukilson.backend.repository.EventRepository;
import fr.oukilson.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.*;
import java.util.Optional;

@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private EventRepository eventRepository;
    private ModelMapper modelMapper;
    private RegexCollection regexCollection;

    public UserService(UserRepository userRepository, EventRepository eventRepository, ModelMapper modelMapper, RegexCollection regexCollection) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.modelMapper = modelMapper;
        this.regexCollection = regexCollection;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = this.userRepository.findByNicknameOrEmail(username, username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        User user = optionalUser.get();
        Collection<SimpleGrantedAuthority> auths = new ArrayList<>();
        return new org.springframework.security.core.userdetails.User(user.getNickname(), user.getPassword(), auths);
    }

    /**
     * Search a user by nickname
     * @param nickname User's nickname
     * @return UserDTO
     */
    public UserDTO findUserByNickname(String nickname) {
        UserDTO result;
        if (this.regexCollection.getNicknamePattern().matcher(nickname).find()) {
            Optional<User> optionalUser = userRepository.findByNickname(nickname);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                result = this.modelMapper.map(user, UserDTO.class);
                result.setEventList(this.findUserEvents(user.getId()));
            }
            else
                result = null;

        }
        else
            result = null;
        return result;
    }

    /**
     * Find all distinct events in which a user is participating in
     * @param userId User's Id
     * @return List<UserEventDTO>
     */
    private List<UserEventDTO> findUserEvents(long userId) {
        List<UserEventDTO> result = new LinkedList<>();
        this.eventRepository.findAllDistinctByCreatorIdOrRegisteredUsersIdOrWaitingUsersId(userId, userId, userId)
                            .forEach(e -> {
                                UserEventDTO event = new UserEventDTO();
                                event.setUuid(e.getUuid());
                                event.setTitle(e.getTitle());
                                event.setGame(e.getGame().getName());
                                event.setDate(e.getStartingDate());
                                result.add(event);
                            });
        return result;
    }

    /** Method to save a user entity to the database
     * @param userCreationDTO User's data
     * @return UserDTO
     */
    public UserDTO createUser(UserCreationDTO userCreationDTO) {
        UserDTO result = null;
        if (userCreationDTO!=null
                && userCreationDTO.isValid(regexCollection.getNicknamePattern(), regexCollection.getEmailPattern())) {
            User user = this.userRepository.save(this.modelMapper.map(userCreationDTO, User.class));
            result = this.modelMapper.map(user, UserDTO.class);
        }
        return result;
    }

    /**
     * Add a user to the main user's friend list
     * @param mainUser User (nickname) to alter the friend list
     * @param secondUser User (nickname) to add
     * @return True if the adding has been done
     */
    public boolean addUserToFriendList(String mainUser, String secondUser) {
        boolean result;
        Optional<User> myOptionalUser = this.userRepository.findByNickname(mainUser);
        if (myOptionalUser.isPresent()) {
            Optional<User> myOptionalFriend = this.userRepository.findByNickname(secondUser);
            if (myOptionalFriend.isPresent()) {
                User myUser = myOptionalUser.get();
                List<User> friends = myUser.getFriendList();
                User myFriend = myOptionalFriend.get();
                if (!friends.contains(myFriend)) {
                    friends.add(myFriend);
                    this.userRepository.save(myUser);
                    result = true;
                }
                else
                    result = false;
            }
            else
                result = false;
        }
        else
            result = false;
        return result;
    }

    /**
     * Remove a user from a friend list
     * @param mainUser The user (nickname) to alter his friend list
     * @param secondUser The user's nickname to remove
     * @return True if the removing has been done
     */
    public boolean removeUserFromFriendList(String mainUser, String secondUser) {
        boolean result;
        Optional<User> myOptionalUser = this.userRepository.findByNickname(mainUser);
        if (myOptionalUser.isPresent()) {
            Optional<User> myOptionalFriend = this.userRepository.findByNickname(secondUser);
            if (myOptionalFriend.isPresent()) {
                User myUser = myOptionalUser.get();
                User myFriend = myOptionalFriend.get();
                myUser.getFriendList().remove(myFriend);
                this.userRepository.save(myUser);
                result = true;
            }
            else
                result = false;
        }
        else
            result = false;
        return result;
    }

    /**
     * Empty a friend list
     * @param nickname The user (nickname) to empty his friend list
     * @return True if successfully emptied
     */
    public boolean emptyFriendList(String nickname) {
        boolean result;
        Optional<User> myOptionalUser = this.userRepository.findByNickname(nickname);
        if (myOptionalUser.isPresent()) {
            User myUser = myOptionalUser.get();
            myUser.getFriendList().clear();
            this.userRepository.save(myUser);
            result = true;
        }
        else
            result = false;
        return result;
    }
}