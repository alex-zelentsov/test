package exel_handler.excel.rowset;

import exel_handler.excel.sheet.Sheet;

/**
 * @author azelentsov
 */
public class PoiRowSet implements RowSet {

    private final Sheet sheet;

    private int currentRowIndex = -1;
    private String[] currentRow;

    PoiRowSet(Sheet sheet) {
        this.sheet = sheet;
    }

    @Override
    public boolean next() {
        currentRow = null;
        currentRowIndex++;
        if (currentRowIndex < sheet.getNumberOfRows()) {
            currentRow = sheet.getRow(currentRowIndex);
            return true;
        }
        return false;
    }

    @Override
    public int getCurrentRowIndex() {
        return this.currentRowIndex;
    }

    @Override
    public String[] getCurrentRow() {
        return this.currentRow;
    }
}
