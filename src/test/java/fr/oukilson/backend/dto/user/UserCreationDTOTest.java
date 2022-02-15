package fr.oukilson.backend.dto.user;

import fr.oukilson.backend.model.RegexCollection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserCreationDTOTest {
    @Autowired
    private RegexCollection regexCollection;

    /**
     * Test isValid when nicknamePattern is null
     */
    @DisplayName("Test isValid : null nickname regex")
    @Test
    void testIsValidNicknamePatternNull() {
        UserCreationDTO dto = new UserCreationDTO("Regis", "dfghjklhjjh", "regis@thales.fr");
        Assertions.assertThrows(NullPointerException.class,
                () -> dto.isValid(null, regexCollection.getEmailPattern()));
    }

    /**
     * Test isValid when emailPattern is null
     */
    @DisplayName("Test isValid : null email regex")
    @Test
    void testIsValidEmailPatternNull() {
        UserCreationDTO dto = new UserCreationDTO("Regis", "dfghjklhjjh", "regis@thales.fr");
        Assertions.assertThrows(NullPointerException.class,
                () -> dto.isValid(regexCollection.getNicknamePattern(), null));
    }

    /**
     * Test isValid when nickname is null
     */
    @DisplayName("Test isValid : null nickname")
    @Test
    void testIsValidNicknameNull() {
        UserCreationDTO dto = new UserCreationDTO(null, "hsdfsqdmlhvjdfvbhld", "unemail@unserveur.fr");
        Assertions.assertFalse(dto.isValid(regexCollection.getNicknamePattern(), regexCollection.getEmailPattern()));
    }

    /**
     * Test isValid when password is null
     */
    @DisplayName("Test isValid : password is null")
    @Test
    void testIsValidPasswordNull() {
        UserCreationDTO dto = new UserCreationDTO("Jean", null, "unemail@unserveur.fr");
        Assertions.assertFalse(dto.isValid(regexCollection.getNicknamePattern(), regexCollection.getEmailPattern()));
    }

    /**
     * Test isValid when email is null
     */
    @DisplayName("Test isValid : null email")
    @Test
    void testIsValidEmailNull() {
        UserCreationDTO dto = new UserCreationDTO("Jean", "hlsdqghosufr", null);
        Assertions.assertFalse(dto.isValid(regexCollection.getNicknamePattern(), regexCollection.getEmailPattern()));
    }

    /**
     * Test isValid when password is blank
     */
    @DisplayName("Test isValid : password is blank")
    @Test
    void testIsValidPasswordBlank() {
        UserCreationDTO dto = new UserCreationDTO("Jean", "    ", "regis@thales.fr");
        Assertions.assertFalse(dto.isValid(regexCollection.getNicknamePattern(), regexCollection.getEmailPattern()));
        dto = new UserCreationDTO("Jean", "", "regis@thales.fr");
        Assertions.assertFalse(dto.isValid(regexCollection.getNicknamePattern(), regexCollection.getEmailPattern()));
    }

    /**
     * Test isValid when email doesn't match the pattern
     */
    @DisplayName("Test isValid : email doesn't match the pattern")
    @Test
    void testIsValidEmailInvalid() {
        UserCreationDTO dto = new UserCreationDTO("Jean", "dfghjklhjjh", "regis@.fr");
        Assertions.assertFalse(dto.isValid(regexCollection.getNicknamePattern(), regexCollection.getEmailPattern()));
    }

    /**
     * Test isValid when nickname doesn't match the pattern
     */
    @DisplayName("Test isValid : nickname doesn't match the pattern")
    @Test
    void testIsValidNicknameInvalid() {
        UserCreationDTO dto = new UserCreationDTO("Rébecca", "dfghjklhjjh", "regis@thales.fr");
        Assertions.assertFalse(dto.isValid(regexCollection.getNicknamePattern(), regexCollection.getEmailPattern()));
    }

    /**
     * Test isValid when everything is valid
     */
    @DisplayName("Test isValid : everything is valid")
    @Test
    void testIsValid() {
        UserCreationDTO dto = new UserCreationDTO("Regis", "dfghjklhjjh", "regis@thales.fr");
        Assertions.assertTrue(dto.isValid(regexCollection.getNicknamePattern(), regexCollection.getEmailPattern()));
    }
}
