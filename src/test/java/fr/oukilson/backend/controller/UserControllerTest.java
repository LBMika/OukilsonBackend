package fr.oukilson.backend.controller;

import com.google.gson.Gson;
import fr.oukilson.backend.dto.user.UserCreationDTO;
import fr.oukilson.backend.dto.user.UserDTO;
import fr.oukilson.backend.entity.User;
import fr.oukilson.backend.security.SecurityEnabledSetup;
import fr.oukilson.backend.util.TestingToolBox;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest extends SecurityEnabledSetup {
    @Autowired
    private MockMvc mockMvc;
    private final String route = "/users";

    // Method findByNickname

    /**
     * Test findByNickname when nickname is present in DB
     */
    @DisplayName("Test findByNickname : nickname found")
    @Test
    void testFindByNicknameFound() throws Exception {
        String nickname = "Tutululu";
        UserDTO dto = new UserDTO(nickname, new LinkedList<>(), new LinkedList<>(), new LinkedList<>(), new LinkedList<>());
        Mockito.when(this.userService.findUserByNickname(nickname)).thenReturn(dto);
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get(route+"/"+nickname))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Gson gson = new Gson();
        UserDTO resultDTO = gson.fromJson(
                result.getResponse().getContentAsString(StandardCharsets.UTF_8),
                UserDTO.class);
        Assertions.assertNotNull(resultDTO);
        Assertions.assertEquals(dto, resultDTO);
    }

    /**
     * Test findByNickname when nickname is not present in DB
     */
    @DisplayName("Test findByNickname : nickname not found")
    @Test
    void testFindByNicknameNotFound() throws Exception {
        String nickname = "Tutululu";
        Mockito.when(this.userService.findUserByNickname(nickname)).thenReturn(null);
        this.mockMvc.perform(MockMvcRequestBuilders.get(route+"/"+nickname))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Test findByNickname when nickname is not valid
     */
    @DisplayName("Test findByNickname : invalid nickname")
    @Test
    void testFindByNicknameInvalid() throws Exception {
        String nickname = "rené";
        Mockito.when(this.userService.findUserByNickname(nickname)).thenReturn(null);
        this.mockMvc.perform(MockMvcRequestBuilders.get(route+"/"+nickname))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Test findByNickname when nickname is null
     */
    @DisplayName("Test findByNickname : null nickname")
    @Test
    void testFindByNicknameNull() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(route+"/"))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
        this.mockMvc.perform(MockMvcRequestBuilders.get(route))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    /**
     * Test findByNickname when there is a SQL problem
     */
    @DisplayName("Test findByNickname : SQL problem")
    @Test
    void testFindByNicknameSQLProblem() throws Exception {
        String nickname = "Tutululu";
        Mockito.when(this.userService.findUserByNickname(nickname)).thenThrow(RuntimeException.class);
        this.mockMvc.perform(MockMvcRequestBuilders.get(route+"/"+nickname))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
  
    // Method createUser

    /**
     * Test createUser with a null body
     */
    @DisplayName("Test createUser : null body")
    @Test
    void testCreateUserNullBody() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(route))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * Test createUser with a null password
     */
    @DisplayName("Test createUser : null password")
    @Test
    void testCreateUserNullPassword() throws Exception {
        UserCreationDTO body = new UserCreationDTO("Toupie", null, "hibiscus@george.fr");
        Gson gson = new Gson();
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post(route)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(gson.toJson(body)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * Test createUser with a null nickname
     */
    @DisplayName("Test createUser : null nickname")
    @Test
    void testCreateUserNullNickname() throws Exception {
        UserCreationDTO body = new UserCreationDTO(null, "sdfghjklmmdj", "hibiscus@george.fr");
        Gson gson = new Gson();
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post(route)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(gson.toJson(body)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * Test createUser with a null email
     */
    @DisplayName("Test createUser : null email")
    @Test
    void testCreateUserNullEmail() throws Exception {
        UserCreationDTO body = new UserCreationDTO("Toupie", "sdfghjklmmdj", null);
        Gson gson = new Gson();
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post(route)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(gson.toJson(body)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * Test createUser when the user creation on the service failed
     */
    @DisplayName("Test createUser : user creation failed")
    @Test
    void testCreateUserUserCreationFailed() throws Exception {
        UserCreationDTO body = new UserCreationDTO("Toupie", "sdfghjklmmdj", "hibiscus@george.fr");
        Mockito.when(this.userService.createUser(body)).thenReturn(null);
        Gson gson = new Gson();
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post(route)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(gson.toJson(body)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * Test createUser when the user creation on the service is successful
     */
    @DisplayName("Test createUser : user creation successful")
    @Test
    void testCreateUserUserCreationSuccess() throws Exception {
        String nickname = "Toupie";
        UserCreationDTO body = new UserCreationDTO(nickname, "sdfghjklmmdj", "hibiscus@george.fr");
        UserDTO userDTO = new UserDTO(nickname, new LinkedList<>(), new LinkedList<>(), new LinkedList<>(), new LinkedList<>());
        Mockito.when(this.userService.createUser(body)).thenReturn(userDTO);
        Gson gson = new Gson();
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                        .post(route)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(gson.toJson(body)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        UserDTO resultDTO = gson.fromJson(
                result.getResponse().getContentAsString(StandardCharsets.UTF_8),
                UserDTO.class);
        Assertions.assertNotNull(resultDTO);
        Assertions.assertEquals(userDTO, resultDTO);
    }

    /**
     * Test createUser when the user creation on the service throw an exception
     */
    @DisplayName("Test createUser : user creation throws an exception")
    @Test
    void testCreateUserUserCreationThrowException() throws Exception {
        UserCreationDTO body = new UserCreationDTO("Toupie", "sdfghjklmmdj", "hibiscus@george.fr");
        Mockito.when(this.userService.createUser(body)).thenThrow(NullPointerException.class);
        Gson gson = new Gson();
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post(route)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(gson.toJson(body)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    // Method addUserToFriendList

    /**
     * Test addUserToFriendList : no header
     */
    @DisplayName("Test addUserToFriendList : no header")
    @Test
    void testAddUserToFriendListNoHeader() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put(route+"/friend/add/toto"))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    /**
     * Test addUserToFriendList : no friend to add
     */
    @DisplayName("Test addUserToFriendList : no friend to add")
    @Test
    @WithMockUser(username = "Toto", password = "b41419df9bdaa5cd16d4766696bc486c8eca5fbcaa99a0e06bb034504f93f71a", roles = "")
    void testAddUserToFriendListNoFriendToAdd() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put(route+"/friend/add/"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Test addUserToFriendList : can't add friend
     */
    @DisplayName("Test addUserToFriendList : can't add friend")
    @Test
    @WithMockUser(username = "Toto", password = "b41419df9bdaa5cd16d4766696bc486c8eca5fbcaa99a0e06bb034504f93f71a", roles = "")
    void testAddUserToFriendListCannotAddFriend() throws Exception {
        User user = TestingToolBox.generateUser(1L, "Borax", TestingToolBox.generatePasswordHash("fdQDHF554s"));
        user.setPassword("b41419df9bdaa5cd16d4766696bc486c8eca5fbcaa99a0e06bb034504f93f71a");
        User friend = TestingToolBox.generateUser(2L, "Marco", "qsdftgyhujik");
        Mockito.when(this.userService.addUserToFriendList(user.getNickname(), friend.getNickname())).thenReturn(false);
        this.mockMvc.perform(MockMvcRequestBuilders.put(route+"/friend/add/"+friend.getNickname())
                                                    .requestAttr("username", user.getNickname()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isBoolean())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("false"));
    }

    /**
     * Test addUserToFriendList : friend is added
     */
    @DisplayName("Test addUserToFriendList : friend is added")
    @Test
    @WithMockUser(username = "Toto", password = "b41419df9bdaa5cd16d4766696bc486c8eca5fbcaa99a0e06bb034504f93f71a", roles = "")
    void testAddUserToFriendListFriendAdded() throws Exception {
        User user = TestingToolBox.generateUser(1L, "Toto", TestingToolBox.generatePasswordHash("fdQDHF554s"));
        user.setPassword("b41419df9bdaa5cd16d4766696bc486c8eca5fbcaa99a0e06bb034504f93f71a");
        User friend = TestingToolBox.generateUser(2L, "Marco", "qsdftgyhujik");
        Mockito.when(this.userService.addUserToFriendList(user.getNickname(), friend.getNickname())).thenReturn(true);
        this.mockMvc.perform(MockMvcRequestBuilders.put(route+"/friend/add/"+friend.getNickname())
                                                    .requestAttr("username", user.getNickname()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isBoolean())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("true"));
    }

    // Method removeUserFromFriendList

    /**
     * Test removeUserFromFriendList : no header
     */
    @DisplayName("Test removeUserFromFriendList : no header")
    @Test
    void testRemoveUserFromFriendListNoHeader() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put(route+"/friend/remove/toto"))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    /**
     * Test removeUserFromFriendList : no friend to remove
     */
    @DisplayName("Test removeUserFromFriendList : no friend to remove")
    @Test
    @WithMockUser(username = "Toto", password = "b41419df9bdaa5cd16d4766696bc486c8eca5fbcaa99a0e06bb034504f93f71a", roles = "")
    void testRemoveUserFromFriendListNoFriendToRemove() throws Exception {
        User user = TestingToolBox.generateUser(1L, "Rubix", TestingToolBox.generatePasswordHash("KloPfD;?!"));
        this.mockMvc.perform(MockMvcRequestBuilders.put(route+"/friend/remove/"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Test addUserToFriendList : can't remove friend
     */
    @DisplayName("Test removeUserFromFriendList : can't remove friend")
    @Test
    @WithMockUser(username = "Toto", password = "b41419df9bdaa5cd16d4766696bc486c8eca5fbcaa99a0e06bb034504f93f71a", roles = "")
    void testRemoveUserFromFriendListCannotRemoveFriend() throws Exception {
        User user = TestingToolBox.generateUser(1L, "Toto", TestingToolBox.generatePasswordHash("fdQDHF554s"));
        user.setPassword("b41419df9bdaa5cd16d4766696bc486c8eca5fbcaa99a0e06bb034504f93f71a");
        User friend = TestingToolBox.generateUser(2L, "Marco", "qsdftgyhujik");
        Mockito.when(this.userService.removeUserFromFriendList(user.getNickname(), friend.getNickname())).thenReturn(false);
        this.mockMvc.perform(MockMvcRequestBuilders.put(route+"/friend/remove/"+friend.getNickname())
                                                    .requestAttr("username", user.getNickname()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isBoolean())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("false"));
    }

    /**
     * Test addUserToFriendList : friend is removed
     */
    @DisplayName("Test removeUserFromFriendList : friend is removed")
    @Test
    @WithMockUser(username = "Toto", password = "b41419df9bdaa5cd16d4766696bc486c8eca5fbcaa99a0e06bb034504f93f71a", roles = "")
    void testRemoveUserFromFriendListFriendRemoved() throws Exception {
        User user = TestingToolBox.generateUser(1L, "Toto", TestingToolBox.generatePasswordHash("fdQDHF554s"));
        user.setPassword("b41419df9bdaa5cd16d4766696bc486c8eca5fbcaa99a0e06bb034504f93f71a");
        User friend = TestingToolBox.generateUser(2L, "Marco", "qsdftgyhujik");
        Mockito.when(this.userService.removeUserFromFriendList(user.getNickname(), friend.getNickname())).thenReturn(true);
        this.mockMvc.perform(MockMvcRequestBuilders.put(route+"/friend/remove/"+friend.getNickname())
                                                    .requestAttr("username", user.getNickname()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isBoolean())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("true"));
    }

    // Method emptyFriendList

    /**
     * Test emptyFriendList : user not in database
     */
    @DisplayName("Test emptyFriendList : user not in database")
    @Test
    @WithMockUser(username = "Toto", password = "b41419df9bdaa5cd16d4766696bc486c8eca5fbcaa99a0e06bb034504f93f71a", roles = "")
    void testEmptyFriendListUnknownUser() throws Exception {
        User user = TestingToolBox.generateUser(1L, "Toto", TestingToolBox.generatePasswordHash("BestOfTheBest"));
        user.setPassword("b41419df9bdaa5cd16d4766696bc486c8eca5fbcaa99a0e06bb034504f93f71a");
        Mockito.when(this.userService.emptyFriendList(user.getNickname())).thenReturn(false);
        this.mockMvc.perform(MockMvcRequestBuilders.put(route+"/friend/empty/")
                                                    .requestAttr("username", user.getNickname()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isBoolean())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("false"));
    }

    /**
     * Test emptyFriendList : everything is ok
     */
    @DisplayName("Test emptyFriendList : everything is ok")
    @Test
    @WithMockUser(username = "Toto", password = "b41419df9bdaa5cd16d4766696bc486c8eca5fbcaa99a0e06bb034504f93f71a", roles = "")
    void testEmptyFriendListUserFound() throws Exception {
        User user = TestingToolBox.generateUser(1L, "Toto", TestingToolBox.generatePasswordHash("BestOfTheBest"));
        user.setPassword("b41419df9bdaa5cd16d4766696bc486c8eca5fbcaa99a0e06bb034504f93f71a");
        Mockito.when(this.userService.emptyFriendList(user.getNickname())).thenReturn(true);
        this.mockMvc.perform(MockMvcRequestBuilders.put(route+"/friend/empty/")
                                                    .requestAttr("username", user.getNickname()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isBoolean())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("true"));
    }
}