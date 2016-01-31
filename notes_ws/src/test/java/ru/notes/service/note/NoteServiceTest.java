package ru.notes.service.note;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.notes.NotesWsApplication;
import ru.notes.enums.ChangeType;
import ru.notes.enums.DiffType;
import ru.notes.model.note.Attachment;
import ru.notes.model.note.Change;
import ru.notes.model.note.Diff;
import ru.notes.model.note.Note;
import ru.notes.repository.note.NoteRepository;
import ru.notes.service.upload.IFileUploadService;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author azelentsov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = NotesWsApplication.class)
@WebAppConfiguration
public class NoteServiceTest {
    public static final String TEST_NAME = "testName";
    public static final String NAME = "name";
    public static final String TEST_TYPE = "testType";
    public static final String TEST_CONTENT = "testContent";
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private INoteService noteService;

    @Test
    @Transactional
    public void testThatAfterUploadFileNoteContainsCorrectAttachmentAndChanges() throws Exception {
        MultipartFile file = new MockMultipartFile(NAME, TEST_NAME, TEST_TYPE, TEST_CONTENT.getBytes());
        long noteId = preparedNote();

        noteService.uploadFile(noteId, file);

        Note foundNote = noteRepository.findOne(noteId);
        checkAttachments(file, foundNote);
        checkChanges(foundNote);
    }

    private long preparedNote() {
        Note note = new Note("testTitle", "testContent", Collections.emptyList(), null);
        Note save = noteRepository.save(note);
        return save.getId();
    }

    private void checkChanges(Note foundNote) {
        List<Change> changes = foundNote.getChanges();
        assertEquals(1, changes.size());
        Change change = changes.get(0);
        assertEquals(ChangeType.UPLOAD_FILE.name(), change.getType());
        List<Diff> diffs = change.getDiffs();
        assertEquals(1, diffs.size());
        Diff diff = diffs.get(0);
        assertEquals(DiffType.ATTACHMENTS.name(), diff.getType());
        assertEquals("", diff.getBefore());
        assertEquals(TEST_NAME, diff.getAfter());
    }

    private void checkAttachments(MultipartFile file, Note foundNote) throws IOException {
        List<Attachment> attachments = foundNote.getAttachments();
        assertEquals(1, attachments.size());
        Attachment attachment = attachments.get(0);
        assertEquals(file.getOriginalFilename(), attachment.getName());
        assertTrue(Arrays.equals(file.getBytes(), attachment.getContent()));
    }

    @After
    public void clean(){
        noteRepository.deleteAll();
    }

}