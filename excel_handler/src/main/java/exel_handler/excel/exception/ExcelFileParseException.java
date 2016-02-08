package exel_handler.excel.exception;

import org.springframework.batch.item.ParseException;

/**
 * @author azelentsov
 */
public class ExcelFileParseException extends ParseException {

    private final String filename;
    private final String[] row;
    private final int rowNumber;

    public ExcelFileParseException(final String message, final Throwable cause, final String filename, final int rowNumber, final String[] row) {
        super(message, cause);
        this.filename = filename;
        this.rowNumber = rowNumber;
        this.row = row;
    }

    public String getFilename() {
        return filename;
    }

    public String[] getRow() {
        return row;
    }

    public int getRowNumber() {
        return rowNumber;
    }
}
