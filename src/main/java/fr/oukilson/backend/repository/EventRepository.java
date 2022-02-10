package fr.oukilson.backend.repository;

import fr.oukilson.backend.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findByUuid(String uuid);
    List<Event> findAllByStartingDateAfter(LocalDateTime date);
    List<Event> findAllByLocationTown(String town);
    List<Event> findAllByLocationTownContaining(String town);
    List<Event> findAllByCreatorId(long id);
    List<Event> findAllByRegisteredUsersId(long id);
    List<Event> findAllByCreatorIdOrRegisteredUsersIdOrWaitingUsersId(long id1, long id2, long id3);
    void deleteByUuid(String uuid);
}
