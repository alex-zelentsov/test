package ru.notes.service.note;

import org.springframework.web.multipart.MultipartFile;
import ru.notes.model.note.Note;

import java.util.List;

/**
 * @author azelentsov
 */
public interface INoteService {
    Note getNote(Long id);

    List<Note> getAllNotes();

    Note addNote(Note note);

    Note updateNote(Note note, Long id);

    void deleteNote(Long id);

    void uploadFile(Long id, MultipartFile file);
}
