package ru.notes.controller.change;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.notes.model.note.Change;
import ru.notes.service.change.ChangeService;

import java.util.List;

/**
 * @author azelentsov
 */
@RestController
public class ChangeController implements IChangeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChangeController.class);

    private ChangeService changeService;

    @Autowired
    public ChangeController(ChangeService changeService) {
        this.changeService = changeService;
    }

    @Override
    @RequestMapping(value = "/note/{id}/changes", method = RequestMethod.GET)
    public List<Change> getAllChangesForNoteId(@PathVariable Long id){
        List<Change> changes = changeService.getAllChangesForNoteId(id);
        LOGGER.info("Retrieved all changes for note with id {}", id);
        return changes;
    }

    @Override
    @RequestMapping(value = "/changes", method = RequestMethod.GET)
    public List<Change> getAllChanges(){
        List<Change> changes = changeService.getAllChanges();
        LOGGER.info("Retrieved all changes");
        return changes;
    }



}
