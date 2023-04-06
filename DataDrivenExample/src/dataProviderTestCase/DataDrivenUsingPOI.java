package dataProviderTestCase;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class DataDrivenUsingPOI {

	static List<String> userNameList=new ArrayList<>();
	static List<String> passwordList=new ArrayList<>();

	public void readExcel() throws IOException {


		FileInputStream excelFile=new FileInputStream("E:\\\\PRog\\\\LoginData.xls");
		@SuppressWarnings("resource")
		Workbook wBook=new HSSFWorkbook(excelFile);
		Sheet sheet=wBook.getSheetAt(0);

		Iterator<Row> rowIterator=sheet.iterator();
		while(rowIterator.hasNext()) {
			Row row=rowIterator.next();
			Iterator<Cell> columnIterator=row.iterator();
			int i=2;
			while(columnIterator.hasNext()) {
				if (i%2==0) {
					userNameList.add(columnIterator.next().getStringCellValue());
				}else {
					passwordList.add(columnIterator.next().getStringCellValue());
				}
				i++;
			}
		}
	}


	public void loginCredentials(String id,String pass) {

		System.setProperty("webdriver.chrome.driver", "E:\\PRog\\chromedriver_win32\\chromedriver.exe");
		WebDriver driver=new ChromeDriver();

		driver.get("https://demo.openmrs.org/openmrs/referenceapplication/login.page");

		WebElement uName = driver.findElement(By.id("username"));
		uName.sendKeys(id);

		WebElement pWord=driver.findElement(By.id("password"));
		pWord.sendKeys(pass);

		WebElement selectOpt=driver.findElement(By.id("Registration Desk"));
		selectOpt.click();

		WebElement loginButton=driver.findElement(By.id("loginButton"));
		loginButton.click();

		driver.quit();

	}
	
	public void executeTime() {
		for (int i = 0; i < userNameList.size(); i++) {
			loginCredentials(userNameList.get(i),passwordList.get(i));
		}
	}
	
	public static void main(String[] args) throws IOException {

		DataDrivenUsingPOI usingPOI=new DataDrivenUsingPOI();
		usingPOI.readExcel();
		System.out.println(userNameList);
		System.out.println(passwordList);
		usingPOI.executeTime();
	}

}
