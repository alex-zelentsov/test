package ru.notes.service.change;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.notes.model.note.Change;
import ru.notes.model.note.Diff;
import ru.notes.model.note.Note;
import ru.notes.repository.change.ChangeRepository;
import ru.notes.repository.note.NoteRepository;
import ru.notes.service.diff.IDiffCreator;
import ru.notes.enums.ChangeType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author azelentsov
 */
@Service
public class ChangeService implements IChangeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChangeService.class);

    private NoteRepository noteRepository;
    private ChangeRepository changeRepository;
    private IDiffCreator diffCreator;

    @Autowired
    public ChangeService(
            NoteRepository noteRepository,
            ChangeRepository changeRepository,
            IDiffCreator diffCreator) {
        this.noteRepository = noteRepository;
        this.changeRepository = changeRepository;
        this.diffCreator = diffCreator;
    }

    @Override
    public Note addChange(ChangeType changeType, Note oldNote, Note newNote) {
        List<Diff> diffs = diffCreator.createDiffList(oldNote, newNote);

        newNote = addDiffToNote(changeType, newNote, diffs);

        LOGGER.info("Change with type: {} added for noteId: {}", changeType, newNote.getId());
        return newNote;
    }

    private Note addDiffToNote(ChangeType changeType, Note newNote, List<Diff> diffs) {
        List<Change> changes = newNote.getChanges();
        if(changes == null){
            changes = new ArrayList<>();
        }
        changes.add(new Change(changeType.name(), LocalDateTime.now(), diffs));

        newNote.setChanges(changes);
        return newNote;
    }

    @Override
    public List<Change> getAllChangesForNoteId(Long id) {
        Note foundNode = noteRepository.findOne(id);
        List<Change> changes = foundNode.getChanges();

        LOGGER.info("Retrieved changes for noteId: {}", id);
        return changes;
    }

    @Override
    public List<Change> getAllChanges() {
        List<Change> changes = changeRepository.findAll();
        LOGGER.info("Retrieved all changes");
        return changes;
    }
}