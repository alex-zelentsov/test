package exel_handler.controller.upload;

import exel_handler.service.upload.UploadService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author azelentsov
 */
@RestController
public class ExcelUploadController implements UploadController {

    private final Log logger = LogFactory.getLog(getClass());

    private UploadService uploadService;

    @Autowired
    public ExcelUploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @Override
    @RequestMapping("/upload")
    public void uploadFile(@RequestParam MultipartFile file){
        try {
            uploadService.uploadFile(file);
        } catch (Exception e) {
            logger.error("Error in file uploading: ", e);
        }

    }
}
