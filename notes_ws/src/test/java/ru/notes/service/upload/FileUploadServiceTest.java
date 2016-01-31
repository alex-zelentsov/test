package ru.notes.service.upload;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.notes.NotesWsApplication;
import ru.notes.model.note.Attachment;
import ru.notes.model.note.Note;
import ru.notes.repository.note.NoteRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * @author azelentsov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = NotesWsApplication.class)
@WebAppConfiguration
public class FileUploadServiceTest {
    public static final String TEST_FILE_NAME = "testFileName";
    public static final MultipartFile FILE = new MockMultipartFile(TEST_FILE_NAME, "testContent".getBytes());
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private IFileUploadService fileUploadService;

    @Test
    @Transactional
    public void testUploadCorrectFileForNodeWithoutAttachments() throws Exception {
        Note note = noteRepository.save(new Note("title", "content", Collections.emptyList(), null));

        fileUploadService.uploadFileForNote(FILE, note.getId());

        int attachmentsCount = 1;
        int attachmentForCheck = 0;
        checkAttachments(note, attachmentsCount, attachmentForCheck);
    }

    @Test
    @Transactional
    public void testUploadCorrectFileForNodeWithAttachments() throws Exception {
        Note note = createNote(FILE);

        fileUploadService.uploadFileForNote(FILE, note.getId());

        int attachmentsCount = 2;
        int attachmentForCheck = 1;
        checkAttachments(note, attachmentsCount, attachmentForCheck);
    }

    private void checkAttachments(Note note, int attachmentsCount, int attachmentForCheck) throws IOException {
        Note foundNode = noteRepository.findOne(note.getId());
        List<Attachment> foundAttachments = foundNode.getAttachments();
        assertEquals(attachmentsCount, foundAttachments.size());
        Attachment foundAttachment = foundAttachments.get(attachmentForCheck);
        assertTrue(Arrays.equals(FILE.getBytes(), foundAttachment.getContent()));
    }

    private Note createNote(MultipartFile file) throws IOException {
        Note note = new Note();
        note.setContent("test");
        List<Attachment> attachments = new ArrayList<>();
        attachments.add(new Attachment(file.getName(), file.getBytes()));
        note.setAttachments(attachments);
        return noteRepository.save(note);
    }

    @After
    public void clean(){
        noteRepository.deleteAll();
    }


}