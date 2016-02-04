package ru.notes.service.upload;

import org.springframework.web.multipart.MultipartFile;
import ru.notes.model.note.Note;

/**
 * @author azelentsov
 */
public interface IFileUploadService {
    Note uploadFileForNote(MultipartFile file, Long noteId);
}
