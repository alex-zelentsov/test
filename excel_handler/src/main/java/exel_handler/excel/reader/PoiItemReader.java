package exel_handler.excel.reader;

import exel_handler.excel.exception.ExcelFileParseException;
import exel_handler.excel.mapping.RowMapper;
import exel_handler.excel.rowset.PoiRowSetFactory;
import exel_handler.excel.rowset.RowSet;
import exel_handler.excel.rowset.RowSetFactory;
import exel_handler.excel.sheet.PoiSheet;
import exel_handler.excel.sheet.Sheet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.core.io.Resource;
import org.springframework.util.ClassUtils;

import java.io.InputStream;

/**
 * @author azelentsov
 */
public class PoiItemReader<T> extends AbstractItemCountingItemStreamItemReader<T> implements
        ResourceAwareItemReaderItemStream<T> {

    private final Log logger = LogFactory.getLog(getClass());
    private Resource resource;
    private RowMapper<T> rowMapper;
    private boolean noInput = false;
    private RowSetFactory rowSetFactory = new PoiRowSetFactory();
    private RowSet rs;
    private Workbook workbook;
    private InputStream workbookStream;

    public PoiItemReader() {
        super();
        this.setName(ClassUtils.getShortName(this.getClass()));
    }

    @Override
    protected T doRead() throws Exception {
        if (this.noInput || this.rs == null) {
            return null;
        }

        if (rs.next()) {
            try {
                return this.rowMapper.mapRow(rs);
            } catch (final Exception e) {
                throw new ExcelFileParseException("Exception parsing Excel file.", e, this.resource.getDescription(), rs.getCurrentRowIndex(), rs.getCurrentRow());
            }
        }
        return null;
    }

    @Override
    protected void doOpen() throws Exception {
        if(this.resource ==  null) {
            return;
        }
        this.noInput = true;
        if (!this.resource.exists()) {
            logger.warn("Input resource does not exist '" + this.resource.getDescription() + "'.");
            return;
        }

        if (!this.resource.isReadable()) {
            logger.warn("Input resource is not readable '" + this.resource.getDescription() + "'.");
            return;
        }

        this.openExcelFile(this.resource);
        this.openSheet();
        this.noInput = false;
        if (logger.isDebugEnabled()) {
            logger.debug("Opened workbook [" + this.resource.getFilename() + "] with " + this.getNumberOfSheets() + " sheets.");
        }
    }

    private void openSheet() {
        final Sheet sheet = this.getSheet(0);
        this.rs =rowSetFactory.create(sheet);

        if (logger.isDebugEnabled()) {
            logger.debug("Opened sheet " + sheet.getName() + ", with " + sheet.getNumberOfRows() + " rows.");
        }

    }

    public void setResource(final Resource resource) {
        this.resource = resource;
    }

    private Sheet getSheet(final int sheet) {
        return new PoiSheet(this.workbook.getSheetAt(sheet));
    }

    private int getNumberOfSheets() {
        return this.workbook.getNumberOfSheets();
    }

    @Override
    protected void doClose() throws Exception {
        if (workbook != null) {
            this.workbook.close();
        }

        if (workbookStream != null) {
            workbookStream.close();
        }
        this.workbook=null;
        this.workbookStream=null;
    }

    private void openExcelFile(final Resource resource) throws Exception {
        workbookStream = resource.getInputStream();

        this.workbook = WorkbookFactory.create(workbookStream);
        this.workbook.setMissingCellPolicy(Row.CREATE_NULL_AS_BLANK);
    }

    public void setRowMapper(final RowMapper<T> rowMapper) {
        this.rowMapper = rowMapper;
    }

}
