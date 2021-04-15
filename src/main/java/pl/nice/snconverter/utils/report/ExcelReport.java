package pl.nice.snconverter.utils.report;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import pl.nice.snconverter.utils.report.message.ReportMessageContent;
import java.io.*;
import java.util.Objects;

@Component
public class ExcelReport extends Report{

    @Override
    public void createReport() {
        XSSFWorkbook workbook = new XSSFWorkbook();

        Sheet sheet = createSheet(workbook);
        createHeader(sheet, workbook);
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        for (int x = 0; x < data.size(); x++) {
            Row row = sheet.createRow(x + 1);
            row.setHeight((short) 300);
            String[] cellsValue = data.get(x).split(",");

            for (int y = 0; y < cellsValue.length; y++) {
                Cell cell = row.createCell(y);
                cell.setCellValue(cellsValue[y]);
                cell.setCellStyle(style);
            }
        }

        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + "temp11.xlsx";
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(fileLocation);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
          workbook.write(byteArrayOutputStream);
            reportBytes = byteArrayOutputStream.toByteArray();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Sheet createSheet(Workbook workbook) {
        String sheetName;
        sheetName = Objects.requireNonNullElse(reportName, ReportMessageContent.EXCEL_SHEET_DEFAULT_NAME);
        return workbook.createSheet(sheetName);
    }

    private void createHeader(Sheet sheet, Workbook workbook) {
        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Calibri");
        font.setFontHeightInPoints((short) 11);
        font.setBold(true);
        headerStyle.setFont(font);

        for (int x = 0; x < headersNames.size(); x++) {
            Cell headerCell = header.createCell(x);
            headerCell.setCellValue(headersNames.get(x));
            headerCell.setCellStyle(headerStyle);
        }
    }
}
//TODO sprawdzac czy dane przychodzace nie sa za duze i czy plik wynikowy nie jest za duzy
