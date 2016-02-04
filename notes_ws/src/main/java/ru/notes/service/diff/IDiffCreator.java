package ru.notes.service.diff;

import ru.notes.model.note.Diff;
import ru.notes.model.note.Note;

import java.util.List;

/**
 * @author azelentsov
 */
public interface IDiffCreator {
    List<Diff> createDiffList(Note oldNote, Note newNote);
}
