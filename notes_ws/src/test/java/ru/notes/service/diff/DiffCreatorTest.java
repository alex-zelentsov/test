package ru.notes.service.diff;

import org.junit.Test;
import ru.notes.model.note.Attachment;
import ru.notes.model.note.Diff;
import ru.notes.model.note.Note;
import ru.notes.model.note.Tag;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author azelentsov
 */
public class DiffCreatorTest {

    IDiffCreator diffCreator = new DiffCreator();
    @Test
    public void testCreateDiff() throws Exception {
        Note oldNote = new Note(
                "title1",
                "content",
                Collections.singletonList(new Tag("tag1")),
                Collections.singletonList(new Attachment("file1", "content1".getBytes())));
        Note newNote = new Note(
                "title2",
                "content",
                Arrays.asList(new Tag("tag2"), new Tag("tag3")),
                Collections.singletonList(new Attachment("file2", "content2".getBytes())));

        List<Diff> diffs = diffCreator.createDiffList(oldNote, newNote);

        String expectedDiff = "[Diff{id=0, type='TITLE', before='title1', after='title2'}, " +
                "Diff{id=0, type='TAGS', before='tag1', after='tag2, tag3'}, " +
                "Diff{id=0, type='ATTACHMENTS', before='file1', after='file2'}]";
        assertEquals(expectedDiff, diffs.toString());
    }
}