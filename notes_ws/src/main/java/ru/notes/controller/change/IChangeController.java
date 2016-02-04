package ru.notes.controller.change;

import ru.notes.model.note.Change;

import java.util.List;

/**
 * @author azelentsov
 */
public interface IChangeController {
    List<Change> getAllChangesForNoteId(Long id);

    List<Change> getAllChanges();
}
