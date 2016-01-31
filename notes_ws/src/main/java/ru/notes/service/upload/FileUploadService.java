package ru.notes.service.upload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.notes.model.note.Attachment;
import ru.notes.model.note.Note;
import ru.notes.repository.note.NoteRepository;
import ru.notes.service.change.IChangeService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author azelentsov
 */
@Service
public class FileUploadService implements IFileUploadService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadService.class);

    private NoteRepository noteRepository;

    @Autowired
    public FileUploadService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public Note uploadFileForNote(MultipartFile file, Long noteId) {
        String originalFilename = file.getOriginalFilename();
        Note resultNote = null;
        try {
            Note note = noteRepository.findOne(noteId);
            resultNote = addAttachmentToNode(file, note);
        } catch (IOException e) {
            LOGGER.error("Attachment: {} is incorrect for noteId: {}", originalFilename, noteId);
        }
        return resultNote;
    }

    private Note addAttachmentToNode(MultipartFile file, Note note) throws IOException {
        List<Attachment> attachments = note.getAttachments();
        if(attachments == null) {
            attachments = new ArrayList<>();
        }
        Attachment attachment = new Attachment(file.getOriginalFilename(), file.getBytes());
        attachments.add(attachment);
        note.setAttachments(attachments);
        return note;
    }
}
