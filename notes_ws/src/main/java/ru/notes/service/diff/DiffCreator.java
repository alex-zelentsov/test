package ru.notes.service.diff;

import org.springframework.stereotype.Service;
import ru.notes.model.note.Diff;
import ru.notes.model.note.Note;
import ru.notes.enums.DiffType;
import ru.notes.util.NoteUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author azelentsov
 */
@Service
public class DiffCreator implements IDiffCreator {

    public static final String BEFORE_UNDEFINED = "";

    @Override
    public List<Diff> createDiffList(Note oldNote, Note newNote) {
        List<Diff> diffs = new ArrayList<>();
        Note note = NoteUtils.createNewNoteFrom(oldNote);

        addDiff(DiffType.TITLE,
                note.getTitle(),
                newNote.getTitle(),
                diffs);
        addDiff(DiffType.CONTENT,
                note.getContent(),
                newNote.getContent(),
                diffs);
        addDiff(DiffType.TAGS,
                NoteUtils.getTagsContent(note),
                NoteUtils.getTagsContent(newNote),
                diffs);
        addDiff(DiffType.ATTACHMENTS,
                NoteUtils.getAttachmentsName(note),
                NoteUtils.getAttachmentsName(newNote),
                diffs);

        return diffs;
    }

    private void addDiff(DiffType diffType, Object oldValue, Object newValue, List<Diff> diffs) {
        if(isDiffNeeded(oldValue, newValue)) {
            add(diffType, oldValue, newValue, diffs);
        }
    }

    private boolean isDiffNeeded(Object oldValue, Object newValue) {
        return newValue != null && !newValue.equals(oldValue);
    }

    private void add(DiffType diffType, Object oldValue, Object newValue, List<Diff> diffs) {
        Diff diff = new Diff(diffType.name(), getStringValueOrNull(oldValue), getStringValueOrNull(newValue));
        if (!diff.getBefore().equals(diff.getAfter())) {
            diffs.add(diff);
        }
    }

    private String getStringValueOrNull(Object target) {
        return target == null ? BEFORE_UNDEFINED : target.toString();
    }

}
