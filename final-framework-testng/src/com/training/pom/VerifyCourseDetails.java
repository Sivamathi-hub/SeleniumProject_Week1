package com.training.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class VerifyCourseDetails {
	private WebDriver driver; 
	
	public VerifyCourseDetails(WebDriver driver) {
		this.driver = driver; 
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//*[@title='My courses']")
	private WebElement myCourseLink; 
	
	@FindBy(xpath="//*[@class='col-md-2']/a[1]")
	private WebElement courseItem;
	
	@FindBy(xpath="//*[@class='content']/a[1]")
	private WebElement courseDescription; 
	
	
	public void clickMyCourseLink() {
		this.myCourseLink.click(); 
	}
	
	public void clickCourseItem() {
		this.courseItem.click(); 
	}
	
	public String verifyCourseDescription() {
		String Message;
		Message = this.courseDescription.getText();
		return Message;
	}
}
