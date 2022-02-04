package fr.oukilson.backend.security;

import fr.oukilson.backend.configuration.UserConfiguration;
import fr.oukilson.backend.repository.UserRepository;
import fr.oukilson.backend.service.UserService;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

@Import({UserRepository.class, UserConfiguration.class})
public class SecurityEnabledSetup {
    @MockBean
    protected UserService userService;

    @MockBean
    protected CustomPasswordEncoder customPasswordEncoder;

    @MockBean
    private UserRepository userRepository;
}
