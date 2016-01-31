package ru.notes.service.change;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.notes.NotesWsApplication;
import ru.notes.model.note.Change;
import ru.notes.model.note.Note;
import ru.notes.repository.note.NoteRepository;
import ru.notes.enums.ChangeType;

import java.util.Collections;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * @author azelentsov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = NotesWsApplication.class)
@WebAppConfiguration
@TestPropertySource(locations="classpath:application.properties")
public class ChangeServiceTest {

    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private IChangeService changeService;

    private Note savedNote = null;
    private ChangeType changeType = ChangeType.UPDATE;

    @Before
    public void before() {
        savedNote = noteRepository.save(new Note("title", "content", Collections.emptyList(), Collections.emptyList()));
    }

    @Test
    @Transactional
    public void testThatAddChangeCorrect() throws Exception {
        changeService.addChange(changeType, null, savedNote);
        Note foundNote = noteRepository.findOne(savedNote.getId());

        List<Change> changeList = foundNote.getChanges();
        assertEquals(1, changeList.size());
        Change change = changeList.get(0);
        assertEquals(changeType.name(), change.getType());

    }

    @Test
    @Transactional
    public void testGetAllChangesAfterAddingNoteCorrect() {
        changeService.addChange(changeType, null,  savedNote);
        List<Change> allChanges = changeService.getAllChanges();

        assertEquals(1, allChanges.size());
        Change change = allChanges.get(0);
        assertEquals(changeType.name(), change.getType());

    }

    @After
    public void clean(){
        noteRepository.deleteAll();
    }
}