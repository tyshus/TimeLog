package Apps.TimeLog.Tools;

import Apps.TimeLog.TimeLog.Report;
import Apps.TimeLog.TimeLog.TimeLog;
import javafx.scene.control.TableColumnBase;
import javafx.scene.control.TableView;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class ReportExcelExport {
	private String pth, xlsx_file;
	private TableView<TimeLog> tableView;
	private Model model = Model.getModel();

	@SuppressWarnings("unchecked")
	public ReportExcelExport(Report report) {

		tableView = report.getTableView();
		pth = report.getCompany() + "/TimeReports";
		xlsx_file = pth + "/" + report.getDateTo().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "_TimeReport.xlsx";
		model.createDirectory(pth);
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Time log report");

		DataFormat fmt = workbook.createDataFormat();
		XSSFCellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		Font headerFont = workbook.createFont();
		headerStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		headerFont.setColor(IndexedColors.BLACK.getIndex());
		headerFont.setBold(true);
		headerStyle.setFont(headerFont);

		XSSFCellStyle dateStyle = workbook.createCellStyle();
		dateStyle.setAlignment(HorizontalAlignment.CENTER);
		dateStyle.setDataFormat(fmt.getFormat("@"));
		Font dateFont = workbook.createFont();
		dateStyle.setFont(dateFont);

		XSSFCellStyle daoubleStyle = workbook.createCellStyle();
		daoubleStyle.setAlignment(HorizontalAlignment.CENTER);
		daoubleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		daoubleStyle.setDataFormat(fmt.getFormat("0.0"));
		Font doubleFont = workbook.createFont();
		daoubleStyle.setFont(doubleFont);

		XSSFCellStyle textStyle = workbook.createCellStyle();
		textStyle.setAlignment(HorizontalAlignment.CENTER);
		textStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		dateStyle.setDataFormat(fmt.getFormat("@"));
		Font textFont = workbook.createFont();
		textStyle.setFont(textFont);

		XSSFCellStyle longTextStyle = workbook.createCellStyle();
		longTextStyle.setAlignment(HorizontalAlignment.LEFT);
		longTextStyle.setWrapText(true);
		longTextStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		dateStyle.setDataFormat(fmt.getFormat("@"));
		Font longTextFont = workbook.createFont();
		longTextStyle.setFont(longTextFont);

		int i = 0;
		for (i = 0; i < 3; i++) {
			Row row = sheet.createRow(i);
			for (int j = 0; j < 3; j++) {
				XSSFCell cell = (XSSFCell) row.createCell(j);
				if (i == 0 && j == 0) {
					cell.setCellValue("Name, Surename:");
				} else if ((i == 0 && j == 1)) {
					cell.setCellValue(model.prop.getProperty("mail_greatings2"));
				} else if ((i == 1 && j == 0)) {
					cell.setCellValue("Period:");
				} else if ((i == 1 && j == 1)) {
					cell.setCellValue(report.getDateFrom().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) + "-"
							+ report.getDateTo().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")));
				}
				cell.setCellStyle(headerStyle);
			}
		}

		i = 2;
		Row row = sheet.createRow(i);
		for (int j = 0; j < tableView.getColumns().size(); j++) {
			XSSFCell cell = (XSSFCell) row.createCell(j);
			cell.setCellValue(((TableColumnBase<TimeLog, String>) tableView.getColumns().get(j)).getText());
			cell.setCellStyle(headerStyle);
		}
		i = 3;
		for (TimeLog timeLog : tableView.getItems()) {
			row = sheet.createRow(i);
			for (int j = 0; j < tableView.getColumns().size(); j++) {
				XSSFCell cell = (XSSFCell) row.createCell(j);
				cell.setCellStyle(textStyle);
				switch (j) {
				case 0:
					cell.setCellValue(timeLog.getId());
					break;
				case 1:
					cell.setCellValue(timeLog.getLogDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
					break;
				case 2:
					cell.setCellValue(timeLog.getContactName());
					break;
				case 3:
					cell.setCellValue(timeLog.getTaskType());
					break;
				case 4:
					cell.setCellValue(timeLog.getTaskDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));

					break;
				case 5:
					cell.setCellValue(timeLog.getTaskName());
					break;
				case 6:
					cell.setCellValue(timeLog.getTime());
					cell.setCellStyle(daoubleStyle);
					break;
				case 7:
					cell.setCellValue(timeLog.getTaskBody());
					cell.setCellStyle(longTextStyle);
					break;
				case 8:
					cell.setCellValue(timeLog.getMyComment());
					cell.setCellStyle(longTextStyle);
					break;
				}
			}
			i++;
		}

		Row rowTotal = sheet.createRow(i + 1);
		for (int j = 0; j < 3; j++) {
			XSSFCell cell = (XSSFCell) rowTotal.createCell(j);
			if (j == 0) {
				cell.setCellValue("Total hours:");
			} else if (j == 1) {
				cell.setCellValue(String.valueOf(report.getTotalHours()));
			}
			cell.setCellStyle(headerStyle);
		}

		for (int j = 0; j < 10; j++) {
			sheet.autoSizeColumn(j);
		}
		try {
			FileOutputStream fileOut;
			fileOut = new FileOutputStream(xlsx_file);
			try {
				workbook.write(fileOut);
				fileOut.close();
				report.setPrintout(xlsx_file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}
