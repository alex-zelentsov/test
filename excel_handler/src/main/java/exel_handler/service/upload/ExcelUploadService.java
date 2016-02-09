package exel_handler.service.upload;

import exel_handler.service.handle.HandleService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author azelentsov
 */
public class ExcelUploadService implements UploadService {

    public static final String EXCEL_FILES_DIR = "src/main/resources/files/";
    public static final String DEFAULT_FILE_NAME = "excel_";
    public static final String TYPE = ".xlsx";

    private AtomicInteger fileNumber = new AtomicInteger(0);

    private HandleService handleService;

    public ExcelUploadService(HandleService handleService) {
        this.handleService = handleService;
    }

    @Override
    public void uploadFile(MultipartFile file) throws Exception {
        String filePath = saveFile(file);

        handleService.handleFile(filePath);
    }

    private String saveFile(MultipartFile file) throws IOException {
        return writeFileToPath(file.getInputStream(),
                EXCEL_FILES_DIR + DEFAULT_FILE_NAME + fileNumber.getAndIncrement() + TYPE);
    }

    private String writeFileToPath(InputStream is, String path) throws IOException {
        String result;
        Path filePath = Paths.get(path);
        try(
                OutputStream os = Files.newOutputStream(filePath,
                        StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND,
                        StandardOpenOption.SYNC);
                InputStream input = is
        ) {
            result = write(path, os, input);
        }
        return result;
    }

    private String write(String path, OutputStream os, InputStream input) throws IOException {
        String result;
        byte[] buffer = new byte[1024];
        int bytesRead;
        while((bytesRead = input.read(buffer)) !=-1) {
            os.write(buffer, 0, bytesRead);
        }
        result = path;
        return result;
    }

}
