package fr.oukilson.backend.repository;

import fr.oukilson.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNickname(String nickname);
    Optional<User> findByNicknameOrEmail(String nickname, String email);
    @Query(value = "SELECT * FROM user ORDER BY RAND() LIMIT 10", nativeQuery = true)
    List<User> find10RandomUsers();
    List<User> findAllByNicknameContainingIgnoreCase(String nickname);
}