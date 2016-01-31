package ru.notes.controller.note;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.notes.enums.ChangeType;
import ru.notes.model.note.Note;
import ru.notes.service.note.INoteService;

import java.util.List;

/**
 * @author azelentsov
 */
@RestController
public class NoteController implements INoteController {
    private static final Logger LOGGER = LoggerFactory.getLogger(NoteController.class);

    private INoteService noteService;

    @Autowired
    public NoteController(INoteService noteService){
        this.noteService = noteService;
    }

    @Override
    @RequestMapping(value = "/note/{id}", method = RequestMethod.GET)
    public Note getNote(@PathVariable Long id) {
        Note note = noteService.getNote(id);
        LOGGER.info("Retrieved note with id {}", id);
        return note;
    }

    @Override
    @RequestMapping(value = "/note", method = RequestMethod.GET)
    public List<Note> getAllNotes() {
        List<Note> allNotes = noteService.getAllNotes();
        LOGGER.info("Retrieved all notes");
        return allNotes;
    }

    @Override
    @RequestMapping(value = "/note", method = RequestMethod.PUT)
    public Note addNote(@RequestBody Note note) {
        Note addedNote = noteService.addNote(note);
        LOGGER.info("Added note {}", note);
        return addedNote;
    }

    @Override
    @RequestMapping(value = "/note/{id}", method = RequestMethod.POST)
    public Note updateNote(@RequestBody Note note, @PathVariable Long id) {
        Note updatedNote = noteService.updateNote(note, id);
        LOGGER.info("Updated note with id {}", id);
        return updatedNote;
    }

    @Override
    @RequestMapping(value = "/note/{id}", method = RequestMethod.DELETE)
    public void deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        LOGGER.info("Deleted note with id {}", id);
    }

    @Override
    @RequestMapping(value = "/note/{id}/upload", method = RequestMethod.POST)
    public void uploadFile(@PathVariable Long id, @RequestParam MultipartFile file) {
        noteService.uploadFile(id, file);
        LOGGER.info("Upload file {} for note with id {}", file.getOriginalFilename(), id);
    }
}
