package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.Select;

import TestBase.TestBaseUtility;
import io.restassured.RestAssured;

public class TestUtilities extends TestBaseUtility {

	// we can also define this inside config properties file
	public static final long PAGE_LOAD_TIMEOUT = 30;
	public static final long IMPLICIT_WAIT = 20;

	// more utils will be added, select, alert, action, screenshot, excel read and
	// write, highlight element,action

	public static void screenGrab(String fileName) {
		TakesScreenshot screenshot = (TakesScreenshot) driver;
		File source = screenshot.getScreenshotAs(OutputType.FILE);

		String time = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss").format(new Date());
		File destination = new File("screenGrabs//" + fileName + "-" + time + ".png");

		try {
			FileHandler.copy(source, destination);
			System.out.println("screen grab done for: " + fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// alert utility
	public static void alertAccept() {
		Alert alert = driver.switchTo().alert();
		alert.accept();
		System.out.println("Alert accepted");
	}

	public static void alertDismiss() {
		Alert alert = driver.switchTo().alert();
		alert.dismiss();
		System.out.println("Alert Dismissed");
	}

	public static void alertPrompt(String prompt) {
		Alert alert = driver.switchTo().alert();
		alert.sendKeys(prompt);
		System.out.println("keys send to alert :" + prompt);
	}

	public static String getAlertText() {
		Alert alert = driver.switchTo().alert();
		return alert.getText();
	}

	// get title
	public static String getPageTitle() {
		return driver.getTitle();
	}

	// link validation
	public static int validateLink(String url_to_validate) {
		return RestAssured.given().baseUri(url_to_validate).get().getStatusCode();
	}

	// image validation
	public static boolean validateImage(WebElement imageElement) {
		String imgAttributeBroken = imageElement.getAttribute("naturalWidth"); // it gives 0 if broken
		if (imgAttributeBroken.equals("0")) {
			return false; // false image broken
		} else {
			return true; // true is image not broken
		}
	}

	// select by index
	public static void selectByIndex(WebElement element, int index) {
		Select select = new Select(element);
		select.selectByIndex(index);
	}

	// select by value
	public static void selectByValue(WebElement element, String value) {
		Select select = new Select(element);
		select.selectByValue(value);
	}

	// select by visible text
	public static void selectByVisibleText(WebElement element, String text) {
		Select select = new Select(element);
		select.selectByVisibleText(text);
	}

	// get all options
	public static List<String> getAllOptions(WebElement element) {
		Select select = new Select(element);
		List<WebElement> options = select.getOptions();
		return options.parallelStream().map(e -> e.getText()).collect(Collectors.toList());
	}

	// these also an be extended to deSelect as well

	// write to excel
	public static void writeListToExcelUsingPOI(LinkedList<LinkedList<String>> all_data, String fileName)
			throws IOException {
		System.out.println("Excel writing started..");
		// using apache POI workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet(prop.getProperty("sheetName"));

		XSSFFont font = sheet.getWorkbook().createFont();
		font.setBold(true);
		font.setFontHeightInPoints((short) 11);

		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setFont(font);
		cellStyle.setWrapText(false);
		cellStyle.setFont(font);

		// creating header rows
		Row row1 = sheet.createRow(0);

		Cell cellTitle = row1.createCell(0);
		cellTitle.setCellStyle(cellStyle);
		cellTitle.setCellValue("phone models");

		Cell cellAuthor = row1.createCell(1);
		cellAuthor.setCellStyle(cellStyle);
		cellAuthor.setCellValue("price");

		int rowSize = all_data.size();
		int colSize = all_data.get(0).size();

		// creating empty rows first, so it will not overwrite previous written columns
		// in that same row
		for (int j = 0; j < colSize; j++) {
			Row row = sheet.createRow(j + 1);
		}

		// main loop to writing to excel
		for (int i = 0; i < rowSize; i++) {
			LinkedList<String> data = all_data.get(i);
			for (int j = 0; j < colSize; j++) {
				Row row = sheet.getRow(j + 1); // we already created empty rows, now just getting that row
				Cell cell = row.createCell(i); // cell should not change, as we are writing row wise
				cell.setCellValue(data.get(j));
			}
		}

		String time = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss").format(new Date());
		File status_file = new File("C:\\Users\\sasidaran.kumarasamy\\Downloads\\" + fileName + "_" + time + ".xlsx");

		FileOutputStream fileOut = new FileOutputStream(status_file);
		workbook.write(fileOut);
		fileOut.close();
		workbook.close();

		System.out.println("Excel write finished!");
	}

	public static LinkedHashSet<String> readFromExcel(FileInputStream inputStream) throws IOException {

		String sheetName = prop.getProperty("sheetName");

		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		Sheet sheet = workbook.getSheet(sheetName);

		LinkedHashSet<String> keys = new LinkedHashSet<>();

		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				String cellValue = cell.getStringCellValue();
				if (!cellValue.isBlank()) {
					keys.add(cellValue);
				}
			}
		}
		workbook.close();
		return keys;
	}

}
