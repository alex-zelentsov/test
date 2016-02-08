package exel_handler.excel.reader;

import exel_handler.excel.mapping.EntryRowMapper;
import exel_handler.model.Entry;
import org.junit.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.core.io.ClassPathResource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author azelentsov
 */
public class PoiItemReaderTest {

    PoiItemReader<Entry> reader = new PoiItemReader<>();

    @Test
    public void fileReadFor1Entry() throws Exception {
        reader.setRowMapper(new EntryRowMapper());
        reader.setResource(new ClassPathResource("test_1_entries.xlsx"));
        ExecutionContext executionContext = new ExecutionContext();
        reader.open(executionContext);
        Entry entry = reader.read();
        assertEquals(new Long(1L), entry.getCode());
        assertEquals("test1", entry.getName());
        assertEquals(new Double(1.3d), entry.getPrice());
        assertEquals(1357972261000L, entry.getDate().getTime());
    }
    @Test
    public void fileReadFor10Entry() throws Exception {
        reader.setRowMapper(new EntryRowMapper());
        reader.setResource(new ClassPathResource("test_10_entries.xlsx"));
        ExecutionContext executionContext = new ExecutionContext();
        reader.open(executionContext);
        Entry entry;
        List<Entry> entries = new ArrayList<>();

        do {
            entry = reader.read();
            entries.add(entry);
        } while (entry != null);

        assertEquals(11, entries.size());
    }
}