package exel_handler.controller.result;

import exel_handler.model.Entry;
import exel_handler.model.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author azelentsov
 */
@RestController
public class ExcelResultController implements ResultController {
    private EntryRepository entryRepository;

    @Autowired
    public ExcelResultController(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    @Override
    @RequestMapping("/result")
    public List<Entry> getAllEntries() {
        return entryRepository.findAll();
    }
}
