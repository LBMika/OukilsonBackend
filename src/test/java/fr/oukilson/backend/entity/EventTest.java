package fr.oukilson.backend.entity;

import fr.oukilson.backend.util.TestingToolBox;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EventTest {

    /**
     * Test the method addUser when adding a user in the empty registered list
     */
    @DisplayName("Test addUser : add a valid user in an empty registered list")
    @Test
    public void testAddUserWhenRegisteredListIsEmpty() {
        Event event = new Event();
        event.setMaxPlayer(5);
        User user = TestingToolBox.createValidFullUser(1L, "toto");

        Assertions.assertNotNull(event.getRegisteredUsers());
        Assertions.assertEquals(0, event.getRegisteredUsers().size());
        Assertions.assertTrue(event.addUser(user));
        Assertions.assertEquals(1, event.getRegisteredUsers().size());
        Assertions.assertEquals(user, event.getRegisteredUsers().get(0));
    }

    /**
     * Test the method addUser when adding a user in the registered list
     */
    @DisplayName("Test addUser : add a valid user in registered list")
    @Test
    public void testAddUser() {
        Event event = new Event();
        event.setMaxPlayer(5);
        User user1 = TestingToolBox.createValidFullUser(1L, "toto");
        User user2 = TestingToolBox.createValidFullUser(2L, "tata");

        Assertions.assertNotNull(event.getRegisteredUsers());
        Assertions.assertEquals(0, event.getRegisteredUsers().size());
        Assertions.assertTrue(event.addUser(user1));
        Assertions.assertEquals(1, event.getRegisteredUsers().size());
        Assertions.assertEquals(user1, event.getRegisteredUsers().get(0));
        Assertions.assertTrue(event.addUser(user2));
        Assertions.assertEquals(2, event.getRegisteredUsers().size());
        Assertions.assertEquals(user2, event.getRegisteredUsers().get(1));
    }

    /**
     * Test the method addUser when adding a user if the registered list is full
     */
    @DisplayName("Test addUser : add user in a full registered list")
    @Test
    public void testAddUserWhenListIsFull() {
        Event event = new Event();
        event.setMaxPlayer(2);
        event.addUser(TestingToolBox.createValidFullUser(1L, "toto"));
        event.addUser(TestingToolBox.createValidFullUser(2L, "tata"));
        Assertions.assertEquals(event.getMaxPlayer(), event.getRegisteredUsers().size());
        Assertions.assertFalse(event.addUser(TestingToolBox.createValidFullUser(3L, "alpha")));
    }

    /**
     * Test the method addUser when adding a user who is already in the waiting list
     */
    @DisplayName("Test addUser : add user who's already in waiting list")
    @Test
    public void testAddUserWhenUserIsAlreadyInWaitingList() {
        Event event = new Event();
        event.setMaxPlayer(5);
        User user = TestingToolBox.createValidFullUser(1L, "toto");

        Assertions.assertTrue(event.addUserInWaitingQueue(user));
        Assertions.assertTrue(event.getWaitingUsers().stream().anyMatch(u -> u.getNickname().equals(user.getNickname())));
    }

    /**
     * Test the method addUser when adding a user who's already in the registered list
     */
    @DisplayName("Test addUser : add user who's already in registered list")
    @Test
    public void testAddUserWhenUserIsAlreadyInRegisteredList() {
        Event event = new Event();
        event.setMaxPlayer(5);
        User user = TestingToolBox.createValidFullUser(1L, "toto");

        Assertions.assertTrue(event.addUser(user));
        Assertions.assertFalse(event.addUser(user));
    }

    /**
     * Test the method addUserInWaitingQueue when adding a user in the empty waiting list
     */
    @DisplayName("Test addUserInWaitingQueue : add a valid user in an empty waiting list")
    @Test
    public void testAddUserInWaitingListWhenEmpty() {
        Event event = new Event();
        event.setMaxPlayer(5);
        User user = TestingToolBox.createValidFullUser(1L, "toto");

        Assertions.assertNotNull(event.getWaitingUsers());
        Assertions.assertEquals(0, event.getWaitingUsers().size());
        Assertions.assertTrue(event.addUserInWaitingQueue(user));
        Assertions.assertEquals(1, event.getWaitingUsers().size());
        Assertions.assertEquals(user, event.getWaitingUsers().get(0));
    }

    /**
     * Test the method addUserInWaitingQueue when adding a user in the waiting list
     */
    @DisplayName("Test addUserInWaitingQueue : add a valid user in the waiting list")
    @Test
    public void testAddUserInWaitingQueue() {
        Event event = new Event();
        event.setMaxPlayer(5);
        User user1 = TestingToolBox.createValidFullUser(1L, "toto");
        User user2 = TestingToolBox.createValidFullUser(2L, "tata");

        Assertions.assertNotNull(event.getWaitingUsers());
        Assertions.assertEquals(0, event.getWaitingUsers().size());
        Assertions.assertTrue(event.addUserInWaitingQueue(user1));
        Assertions.assertEquals(1, event.getWaitingUsers().size());
        Assertions.assertEquals(user1, event.getWaitingUsers().get(0));
        Assertions.assertTrue(event.addUserInWaitingQueue(user2));
        Assertions.assertEquals(2, event.getWaitingUsers().size());
        Assertions.assertEquals(user2, event.getWaitingUsers().get(1));
    }

    /**
     * Test the method addUserInWaitingQueue when adding a user in a full waiting list
     */
    @DisplayName("Test addUserInWaitingQueue : add a valid user in a full waiting list")
    @Test
    public void testAddUserInWaitingQueueWhenListIsFull() {
        Event event = new Event();
        event.setMaxPlayer(2);
        event.addUserInWaitingQueue(TestingToolBox.createValidFullUser(1L, "toto"));
        event.addUserInWaitingQueue(TestingToolBox.createValidFullUser(2L, "tata"));
        Assertions.assertEquals(event.getMaxPlayer(), event.getWaitingUsers().size());
        Assertions.assertFalse(event.addUserInWaitingQueue(TestingToolBox.createValidFullUser(3L, "alpha")));
    }

    /**
     * Test the method addUserInWaitingQueue when adding a user who's already in the waiting list
     */
    @DisplayName("Test addUserInWaitingQueue : add a valid user who's already in the waiting list")
    @Test
    public void testAddUserInWaitingQueueWhenUserIsAlreadyInWaitingList() {
        Event event = new Event();
        event.setMaxPlayer(5);
        User user = TestingToolBox.createValidFullUser(1L, "toto");

        Assertions.assertTrue(event.addUserInWaitingQueue(user));
        Assertions.assertFalse(event.addUserInWaitingQueue(user));
    }

    /**
     * Test the method addUserInWaitingQueue when adding a user who's already in the registered list
     */
    @DisplayName("Test addUserInWaitingQueue : add a valid user who's already in the registered list")
    @Test
    public void testAddUserInWaitingQueueWhenUserIsAlreadyInRegisteredList() {
        Event event = new Event();
        event.setMaxPlayer(5);
        User user = TestingToolBox.createValidFullUser(1L, "toto");

        Assertions.assertTrue(event.addUser(user));
        Assertions.assertTrue(event.getRegisteredUsers().stream().anyMatch(u -> u.getNickname().equals(user.getNickname())));
    }

    /**
     * Test the method removeUser
     */
    @DisplayName("Test removeUser : remove a user in the registered list")
    @Test
    public void testRemoveUser() {
        Event event = new Event();
        event.setMaxPlayer(5);
        User user = TestingToolBox.createValidFullUser(1L, "toto");

        event.addUser(user);
        Assertions.assertTrue(event.removeUser(user));
        Assertions.assertEquals(0, event.getRegisteredUsers().size());
    }

    /**
     * Test the method removeUser when removing a user in an empty registered list
     */
    @DisplayName("Test removeUser : remove a user when the registered list is empty")
    @Test
    public void testRemoveUserWithEmptyList() {
        Event event = new Event();
        event.setMaxPlayer(5);
        User user = TestingToolBox.createValidFullUser(1L, "toto");
        Assertions.assertFalse(event.removeUser(user));
    }

    /**
     * Test the method removeUser when removing a user who's not in the registered list
     */
    @DisplayName("Test removeUser : remove a user who's not in the registered list")
    @Test
    public void testRemoveUserWhoIsNotInList() {
        Event event = new Event();
        event.setMaxPlayer(5);
        event.addUser(TestingToolBox.createValidFullUser(1L, "toto"));
        Assertions.assertNotEquals(0, event.getRegisteredUsers().size());
        Assertions.assertFalse(event.removeUser(TestingToolBox.createValidFullUser(2L, "tata")));
    }

    /**
     * Test the method removeUserInWaitingQueue
     */
    @DisplayName("Test removeUserInWaitingQueue : remove a user in the waiting list")
    @Test
    public void testRemoveUserInWaitingQueue() {
        Event event = new Event();
        event.setMaxPlayer(5);
        User user = TestingToolBox.createValidFullUser(1L, "toto");

        event.addUserInWaitingQueue(user);
        Assertions.assertTrue(event.removeUser(user));
        Assertions.assertEquals(0, event.getWaitingUsers().size());
    }

    /**
     * Test the method removeUserInWaitingQueue on an empty waiting list
     */
    @DisplayName("Test removeUserInWaitingQueue : remove a user on an empty waiting list")
    @Test
    public void testRemoveUserInWaitingQueueWithEmptyList() {
        Event event = new Event();
        event.setMaxPlayer(5);
        User user = TestingToolBox.createValidFullUser(1L, "toto");
        Assertions.assertEquals(0, event.getWaitingUsers().size());
        Assertions.assertFalse(event.removeUser(user));
    }

    /**
     * Test the method removeUserInWaitingQueue when removing a user who's not in the waiting list
     */
    @DisplayName("Test removeUserInWaitingQueue : remove a user not in the waiting list")
    @Test
    public void testRemoveUserInWaitingQueueWhoIsNotInList() {
        Event event = new Event();
        event.setMaxPlayer(5);
        event.addUserInWaitingQueue(TestingToolBox.createValidFullUser(1L, "toto"));
        Assertions.assertFalse(event.removeUser(TestingToolBox.createValidFullUser(2L, "tata")));
    }
}
