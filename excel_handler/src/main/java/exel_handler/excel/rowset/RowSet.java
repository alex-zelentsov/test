package exel_handler.excel.rowset;

/**
 * @author azelentsov
 */
public interface RowSet {

    boolean next();

    int getCurrentRowIndex();

    String[] getCurrentRow();
}
