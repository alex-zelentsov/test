package exel_handler.excel.rowset;

import exel_handler.excel.sheet.Sheet;

/**
 * @author azelentsov
 */
public class DefaultRowSetFactory implements RowSetFactory {

    @Override
    public RowSet create(Sheet sheet) {
        return new DefaultRowSet(sheet);
    }

}
