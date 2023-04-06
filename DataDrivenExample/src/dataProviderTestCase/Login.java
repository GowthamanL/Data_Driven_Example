package dataProviderTestCase;

import java.io.FileInputStream;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class Login {

	WebDriver driver;

	String[][] data= null;
	@DataProvider(name="loginDatas")
	public String[][] loginDataProvider() throws BiffException, IOException{
		data=getExcelData();
		return data;
	}

	public String[][] getExcelData() throws IOException, BiffException {
		FileInputStream excelFile=new FileInputStream("E:\\PRog\\LoginData.xls");
		Workbook workbook=Workbook.getWorkbook(excelFile);
		Sheet sheet=workbook.getSheet(0);

		int rowCount= sheet.getRows();
		int columnCount=sheet.getColumns();

/*" -1 " IN ROW COUNT REPRESENTS WE DONT NEED FIRST ROW DATA COZ THOSE ARE HEADERS*/
		String testData[][]=new String[rowCount-1][columnCount];

		for (int i = 1; i <rowCount; i++) {
			for (int j = 0; j <columnCount; j++) {
				testData[i-1][j]=sheet.getCell(j, i).getContents();
			}
		}
		return testData;
	}

	@BeforeTest
	public void beforeTest() {
		System.setProperty("webdriver.chrome.driver", "E:\\PRog\\chromedriver_win32\\chromedriver.exe");
		driver=new ChromeDriver();
	}

	@AfterTest
	public void afterTest() {
		driver.quit();
	}

	@Test(dataProvider="loginDatas")
	public void loginCredentials(String id,String pass) {

		driver.get("https://demo.openmrs.org/openmrs/referenceapplication/login.page");

		WebElement uName = driver.findElement(By.id("username"));
		uName.sendKeys(id);

		WebElement pWord=driver.findElement(By.id("password"));
		pWord.sendKeys(pass);

		WebElement selectOpt=driver.findElement(By.id("Registration Desk"));
		selectOpt.click();

		WebElement loginButton=driver.findElement(By.id("loginButton"));
		loginButton.click();

	}

}
