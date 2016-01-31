package ru.notes.repository.note;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.notes.model.note.Note;

/**
 * @author azelentsov
 */
@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
}
