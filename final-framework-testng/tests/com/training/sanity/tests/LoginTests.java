package com.training.sanity.tests;

import static org.testng.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.training.generics.ScreenShot;
import com.training.pom.LoginPOM;
import com.training.pom.RegisterCourse;
import com.training.pom.VerifyCourseDetails;
import com.training.pom.UserLogout;
import com.training.utility.DriverFactory;
import com.training.utility.DriverNames;

import java.util.List;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginTests {

	private WebDriver driver;
	private String baseUrl;
	private LoginPOM loginPOM;
	private RegisterCourse registerCourse;
	private VerifyCourseDetails verifyCourseDetails;
	private UserLogout userLogout;
	private static Properties properties;
	private ScreenShot screenShot;

	@BeforeSuite
	public static void setUpBeforeClass() throws IOException {
		properties = new Properties();
		FileInputStream inStream = new FileInputStream("./resources/others.properties");
		properties.load(inStream);
	}

	@BeforeTest
	public void setUp() throws Exception {
		driver = DriverFactory.getDriver(DriverNames.CHROME);
		//creating instance for training.pom classess
		loginPOM = new LoginPOM(driver);
		registerCourse = new RegisterCourse(driver);
		verifyCourseDetails = new VerifyCourseDetails(driver);
		userLogout = new UserLogout(driver);
		
		baseUrl = properties.getProperty("baseURL");
		screenShot = new ScreenShot(driver); 
		// open the browser 
		driver.get(baseUrl);
	}
	
	@AfterMethod
	public void tearDown() throws Exception {
		Thread.sleep(1000);
	}
	
	@Test(priority=6)
	public void closeBrowser(){
		driver.quit();
	}
	@Test(priority=1)
	public void validLoginTest() {
		loginPOM.sendUserName("Sivamathi");
		loginPOM.sendPassword("1w2qaxsz");
		loginPOM.clickLoginBtn(); 
		screenShot.captureScreenShot("login");
	}
	
	@Test(priority=2)
	public void registerCourse() throws InterruptedException {
		String actMsg="";
		registerCourse.clickHomePage();
		Thread.sleep(1000);
		registerCourse.clickCourseCatalog();
		Thread.sleep(2000);
		registerCourse.sendCourceName("Selenium");
		registerCourse.clickSearch();
		Thread.sleep(2000);
		registerCourse.clickCourseItem();
		Thread.sleep(2000);
		actMsg=registerCourse.verifyAlertMessage();
		
		if (actMsg.contains("User Sivamathi s (Sivamathi) has been registered to course")){
			System.out.println("Course registered confirmation message displayed, Step Passed");
		}
		else{
			System.out.println("Course registered confirmation message NOT displayed, Step Failed");
		}
		screenShot.captureScreenShot("RegisterCourse");
	}
	
	@Test(priority=3)
	public void verifyCourseDetails() throws InterruptedException {
		String actTxt="";
		String expTxt = "Course description";
		//boolean courseDetails = true;
		verifyCourseDetails.clickMyCourseLink();
		Thread.sleep(1000);
		verifyCourseDetails.clickCourseItem();
		Thread.sleep(2000);
		
		actTxt=verifyCourseDetails.verifyCourseDescription();
		if (actTxt.equalsIgnoreCase(expTxt)){
			System.out.println("User able to see course details, Step Passed");
		}
		else{
			System.out.println("Course details NOT displayed, Step Failed");
		}
		screenShot.captureScreenShot("VerifyCourseDetails");
	}
	
	@Test(priority=4)
	public void verifyUserMenuLinks() throws InterruptedException {
		userLogout.clickUserIcon();
		Thread.sleep(1000);		
		
		//verify displayed links is as expected or not
		String[] expLinks = new String[]{"Inbox","My certificates","Logout"};
		
		List<WebElement> tot = driver.findElements(By.xpath("//*[@class='user-body']/a"));
		String[] ltext = new String[tot.size()];
		for (int i=0;i<tot.size();i++){
			System.out.println("Link Name is:"+tot.get(i).getText());
			ltext[i]=tot.get(i).getAttribute("innerText").trim();
			System.out.println("Inner text Name is:"+ltext[i]);
		}
		
		for (int i=0;i<tot.size();i++){
			if (expLinks[i].equalsIgnoreCase(ltext[i])) {
				System.out.println("UserIcon displayed the menu,Exp:"+expLinks[i] +" Act:"+ltext[i]+", Step Passed");
			}else {
				System.out.println("UserIcon NOT displayed the menu,Exp:"+expLinks[i] +" Act:"+ltext[i]+", Step Failed");
			}
		}
		
		/*if (ltext.equals(expLinks)) {
			System.out.println("UserIcon displayed list of menu as expected, Step Passed");
		}
		else {
			System.out.println("UserIcon NOT displayed list of menu, Step Failed");
		}*/
		
		screenShot.captureScreenShot("verifyUserMenuLinks");
	}
	
	@Test(priority=5)
	public void userLogout() throws InterruptedException {
		String expTitle="e Learning - My education";		
		userLogout.clickUserLogout();
		Thread.sleep(2000);
		String actTitle = driver.getTitle();
		if (actTitle.equalsIgnoreCase(expTitle)) {
			System.out.println("Homepage displayed once user Loggedout, Step Passed");
		}
		else {
			System.out.println("Homepage NOT displayed after logout, Step Failed");
		}			
		screenShot.captureScreenShot("userLogout");
	}
}
