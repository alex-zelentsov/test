package ru.notes.repository.note;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.notes.NotesWsApplication;
import ru.notes.model.note.Change;
import ru.notes.model.note.Diff;
import ru.notes.model.note.Note;
import ru.notes.enums.ChangeType;
import ru.notes.enums.DiffType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author azelentsov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = NotesWsApplication.class)
@WebAppConfiguration
public class NoteRepositoryTest {

    @Autowired
    private NoteRepository noteRepository;

    private Note note;

    @Before
    public void before() {
        note = noteRepository.save(new Note("title", "content", Collections.emptyList(), Collections.emptyList()));
    }

    @Test
    public void testThatNoteWasSaved(){
        assertEquals(note, noteRepository.findOne(note.getId()));
    }

    @Test
    public void testThatFoundNullForNotExistNote(){
        Note note = noteRepository.findOne(999L);
        assertNull(note);
    }

    @Test
    public void testThatDeleteCorrectForExistNote(){
        long nodeId = note.getId();
        noteRepository.delete(nodeId);
        assertNull(noteRepository.findOne(nodeId));
    }

    @Test
    @Transactional
    public void testAddChangeForNote() {
        Change change = createChange();
        note = setChangesToNote(change, note);

        Note updatedNote = noteRepository.save(note);

        List<Change> changeList = updatedNote.getChanges();
        assertEquals(1, changeList.size());
        Change resultChange = changeList.get(0);
        assertEquals(change, resultChange);
    }

    private Change createChange() {
        Diff diffOne = new Diff(DiffType.TITLE.name(), "before test1", "after test1");
        Diff diffTwo = new Diff(DiffType.TITLE.name(), "before test2", "after test2");
        List<Diff> diffs = new ArrayList<>();
        diffs.add(diffOne);
        diffs.add(diffTwo);
        return new Change(ChangeType.UPLOAD_FILE.name(), LocalDateTime.now(), diffs);
    }

    private Note setChangesToNote(Change change, Note targetNote) {
        List<Change> changes = targetNote.getChanges();
        if(changes == null) {
            changes = new ArrayList<>();
        }
        changes.add(change);

        targetNote.setChanges(changes);
        return targetNote;
    }

    @After
    public void clean(){
        noteRepository.deleteAll();
    }

}