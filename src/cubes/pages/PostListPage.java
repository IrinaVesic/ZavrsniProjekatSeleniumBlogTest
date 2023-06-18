package cubes.pages;

import static org.junit.Assert.assertTrue;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PostListPage {
	
	private String url;
	private WebDriver driver;
	private WebElement addNewPost;
	
	public PostListPage (WebDriver driver) {
		
		this.url = "https://testblog.kurs-qa.cubes.edu.rs/admin/posts";
		this.driver = driver;
		
		this.driver.get(url);
		this.driver.manage().window().maximize();
		
		this.addNewPost = driver.findElement(By.xpath("//i[@class='fas fa-plus-square']"));	
		
	}
	
	public void openPage() {
		driver.get(url);
	}
	
	public boolean isOnPage() {
		return driver.getCurrentUrl().equalsIgnoreCase(url);
	}
	
	public void clickOnAddNewPostButton() {
		addNewPost.click();
	}
		
	public void searchPostByTitleName(String titleName) {
		WebElement searchByTitleWebElement = driver.findElement(By.xpath("//input[@name='title']"));
		searchByTitleWebElement.clear();
		searchByTitleWebElement.sendKeys(titleName);
	}
	
	public void searchPostByAuthorName(String authorName) {
		WebElement chooseAuthorWE = driver.findElement(By.xpath("//span[@class='select2-selection select2-selection--single']"));
		chooseAuthorWE.click();
		
		WebElement inputWE = driver.findElement(By.xpath("//span[@class='select2-search select2-search--dropdown']//input[@type='search']"));
		inputWE.sendKeys(authorName);
		inputWE.sendKeys(Keys.RETURN);
	}
	
	public void searchPostByCategoryName(String categoryName) {
		WebElement chooseCategoryWE = driver.findElement(By.xpath("//span[@title='--Choose Category --']"));
		chooseCategoryWE.click();
		
		WebElement inputWE = driver.findElement(By.xpath("//span[@class='select2-search select2-search--dropdown']//input[@type='search']"));
		inputWE.sendKeys(categoryName);
		inputWE.sendKeys(Keys.RETURN);
	}
	
	public void searchPostByImportantYes() {
		Select selectImportantWE = new Select(driver.findElement(By.xpath("//select[@name='important']")));
		selectImportantWE.selectByIndex(1);
	}
	
	public void searchPostByImportantNo() {
		Select selectImportantWE = new Select(driver.findElement(By.xpath("//select[@name='important']")));
		selectImportantWE.selectByIndex(2);
	}
	
	public void searchByStatusEnabled() {
		Select statusWE = new Select(driver.findElement(By.xpath("//select[@name='status']")));
		statusWE.selectByIndex(1);
	}
	
	public void searchByStatusDisabled() {
		Select statusWE = new Select(driver.findElement(By.xpath("//select[@name='status']")));
		statusWE.selectByVisibleText("disabled");
	}
	
	public void searchByTagName(String tagName) {
		WebElement inputTagWE = driver.findElement(By.xpath("//input[@class='select2-search__field']"));
		inputTagWE.sendKeys(tagName);
		inputTagWE.sendKeys(Keys.ENTER);
	}
	
	public String isPhotoDisplayed(String titleName) {
		WebElement photo = driver.findElement(By.xpath("//td[text()='"+titleName+"']//ancestor::tr/td[2]/img"));
		return photo.getAttribute("src");
	}
	
	public boolean isTitleNameOnPage(String titleName) {
		WebElement titleWE = driver.findElement(By.xpath("//td[text()='"+titleName+"']"));
		return titleWE.getText().equalsIgnoreCase(titleName);
	}
	
	public boolean verifyImportantYesStatus(String titleName, String expected) {
		WebElement actualImportantWE = driver.findElement(By.xpath("//td[text()='"+titleName+"']//ancestor::tr/td[3]/span[text()='Yes']"));
		return actualImportantWE.getText().equalsIgnoreCase(expected);		
	}
	
	public boolean verifyImportantNoStatus(String titleName, String expected) {
		WebElement actualImportantWE = driver.findElement(By.xpath("//td[text()='"+titleName+"']//ancestor::tr/td[3]/span[text()='No']"));
		return actualImportantWE.getText().equalsIgnoreCase(expected);		
	}
	
	public boolean verifyPostEnabledStatus(String titleName, String expected) {
		WebElement actualImportantWE = driver.findElement(By.xpath("//td[text()='"+titleName+"']//ancestor::tr/td[4]/span[text()='enabled']"));
		return actualImportantWE.getText().equalsIgnoreCase(expected);		
	}
	
	public boolean verifyPostDisabledStatus(String titleName, String expected) {
		WebElement actualImportantWE = driver.findElement(By.xpath("//td[text()='"+titleName+"']//ancestor::tr/td[4]/span[text()='disabled']"));
		return actualImportantWE.getText().equalsIgnoreCase(expected);		
	}
	
	public String getAuthorName(String titleName) {
		WebElement authorWE = driver.findElement(By.xpath("//td[text()='"+titleName+"']//ancestor::tr/td[6]"));
		String actualAuthorNameWE = authorWE.getText();	
		return actualAuthorNameWE;
	}
	
	public String getCategoryName(String titleName) {
		WebElement categoryWE = driver.findElement(By.xpath("//td[text()='"+titleName+"']//ancestor::tr/td[7]"));
		String actualCategoryNameWE = categoryWE.getText();	
		return actualCategoryNameWE;
	}
	
	public String getTagName(String titleName) {
		WebElement tagWE = driver.findElement(By.xpath("//td[text()='"+titleName+"']//ancestor::tr/td[8]"));
		String actuaTagNameWE = tagWE.getText();	
		return actuaTagNameWE;
	}
	
	public void clickOnUnimportantButton(String titleName) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement importantWE = wait.until(ExpectedConditions.elementToBeClickable((By.xpath("//td[text()='"+titleName+"']//ancestor::tr/td[12]/div[2]/button[2]"))));	
		importantWE.click();	
			
		WebElement buttontWE = wait.until(ExpectedConditions.elementToBeClickable((By.xpath("//button[contains(.,'Set as Unimportant')]"))));
		buttontWE.click();			
	}
	
	public void clickOnImportantButton(String titleName) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement importantWE = wait.until(ExpectedConditions.elementToBeClickable((By.xpath("//td[text()='"+titleName+"']//ancestor::tr/td[12]/div[2]/button[2]"))));
		importantWE.click();		
				
		WebElement buttontWE = wait.until(ExpectedConditions.elementToBeClickable((By.xpath("//button[contains(.,'Set as Important')]"))));
		buttontWE.click();			
	}
	public void clickOnDisabledButton(String titleName) {
		WebElement disabledWE = driver.findElement(By.xpath("//td[text()='"+titleName+"']//ancestor::tr/td[12]/div[2]/button[1]"));
		disabledWE.click();	
		try {Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
		WebElement buttontWE = driver.findElement(By.xpath("//button[contains(.,'Disable')]"));
		buttontWE.click();			
	}
	
	public void clickOnEnabledButton(String titleName) {
		WebElement enabledWE = driver.findElement(By.xpath("//td[text()='"+titleName+"']//ancestor::tr/td[12]/div[2]/button[1]"));
		enabledWE.click();		
		try {Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		WebElement buttontWE = driver.findElement(By.xpath("//button[contains(.,'Enable')]"));
		buttontWE.click();			
	}
	
	public void clickOnViewButton(String titleName) {
		WebElement viewButtonWE = driver.findElement(By.xpath("//td[text()='"+titleName+"']//ancestor::tr/td[12]/div[1]/a[1]/i[@class='fas fa-eye']"));
		viewButtonWE.click();
	}
	
	public boolean isOnViewPage(String titleName) {
		List<String> tabs = new ArrayList<>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		return	driver.getTitle().contains(titleName);
		
	}
	
	public void clickOnEditButton(String titleName) {
		WebElement editButtonWE = driver.findElement(By.xpath("//td[text()='"+titleName+"']//ancestor::tr/td[12]/div[1]/a[2]/i[@class='fas fa-edit']"));
		editButtonWE.click();
	}
	
	public String getPostId(String titleName) {
		WebElement idPostWE = driver.findElement(By.xpath("//td[text()='"+titleName+"']//ancestor::tr/td"));
		String id = idPostWE.getText();	
		return id;
	}
	
	public boolean isOnEditPostPage(String id) {
		List<String> tabs = new ArrayList<>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(0));
		String url = driver.getCurrentUrl();	
		return url.startsWith("https://testblog.kurs-qa.cubes.edu.rs/admin/posts/edit/"+id+"");

	}	
	
	public void clickOnDeleteButton(String titleName) {
		WebElement deleteButtonWE = driver.findElement(By.xpath("//td[text()='"+titleName+"']//ancestor::tr/td[12]/div[1]//i[@class='fas fa-trash']"));
		deleteButtonWE.click();
		try {Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		WebElement delButtonWE = driver.findElement(By.xpath("//button[text()='Delete']"));
		delButtonWE.click();
	}
	public boolean isPostDeleted(String titleName) {
		List<WebElement> webElements = driver.findElements(By.xpath("//td[text()='"+titleName+"']"));	
		return webElements.size()>0;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
