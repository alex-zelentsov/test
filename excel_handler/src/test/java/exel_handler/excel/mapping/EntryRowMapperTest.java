package exel_handler.excel.mapping;

import exel_handler.excel.rowset.DefaultRowSetFactory;
import exel_handler.excel.rowset.RowSet;
import exel_handler.excel.mapping.mock.MockSheet;
import exel_handler.model.Entry;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;

/**
 * @author azelentsov
 */
public class EntryRowMapperTest{

    @Test
    public void mapRow() throws Exception {
        EntryRowMapper entryRowMapper = new EntryRowMapper();
        final String[] row = new String[]{"12", "test", "10.4", "1358625600000"};
        MockSheet sheet = new MockSheet("mock", Collections.singletonList(row));
        RowSet rs = new DefaultRowSetFactory().create(sheet);
        assertTrue(rs.next());
        Entry entry = entryRowMapper.mapRow(rs);
        assertNotNull(entry);
        assertEquals(new Long(12L), entry.getCode());
        assertEquals("test", entry.getName());
        assertEquals(new Double(10.4d), entry.getPrice());
        assertEquals(1358625600000L, entry.getDate().getTime());

    }


}