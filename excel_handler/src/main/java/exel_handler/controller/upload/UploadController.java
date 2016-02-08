package exel_handler.controller.upload;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author azelentsov
 */
public interface UploadController {
    void uploadFile(MultipartFile file) throws Exception;
}
