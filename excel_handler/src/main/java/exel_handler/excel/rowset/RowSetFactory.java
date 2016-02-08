package exel_handler.excel.rowset;

import exel_handler.excel.sheet.Sheet;

/**
 * @author azelentsov
 */
public interface RowSetFactory {

    RowSet create(Sheet sheet);
}
