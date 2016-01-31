package ru.notes.repository.change;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.notes.model.note.Change;

/**
 * @author azelentsov
 */
@Repository
public interface ChangeRepository extends JpaRepository<Change, Long> {
}
