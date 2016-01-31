package ru.notes.service.change;

import ru.notes.model.note.Change;
import ru.notes.model.note.Note;
import ru.notes.enums.ChangeType;

import java.util.List;

/**
 * @author azelentsov
 */
public interface IChangeService {
    Note addChange(ChangeType changeType, Note oldNote, Note newNote);

    List<Change> getAllChangesForNoteId(Long id);

    List<Change> getAllChanges();

}
