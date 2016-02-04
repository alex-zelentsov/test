package ru.notes.util;

import org.junit.Test;
import ru.notes.model.note.Attachment;
import ru.notes.model.note.Note;
import ru.notes.model.note.Tag;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author azelentsov
 */
public class NoteUtilsTest {

    public static final String TAG_1 = "tag1";
    public static final String TAG_2 = "tag2";
    public static final String FILE_1 = "file1";
    public static final String FILE_2 = "file2";

    List<Tag> tags = Arrays.asList(new Tag(TAG_1), new Tag(TAG_2));
    List<Attachment> attachments = Arrays.asList(
            new Attachment(FILE_1, "content1".getBytes()),
            new Attachment(FILE_2, "content2".getBytes()));
    Note note = new Note("title", "content", tags, attachments);

    @Test
    public void testGetTagsContent() throws Exception {
        String tagsContent = NoteUtils.getTagsContent(note);
        assertEquals(TAG_1 + ", " + TAG_2, tagsContent);
    }

    @Test
    public void testGetAttachmentsName() throws Exception {
        String attachmentsName = NoteUtils.getAttachmentsName(note);
        assertEquals(FILE_1 + ", " + FILE_2, attachmentsName);
    }

    @Test
    public void testCreateNoteFromExistNote() throws Exception {
        Note createdNote = NoteUtils.createNewNoteFrom(note);
        assertEquals(note, createdNote);
        assertTrue(note != createdNote);
    }
}