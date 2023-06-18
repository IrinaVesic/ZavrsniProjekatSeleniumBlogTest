package cubes.pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import cubes.helper.MyWebDriver;

public class AddNewPostPage {

	private String url;
	private WebDriver driver;
	
	private WebElement chooseCategoryWebelement;
	private WebElement titleWebElement;
	private WebElement descriptionWebElement;
	private WebElement importantNoOptionWebElement;
	private WebElement importantYesoptionWebElement;
	private WebElement chooseFileButtonWebElement;
	private WebElement saveButton;
	private WebElement cancelButton;
	
	public AddNewPostPage (WebDriver driver) {
		
		this.url = "https://testblog.kurs-qa.cubes.edu.rs/admin/posts/add";
		this.driver = driver;	
		
		this.chooseCategoryWebelement = driver.findElement(By.xpath("//select[@name='post_category_id']"));
		this.titleWebElement = driver.findElement(By.xpath("//input[@name='title']"));
		this.descriptionWebElement = driver.findElement(By.xpath("//textarea[@name='description']"));
		this.importantNoOptionWebElement = driver.findElement(By.xpath("//label[.='No']"));
		this.importantYesoptionWebElement = driver.findElement(By.xpath("//label[.='Yes']"));
		this.chooseFileButtonWebElement = driver.findElement(By.xpath("//input[@name='photo']"));	
		this.saveButton = driver.findElement(By.xpath("//button[@type='submit']"));
		this.cancelButton = driver.findElement(By.xpath("//a[contains(text(),'Cancel')]"));
	}
	
	public void openPage() {
		driver.get(url);
	}
	
	public boolean isOnPage() {
		return driver.getCurrentUrl().equalsIgnoreCase(url);
	}
	
	public void selectCategory(String categoryName) {
		Select selectCategory = new Select(chooseCategoryWebelement);
		selectCategory.selectByVisibleText(categoryName);
	}
	
	public String getSelectedCategoryName() {
		Select select = new Select(chooseCategoryWebelement);
		return select.getFirstSelectedOption().getText();
		
	}
	
	public void insertTitleName(String titleName) {
		titleWebElement.clear();
		titleWebElement.sendKeys(titleName);
	}
	
	public boolean errorTitleMessage(String errorMessage) {
		WebElement errorTitleWE = driver.findElement(By.id("title-error"));
		return errorTitleWE.getText().equalsIgnoreCase(errorMessage);
	}
	
	public void insertDescription(String description) {
		descriptionWebElement.clear();
		descriptionWebElement.sendKeys(description);
		
	}
	
	public boolean errorDescriptionMessage(String errorMessage) {
		WebElement errorDescriptionWE = driver.findElement(By.id("description-error"));
		return errorDescriptionWE.getText().equalsIgnoreCase(errorMessage);
	}
	
	public void selectImportantNo() {
		
		importantNoOptionWebElement.click();
		
	}
	
	public boolean importantNoOptionIsSelected() {
		WebElement noOptionSelected = driver.findElement(By.id("set-as-unimportant"));
		return noOptionSelected.isSelected();
	}
	
	public void selectImportantYes() {
		
		importantYesoptionWebElement.click();
	}
	
	public boolean importantYesOptionIsSelected() {
		WebElement yesOptionSelected = driver.findElement(By.id("set-as-important"));
		return yesOptionSelected.isSelected();
	}
	
	public void selectTag(String tagName) {
		WebElement tagWebElement = driver.findElement(By.xpath("//label[text()='"+tagName+"']//ancestor::div/input[1]"));
		tagWebElement.click();
	}
	
	public boolean tagIsSelected(String tagName) {
		WebElement tagWebElement = driver.findElement(By.xpath("//label[text()='"+tagName+"']//ancestor::div/input[1]"));
		return tagWebElement.isSelected();
	}
	
	public boolean errorTagMessage(String errorMessage) {
		WebElement errorTagWE = driver.findElement(By.id("tag_id[]-error"));
		return errorTagWE.getText().equalsIgnoreCase(errorMessage);
	}
	
	public void uploadVideoFile() {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", chooseFileButtonWebElement);
	chooseFileButtonWebElement.sendKeys("/Users/iri/Desktop/photos /video.mp4");
	}
	
	public String getNameInChoosePhotoField() {
	return chooseFileButtonWebElement.getAttribute("value");
	}
	
	public boolean errorPhotoMessage(String errorMessage) {
		WebElement invalidFeedback = driver.findElement(By.xpath("//div[@class='invalid-feedback']/div[1]"));
		return invalidFeedback.getText().equalsIgnoreCase(errorMessage);
	}
	
	public void scrollToChoosePhoto() {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//label[text()='Choose New Photo']")));
	}
	
	public void uploadLargePhotoFile() {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", chooseFileButtonWebElement);
	chooseFileButtonWebElement.sendKeys("/Users/iri/Desktop/photos /largePhoto.jpg");
	}
	
	public void uploadGifPhotoFile() {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", chooseFileButtonWebElement);
	chooseFileButtonWebElement.sendKeys("/Users/iri/Desktop/photos /gif.webpq");
	}
	
	public void uploadImagePngFile() {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", chooseFileButtonWebElement);
	chooseFileButtonWebElement.sendKeys("/Users/iri/Desktop/photos /image.png");
	}
	
	public void uploadValidPhotoFile() {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", chooseFileButtonWebElement);
	chooseFileButtonWebElement.sendKeys("/Users/iri/Desktop/photos /validPhoto.jpg");
	}
	
	public void enterContent(String content) {
		
		driver.switchTo().frame(0);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.tagName("p")));
		
		WebElement contentWebElement = driver.findElement(By.tagName("p"));
		
		contentWebElement.clear();
		
		contentWebElement.sendKeys(content);
		
		driver.switchTo().defaultContent();
	}
	
	public String getEnteredTextContentField() {
		driver.switchTo().frame(0);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.tagName("p")));	
		WebElement contentWebElement = driver.findElement(By.tagName("p"));
		return contentWebElement.getText();		
	}

	public boolean errorContentMessage(String errorMessage) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//div[text()='The content field is required.']")));
		WebElement errorContentWE = driver.findElement(By.xpath("//div[text()='The content field is required.']"));
		return errorContentWE.getText().equalsIgnoreCase(errorMessage);
	}
	
	public void clickOnSave() {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", saveButton);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		saveButton.click();
	}
	
	public void clickOnCancel() {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cancelButton);
	try {
		Thread.sleep(1000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		cancelButton.click();
	}	
	
}
