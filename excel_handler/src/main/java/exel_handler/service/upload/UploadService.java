package exel_handler.service.upload;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author azelentsov
 */
public interface UploadService {
    void uploadFile(MultipartFile file) throws Exception;
}
