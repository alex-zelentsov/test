package exel_handler.service.handle;

import exel_handler.AppConfig;
import exel_handler.model.Entry;
import exel_handler.model.EntryRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * @author azelentsov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(AppConfig.class)
@WebAppConfiguration
public class ExcelHandleServiceTest {

    @Autowired
    HandleService handleService;
    @Autowired
    EntryRepository entryRepository;

    @Test
    public void handleFileWith10Entries() throws Exception {
        handleService.handleFile("src/test/resources/test_10_entries.xlsx");
        List<Entry> entryList = entryRepository.findAll();
        assertEquals(10, entryList.size());
    }

    @Test
    public void handleFileWith100Entries() throws Exception {
        handleService.handleFile("src/test/resources/test_100_entries.xlsx");
        List<Entry> entryList = entryRepository.findAll();
        assertEquals(100, entryList.size());
    }

    @Test
    public void handleFileWith1Entries() throws Exception {
        handleService.handleFile("src/test/resources/test_1_entries.xlsx");
        List<Entry> entryList = entryRepository.findAll();
        assertEquals(1, entryList.size());

        Entry entry = entryList.get(0);
        assertEquals(new Long(1L), entry.getCode());
        assertEquals("test1", entry.getName());
        assertEquals(1.3d, entry.getPrice());
        assertEquals(1357972261000L, entry.getDate().getTime());
    }

    @After
    public void cleanUp() {
        entryRepository.deleteAll();
    }
}