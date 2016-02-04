package ru.notes.service.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.notes.model.note.Note;
import ru.notes.repository.note.NoteRepository;
import ru.notes.service.change.IChangeService;
import ru.notes.enums.ChangeType;
import ru.notes.service.upload.IFileUploadService;
import ru.notes.util.NoteUtils;

import java.util.List;

/**
 * @author azelentsov
 */
@Service
@Transactional
public class NoteService implements INoteService {

    private NoteRepository noteRepository;
    private IChangeService changeService;
    private IFileUploadService fileUploadService;

    @Autowired
    public NoteService(NoteRepository noteRepository,
            IChangeService changeService,
            IFileUploadService fileUploadService){
        this.noteRepository = noteRepository;
        this.changeService = changeService;
        this.fileUploadService = fileUploadService;
    }

    @Override
    public Note getNote(Long id) {
        return noteRepository.findOne(id);
    }

    @Override
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    @Override
    public Note addNote(Note note) {
        Note oldNote = noteRepository.findOne(note.getId());
        Note updatedNote = changeService.addChange(ChangeType.CREATE, oldNote, note);
        return noteRepository.save(updatedNote);
    }

    @Override
    public Note updateNote(Note note, Long id) {
        Note oldNote = noteRepository.findOne(id);
        Note updatedNote = changeService.addChange(ChangeType.UPDATE, oldNote, note);
        return noteRepository.save(updatedNote);
    }

    @Override
    public void deleteNote(Long id) {
        noteRepository.delete(id);
    }

    @Override
    public void uploadFile(Long id, MultipartFile file) {
        Note foundNote = noteRepository.findOne(id);
        Note oldNote = NoteUtils.createNewNoteFrom(foundNote);
        Note updatedNote = fileUploadService.uploadFileForNote(file, id);
        updatedNote = changeService.addChange(ChangeType.UPLOAD_FILE, oldNote, updatedNote);
        noteRepository.save(updatedNote);
    }


}
