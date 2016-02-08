package exel_handler.excel.mapping;

import exel_handler.model.Entry;
import exel_handler.excel.rowset.RowSet;

import java.sql.Date;

/**
 * @author azelentsov
 */
public class EntryRowMapper implements RowMapper<Entry> {

    @Override
    public Entry mapRow(final RowSet rs) throws Exception {
        String[] currentRow = rs.getCurrentRow();

        Long code = (long) Double.valueOf(currentRow[0]).intValue();
        String name = currentRow[1];
        Double price = Double.valueOf(currentRow[2]);
        Date date = new Date(Long.valueOf(currentRow[3]));

        return new Entry(code, name, price, date);
    }

}
