package exel_handler.excel.sheet;

/**
 * @author azelentsov
 */
public interface Sheet {

    int getNumberOfRows();

    String getName();

    String[] getRow(int rowNumber);

    int getNumberOfColumns();
}
