package cubes.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
	private String url;
	private WebDriver driver;
	private WebElement emailWebElement;
	private WebElement passwordWebElement;
	private WebElement loginButtonWebElement;
	
	public LoginPage(WebDriver driver) {
		this.url = "http://testblog.kurs-qa.cubes.edu.rs/login";
		this.driver = driver;
		
		this.driver.get(url);
		this.driver.manage().window().maximize();
		
		this.emailWebElement = this.driver.findElement(By.name("email"));
		this.passwordWebElement = this.driver.findElement(By.name("password"));
		this.loginButtonWebElement = this.driver.findElement(By.xpath("//button[@type='submit']"));
	}
	public void insertEmail(String email) {
		emailWebElement.clear();
		emailWebElement.sendKeys(email);
	}
	
	public void insertPassword(String password) {
		passwordWebElement.clear();
		passwordWebElement.sendKeys(password);
	}
	
	public void clickOnLogin() {
		loginButtonWebElement.click();
	}
	public void login(String email, String password) {
		insertEmail(email);
		insertPassword(password);
		clickOnLogin();
	}
	public void loginSuccess() {
		login("kursqa@cubes.edu.rs", "cubesqa");
	}
	
}
