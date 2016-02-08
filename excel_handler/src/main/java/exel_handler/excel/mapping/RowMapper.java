package exel_handler.excel.mapping;

import exel_handler.excel.rowset.RowSet;

/**
 * @author azelentsov
 */
public interface RowMapper<T> {

    T mapRow(RowSet rs) throws Exception;

}
