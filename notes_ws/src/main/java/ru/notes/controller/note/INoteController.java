package ru.notes.controller.note;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.notes.model.note.Note;

import java.util.List;

/**
 * @author azelentsov
 */
public interface INoteController {
    Note getNote(Long id);

    List<Note> getAllNotes();

    Note addNote(Note note);

    Note updateNote(Note note, Long id);

    void deleteNote(Long id);

    void uploadFile(Long id, MultipartFile file);
}
