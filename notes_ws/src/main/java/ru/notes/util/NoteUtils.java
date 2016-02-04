package ru.notes.util;

import ru.notes.model.note.Attachment;
import ru.notes.model.note.Note;
import ru.notes.model.note.Tag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author azelentsov
 */
public class NoteUtils {

    public static final String SEPARATOR = ", ";
    public static final String EMPTY_STRING = "";

    private NoteUtils(){

    }

    public static String getTagsContent(Note note){
        List<Tag> tags = note.getTags();
        StringBuilder result = new StringBuilder(EMPTY_STRING);
        if(tags != null) {
            for (Tag tag : tags) {
                String text = tag.getText();
                appendIfNeed(result, text);
            }
            result = replaceLastSeparator(result);
        }
        return result.toString();
    }

    public static String getAttachmentsName(Note note){
        List<Attachment> attachments = note.getAttachments();
        StringBuilder result = new StringBuilder(EMPTY_STRING);
        if(attachments != null) {
            for (Attachment attachment : attachments) {
                String name = attachment.getName();
                appendIfNeed(result, name);
            }

            result = replaceLastSeparator(result);
        }
        return result.toString();
    }

    public static Note createNewNoteFrom(Note note) {
        if(note == null) {
            return new Note(null, null, null, null);
        }

        List<Attachment> attachments = note.getAttachments();
        List<Attachment> newAttachments = new ArrayList<>(
                attachments == null ? Collections.emptyList() : attachments);
        return new Note(note.getTitle(),
                note.getContent(),
                note.getTags(),
                newAttachments);

    }

    private static void appendIfNeed(StringBuilder result, String text) {
        if(text != null) {
            result.append(text).append(SEPARATOR);
        }
    }

    private static StringBuilder replaceLastSeparator(StringBuilder target) {
        StringBuilder result = target;
        if(target.indexOf(SEPARATOR) > 0) {
           result = target.delete(target.length() - SEPARATOR.length(), target.length());
        }
        return result;
    }

}
