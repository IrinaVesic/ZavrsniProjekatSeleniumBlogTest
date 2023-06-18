package cubes.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.AWTException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;

import cubes.helper.MyWebDriver;
import cubes.pages.AddNewPostPage;
import cubes.pages.LoginPage;
import cubes.pages.PostListPage;

class AddNewPostTest {
	
	private static WebDriver driver;
	private static String tagName = "Tag name Irina";
	private static String validTitleName = "Inspire/Plan/Discover/Experience";
	private static String shortTitleName = "Alaska";
	private static String longTitleName = "Blablablablablablablablablablabalblabalbalbalbalblabalbalblablabalbalbalbalbalblablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablab";
	private static String validDescription = "Ovo je post za zavrsni projekat, blablablablabalba.";
	private static String shortDescription = "A book to keep a reader up nights.";
	private static String longDescription = "Blablablablablablablablablablablablablablabalbalablbalbalbalbalbalbalbalbalblabalbalbalbalbalbalblabalblablablabalbalbalblablabalblablablabalblablabalbalbalblablabalbalbalbalbalblablabalblablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablabalblablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablabla";
	private static String contentText = "Once you know what to look for, there’s no mistaking a wolf for a dog.";
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		driver = MyWebDriver.getDriver("chrome");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginSuccess();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	driver.close();
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	//Category Selection from Dropdown List
	@Test
	public void test01() {
		
		PostListPage postListpage = new PostListPage(driver);
		
		postListpage.clickOnAddNewPostButton();
		
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectCategory("SomePostCategory");
		
		assertTrue("Category name is not selected", addNewPostPage.getSelectedCategoryName().equalsIgnoreCase("SomePostCategory"));
		
		addNewPostPage.selectCategory("Irina");
		
		assertTrue("Category name is not selected", addNewPostPage.getSelectedCategoryName().equalsIgnoreCase("Irina"));
	}
	
	
	//Selection and Deselection of "Important" Checkbox
	@Test
	public void test02() {
		
		PostListPage postListpage = new PostListPage(driver);
		
		postListpage.clickOnAddNewPostButton();
		
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		//No option is selected by default
		assertTrue("No option is not selected by default", addNewPostPage.importantNoOptionIsSelected());
		assertFalse("Yes option is selected too", addNewPostPage.importantYesOptionIsSelected());
		
		addNewPostPage.selectImportantYes();
		//when yes options is selected no option is deselected
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertFalse("No option is selected too", addNewPostPage.importantNoOptionIsSelected());
		
		addNewPostPage.selectImportantNo();
		//when no option is selected yes option is deselected
		assertTrue("No option is not selected.", addNewPostPage.importantNoOptionIsSelected());
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
	}
	
	//Tag Field Select and deselect
	@Test
	public void test03() {
		
		PostListPage postListpage = new PostListPage(driver);
		
		postListpage.clickOnAddNewPostButton();
		
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		//checking can tags be selected
		addNewPostPage.selectTag("Tag name Irina");
		assertTrue("Tag is not selected", addNewPostPage.tagIsSelected("Tag name Irina"));
		addNewPostPage.selectTag("eos");
		assertTrue("Tag is not selected", addNewPostPage.tagIsSelected("eos"));
		
		//can tags be deselected
		addNewPostPage.selectTag("Tag name Irina");
		assertFalse("Tag is selected", addNewPostPage.tagIsSelected("Tag name Irina"));
		addNewPostPage.selectTag("eos");
		assertFalse("Tag is selected", addNewPostPage.tagIsSelected("eos"));
		
	}
	
	//“Choose File” button functionality Verification of Error Message for invalid photo files
	@Test
	public void test04() throws AWTException, InterruptedException {
		PostListPage postListpage = new PostListPage(driver);
		
		postListpage.clickOnAddNewPostButton();
		
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		
		addNewPostPage.uploadVideoFile();
		assertTrue(addNewPostPage.getNameInChoosePhotoField().contains("video.mp4"));
		//invalid - feedback field doesn't exist!!!
		assertTrue("Error message is not displayed!", addNewPostPage.errorPhotoMessage("The photo must be an image."));
			
		addNewPostPage.uploadGifPhotoFile();
		assertTrue(addNewPostPage.getNameInChoosePhotoField().contains("gif.webp"));
		assertTrue("Error message is not displayed!", addNewPostPage.errorPhotoMessage("The photo type is not supported."));
		
		addNewPostPage.uploadImagePngFile();
		assertTrue(addNewPostPage.getNameInChoosePhotoField().contains("image.png"));
		assertTrue("Error message is not displayed!", addNewPostPage.errorPhotoMessage("The photo type is not supported."));
		
		addNewPostPage.uploadLargePhotoFile();
		assertTrue(addNewPostPage.getNameInChoosePhotoField().contains("largePhoto.jpg"));
		assertTrue("Error message is not displayed!", addNewPostPage.errorPhotoMessage("The photo size is not acceptable."));
		
	}
	
	//Verify that valid photo file can be uploaded in the “Choose File” field
	@Test
	public void test05() {
		
		PostListPage postListpage = new PostListPage(driver);
		
		postListpage.clickOnAddNewPostButton();
		
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		
		addNewPostPage.uploadValidPhotoFile();
		
		assertTrue(addNewPostPage.getNameInChoosePhotoField().contains("validPhoto.jpg"));	
	}

	//Enter text in the “Content” filed
	@Test
	public void test06() {
		PostListPage postListpage = new PostListPage(driver);
		
		postListpage.clickOnAddNewPostButton();
		
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		
		addNewPostPage.enterContent(contentText);
		//ne zname da uradim assert za unesen tekst u content field
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		assertTrue("!", addNewPostPage.getEnteredTextContentField().equalsIgnoreCase(contentText));
	}
	
	//Add new post with all fields empty click on “Cancel
	@Test
	public void test07() {
		
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.clickOnCancel();
		
		assertTrue("User is not redirected to the post list page!", postListpage.isOnPage());
	}
	
	//Add new post with all fields empty click on “Save"
	@Test
	public void test08() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("This field is required."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("This field is required."));
		assertTrue("Error tag message is misssing!", addNewPostPage.errorTagMessage("This field is required."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));		
	}
	
	//Add new post with selected “Yes” option in Important field, and other fields empty
	@Test
	public void test09() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("This field is required."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("This field is required."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error tag message is misssing!", addNewPostPage.errorTagMessage("This field is required."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with selected one Tag, and other fields empty
	@Test
	public void test10() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectTag(tagName);
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("This field is required."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("This field is required."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post upload large photo, leave other fields empty 
	@Test
	public void test11() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.uploadLargePhotoFile();
		addNewPostPage.clickOnSave();
		
		 
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("This field is required."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("This field is required."));
		assertTrue("Error tag message is misssing!", addNewPostPage.errorTagMessage("This field is required."));
		//invalid feedback div doesn't exist
		assertTrue("Error photo message is missing!", addNewPostPage.errorPhotoMessage("The photo size is not acceptable."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post upload valid photo, leave other fields empty
	@Test
	public void test12() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.uploadValidPhotoFile();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("This field is required."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("This field is required."));
		assertTrue("Error tag message is misssing!", addNewPostPage.errorTagMessage("This field is required."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post insert text in the Content field, leave other fields empty
	@Test
	public void test13() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.enterContent("Once you know what to look for, there’s no mistaking a wolf for a dog.");
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("This field is required."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("This field is required."));
		assertTrue("Error tag message is misssing!", addNewPostPage.errorTagMessage("This field is required."));
	}
	
	//Add new post  mark post as important, add  one Tag, leave other fields empty
	@Test
	public void test14() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("This field is required."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("This field is required."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));	
	}
	
	//Add new post  mark post as important, add  one Tag, and upload invalid photo, leave other fields empty
	@Test
	public void test15() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.uploadLargePhotoFile();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("This field is required."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("This field is required."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error photo message is missing!", addNewPostPage.errorPhotoMessage("The photo size is not acceptable."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post  mark post as important, add  one Tag, upload valid photo, leave other field empty
	@Test
	public void test16() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.uploadValidPhotoFile();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("This field is required."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("This field is required."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post  mark post as important, add  one Tag, upload valid photo, insert text in the Content
	@Test
	public void test17() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.uploadValidPhotoFile();
		addNewPostPage.enterContent(contentText);
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("This field is required."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("This field is required."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
	}
	
	//Add new post with empty title field and description less than 50 characters, leave other fields empty
	@Test
	public void test18() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.insertDescription(shortDescription);
		addNewPostPage.clickOnSave();
		//have to click twice on save button!!!
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("This field is required."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter at least 50 characters."));
		assertTrue("Error tag message is misssing!", addNewPostPage.errorTagMessage("This field is required."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
		
	}
	
	//Add new post with empty title field and description less than 50 characters, mark post as important, leave other fields empty
	@Test
	public void test19() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.insertDescription(shortDescription);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
				
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("This field is required."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter at least 50 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error tag message is misssing!", addNewPostPage.errorTagMessage("This field is required."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with empty title field and description less than 50 characters, mark post as important, add  one Tag, leave other fields empty
	@Test
	public void test20() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertDescription(shortDescription);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
				
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("This field is required."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter at least 50 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with empty title field and description less than 50 characters, mark post as important, add  one Tag, and upload invalid photo, leave other fields empty
	@Test
	public void test21() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.uploadLargePhotoFile();
		addNewPostPage.insertDescription(shortDescription);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
				
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("This field is required."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter at least 50 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error photo message is missing!", addNewPostPage.errorPhotoMessage("The photo size is not acceptable."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
		
	}
	
	//Add new post with empty title field and description less than 50 characters, selected “Yes” option in Important field, select one Tag, and upload valid photo, leave other fields empty
	@Test
	public void test22() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.uploadValidPhotoFile();
		addNewPostPage.insertDescription(shortDescription);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
				
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("This field is required."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter at least 50 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with empty title field and description less than 50 characters, mark post as important, add  one Tag, and upload valid photo, insert text in the Content
	@Test
	public void test23() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.uploadValidPhotoFile();
		addNewPostPage.insertDescription(shortDescription);
		addNewPostPage.enterContent(contentText);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
				
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("This field is required."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter at least 50 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
	}
	
	//Add new post with empty title field, insert description more than 500 characters, leave other field empty
	@Test
	public void test24() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.insertDescription(longDescription);
		addNewPostPage.clickOnSave();
		//have to click twice on save button!!!
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("This field is required."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter no more than 500 characters."));
		assertTrue("Error tag message is misssing!", addNewPostPage.errorTagMessage("This field is required."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with empty title field, insert description more than 500 characters, mark post as important, and other fields empty
	@Test
	public void test25() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.insertDescription(longDescription);
		addNewPostPage.clickOnSave();
		//have to click twice on save button!!!
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("This field is required."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter no more than 500 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error tag message is misssing!", addNewPostPage.errorTagMessage("This field is required."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with empty title field, insert description more than 500 characters, mark post as important, add  one Tag, leave other fields empty
	@Test
	public void test26() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertDescription(longDescription);
		addNewPostPage.clickOnSave();
		//have to click twice on save button!!!
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("This field is required."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter no more than 500 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with empty title field, insert description more than 500 characters, mark post as important, add  one Tag, upload invalid photo, leave other fields empty
	@Test
	public void test27() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertDescription(longDescription);
		addNewPostPage.uploadLargePhotoFile();
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("This field is required."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter no more than 500 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error photo message is missing!", addNewPostPage.errorPhotoMessage("The photo size is not acceptable."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with empty title field, insert description more than 500 characters, mark post as important, add  one Tag, upload valid photo, leave other fields empty
	@Test
	public void test28() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertDescription(longDescription);
		addNewPostPage.uploadValidPhotoFile();
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("This field is required."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter no more than 500 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with empty title field, insert description more than 500 characters, mark post as important, add  one Tag, upload valid photo, insert text in the Content
	@Test
	public void test29() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertDescription(longDescription);
		addNewPostPage.uploadValidPhotoFile();
		addNewPostPage.enterContent(contentText);
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("This field is required."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter no more than 500 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
	}
	
	//Add new post with empty title field, insert valid description, leave other fields empty
	@Test
	public void test30() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.insertDescription(validDescription);
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("This field is required."));
		assertTrue("Error tag message is misssing!", addNewPostPage.errorTagMessage("This field is required."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with empty title field, insert valid description, mark post as important, and other fields empty
	@Test
	public void test31() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.insertDescription(validDescription);
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("This field is required."));
		assertTrue("Error tag message is misssing!", addNewPostPage.errorTagMessage("This field is required."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with empty title field, insert valid description, mark post as important, add  one Tag, leave other fields empty
	@Test
	public void test32() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertDescription(validDescription);
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("This field is required."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with empty title field, insert valid description, mark post as important, add  one Tag, upload invalid photo, leave other fields empty
	@Test
	public void test33() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertDescription(validDescription);
		addNewPostPage.uploadLargePhotoFile();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("This field is required."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error photo message is missing!", addNewPostPage.errorPhotoMessage("The photo size is not acceptable."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with empty title field, insert valid description, mark post as important, add  one Tag, upload valid photo, leave other fields empty
	@Test
	public void test34() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertDescription(validDescription);
		addNewPostPage.uploadValidPhotoFile();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("This field is required."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with empty title field, insert valid description, mark post as important, add  one Tag, upload valid photo, insert text in the Content
	@Test
	public void test35() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertDescription(validDescription);
		addNewPostPage.uploadValidPhotoFile();
		addNewPostPage.enterContent(contentText);
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("This field is required."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
	}
	
	//Add new post with a title name less than the allowed character length, leave other fields empty
	@Test
	public void test36() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.insertTitleName(shortTitleName);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter at least 20 characters."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("This field is required."));
		assertTrue("Error tag message is misssing!", addNewPostPage.errorTagMessage("This field is required."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a title name less than the allowed character length, mark post as important, leave other fields empty 
	@Test
	public void test37() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.insertTitleName(shortTitleName);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter at least 20 characters."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("This field is required."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error tag message is misssing!", addNewPostPage.errorTagMessage("This field is required."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a title name less than the allowed character length, description empty, mark post as important, select one Tag, leave other fields empty 
	@Test
	public void test38() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(shortTitleName);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter at least 20 characters."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("This field is required."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a title name less than the allowed character length, description empty, mark post as important, select one Tag, upload invalid photo, leave other fields empty 
	@Test
	public void test39() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(shortTitleName);
		addNewPostPage.uploadLargePhotoFile();
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter at least 20 characters."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("This field is required."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error photo message is missing!", addNewPostPage.errorPhotoMessage("The photo size is not acceptable."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a title name less than the allowed character length, description empty, mark post as important, select one Tag, upload valid photo, leave other fields empty 
	@Test
	public void test40() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(shortTitleName);
		addNewPostPage.uploadValidPhotoFile();
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter at least 20 characters."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("This field is required."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a title name less than the allowed character length, description empty, mark post as important, select one Tag, upload valid photo, insert text in the Content
	@Test
	public void test41() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(shortTitleName);
		addNewPostPage.uploadValidPhotoFile();
		addNewPostPage.enterContent(contentText);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter at least 20 characters."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("This field is required."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
	}
	
	//Add new post with a title name and description less than the allowed character length, leave other fields empty
	@Test
	public void test42() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.insertTitleName(shortTitleName);
		addNewPostPage.insertDescription(shortDescription);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter at least 20 characters."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter at least 50 characters."));
		assertTrue("Error tag message is misssing!", addNewPostPage.errorTagMessage("This field is required."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a title name and description less than the allowed character length, mark post as important, leave other fields empty 
	@Test
	public void test43() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.insertTitleName(shortTitleName);
		addNewPostPage.insertDescription(shortDescription);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter at least 20 characters."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter at least 50 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error tag message is misssing!", addNewPostPage.errorTagMessage("This field is required."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a title name and description less than the allowed character length, mark post as important, add one Tag, leave other fields empty
	@Test
	public void test44() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(shortTitleName);
		addNewPostPage.insertDescription(shortDescription);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter at least 20 characters."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter at least 50 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a title name and description less than the allowed character length, mark post as important, add one Tag, upload invalid photo, leave other fields empty 	
	@Test
	public void test45() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(shortTitleName);
		addNewPostPage.insertDescription(shortDescription);
		addNewPostPage.uploadLargePhotoFile();
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter at least 20 characters."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter at least 50 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error photo message is missing!", addNewPostPage.errorPhotoMessage("The photo size is not acceptable."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a title name and description less than the allowed character length, mark post as important, add one Tag, upload valid photo, leave other fields empty
	@Test
	public void test46() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(shortTitleName);
		addNewPostPage.insertDescription(shortDescription);
		addNewPostPage.uploadValidPhotoFile();
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter at least 20 characters."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter at least 50 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a title name and description less than the allowed character length, mark post as important, add one Tag, upload valid photo, insert text in the Content
	@Test
	public void test47() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(shortTitleName);
		addNewPostPage.insertDescription(shortDescription);
		addNewPostPage.uploadValidPhotoFile();
		addNewPostPage.enterContent(contentText);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter at least 20 characters."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter at least 50 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
	}
	
	//Add new post with a title name less and description longer than the allowed character length, leave other fields empty
	@Test
	public void test48() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.insertTitleName(shortTitleName);
		addNewPostPage.insertDescription(longDescription);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter at least 20 characters."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter no more than 500 characters."));
		assertTrue("Error tag message is misssing!", addNewPostPage.errorTagMessage("This field is required."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a title name less, and description longer than the allowed character length, mark post as important, leave other fields empty 
	@Test
	public void test49() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.insertTitleName(shortTitleName);
		addNewPostPage.insertDescription(longDescription);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter at least 20 characters."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter no more than 500 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error tag message is misssing!", addNewPostPage.errorTagMessage("This field is required."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a title name less, and description longer than the allowed character length, mark post as important, select one Tag, leave other fields empty 
	@Test
	public void test50() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(shortTitleName);
		addNewPostPage.insertDescription(longDescription);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter at least 20 characters."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter no more than 500 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a title name less, and description longer than the allowed character length,  mark post as important, select one Tag, upload invalid photo, leave other fields empty
	@Test
	public void test51() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(shortTitleName);
		addNewPostPage.insertDescription(longDescription);
		addNewPostPage.uploadLargePhotoFile();
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter at least 20 characters."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter no more than 500 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error photo message is missing!", addNewPostPage.errorPhotoMessage("The photo size is not acceptable."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a title name less, and description longer than the allowed character length, mark post as important, select one Tag, upload valid photo, leave other fields empty 
	@Test
	public void test52() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(shortTitleName);
		addNewPostPage.insertDescription(longDescription);
		addNewPostPage.uploadValidPhotoFile();
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter at least 20 characters."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter no more than 500 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a title name less, and description longer than the allowed character length,  mark post as important, select one Tag, upload valid photo, insert text in the Content
	@Test
	public void test53() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(shortTitleName);
		addNewPostPage.insertDescription(longDescription);
		addNewPostPage.uploadValidPhotoFile();
		addNewPostPage.enterContent(contentText);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter at least 20 characters."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter no more than 500 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
	}
	
	//Add new post with a title name less than the allowed character length, and valid description, leave other fields empty
	@Test
	public void test54() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.insertTitleName(shortTitleName);
		addNewPostPage.insertDescription(validDescription);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter at least 20 characters."));
		assertTrue("Error tag message is misssing!", addNewPostPage.errorTagMessage("This field is required."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a title name less than the allowed character length, and valid description, mark post as important, leave other fields empty 
	@Test
	public void test55() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.insertTitleName(shortTitleName);
		addNewPostPage.insertDescription(validDescription);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter at least 20 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error tag message is misssing!", addNewPostPage.errorTagMessage("This field is required."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with  a title name less than the allowed character length, and valid description, mark post as important, select one Tag, leave other fields empty 
	@Test
	public void test56() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(shortTitleName);
		addNewPostPage.insertDescription(validDescription);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter at least 20 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with  a title name less than the allowed character length, and valid description, mark post as important, select one Tag, upload invalid photo, leave other fields empty 
	@Test
	public void test57() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(shortTitleName);
		addNewPostPage.insertDescription(validDescription);
		addNewPostPage.uploadLargePhotoFile();
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter at least 20 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error photo message is missing!", addNewPostPage.errorPhotoMessage("The photo size is not acceptable."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with  a title name less than the allowed character length, and valid description, mark post as important, select one Tag, upload valid photo, leave other fields empty
	@Test
	public void test58() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(shortTitleName);
		addNewPostPage.insertDescription(validDescription);
		addNewPostPage.uploadValidPhotoFile();
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter at least 20 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with  a title name less than the allowed character length, and valid description, mark post as important, select one Tag, upload valid photo, insert text in the Content
	@Test
	public void test59() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(shortTitleName);
		addNewPostPage.insertDescription(validDescription);
		addNewPostPage.uploadValidPhotoFile();
		addNewPostPage.enterContent(contentText);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter at least 20 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
	}
	
	//Add new post with a title name longer than the allowed character length, leave other fields empty
	@Test
	public void test60() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.insertTitleName(longTitleName);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter no more than 255 characters."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("This field is required."));
		assertTrue("Error tag message is misssing!", addNewPostPage.errorTagMessage("This field is required."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a title name longer than the allowed character length, description empty, mark post as important, leave other fields empty 
	@Test
	public void test61() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.insertTitleName(longTitleName);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter no more than 255 characters."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("This field is required."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error tag message is misssing!", addNewPostPage.errorTagMessage("This field is required."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a title name longer than the allowed character length, description empty, mark post as important, add one Tag, leave other fields empty 
	@Test
	public void test62() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(longTitleName);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter no more than 255 characters."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("This field is required."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a title name longer than the allowed character length, description empty, mark post as important, add one Tag, upload invalid photo, leave other fields empty 
	@Test
	public void test63() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(longTitleName);
		addNewPostPage.uploadLargePhotoFile();
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter no more than 255 characters."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("This field is required."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error photo message is missing!", addNewPostPage.errorPhotoMessage("The photo size is not acceptable."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a title name longer than the allowed character length, description empty, mark post as important, add one Tag, upload valid photo, leave other fields empty 
	@Test
	public void test64() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(longTitleName);
		addNewPostPage.uploadValidPhotoFile();
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter no more than 255 characters."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("This field is required."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a title name longer than the allowed character length, description empty, mark post as important, add one Tag, upload valid photo, insert text in the Content
	@Test
	public void test65() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(longTitleName);
		addNewPostPage.uploadValidPhotoFile();
		addNewPostPage.enterContent(contentText);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter no more than 255 characters."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("This field is required."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
	}
	
	//Add new post with a title name longer and description less than the allowed character length, leave other fields empty
	@Test
	public void test66() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.insertTitleName(longTitleName);
		addNewPostPage.insertDescription(shortDescription);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter no more than 255 characters."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter at least 50 characters."));
		assertTrue("Error tag message is misssing!", addNewPostPage.errorTagMessage("This field is required."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a title name longer and description less than the allowed character length, mark post as important, leave other fields empty 
	@Test
	public void test67() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.insertTitleName(longTitleName);
		addNewPostPage.insertDescription(shortDescription);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter no more than 255 characters."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter at least 50 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error tag message is misssing!", addNewPostPage.errorTagMessage("This field is required."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a title name longer and description less than the allowed character length, mark post as important, add one Tag, leave other fields empty
	@Test
	public void test68() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(longTitleName);
		addNewPostPage.insertDescription(shortDescription);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter no more than 255 characters."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter at least 50 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a title name longer and description less than the allowed character length, mark post as important, add one Tag, upload invalid photo, leave other fields empty 
	@Test
	public void test69() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(longTitleName);
		addNewPostPage.insertDescription(shortDescription);
		addNewPostPage.uploadLargePhotoFile();
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter no more than 255 characters."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter at least 50 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error photo message is missing!", addNewPostPage.errorPhotoMessage("The photo size is not acceptable."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a title name longer and description less than the allowed character length, mark post as important, add one Tag, upload valid photo, leave other fields empty 
	@Test
	public void test70() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(longTitleName);
		addNewPostPage.insertDescription(shortDescription);
		addNewPostPage.uploadValidPhotoFile();
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter no more than 255 characters."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter at least 50 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a title name longer and description less than the allowed character length, mark post as important, add one Tag, upload valid photo, insert text in the Content
	@Test
	public void test71() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(longTitleName);
		addNewPostPage.insertDescription(shortDescription);
		addNewPostPage.uploadValidPhotoFile();
		addNewPostPage.enterContent(contentText);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter no more than 255 characters."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter at least 50 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
	}
	
	//Add new post with a title name and description longer than the allowed character length, leave other fields empty
	@Test
	public void test72() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.insertTitleName(longTitleName);
		addNewPostPage.insertDescription(longDescription);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter no more than 255 characters."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter no more than 500 characters."));
		assertTrue("Error tag message is misssing!", addNewPostPage.errorTagMessage("This field is required."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a title name and description longer than the allowed character length, mark post as important, leave other fields empty
	@Test
	public void test73() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.insertTitleName(longTitleName);
		addNewPostPage.insertDescription(longDescription);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter no more than 255 characters."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter no more than 500 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error tag message is misssing!", addNewPostPage.errorTagMessage("This field is required."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a title name and description longer than the allowed character length, mark post as important, add one Tag, leave other fields empty
	@Test
	public void test74() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(longTitleName);
		addNewPostPage.insertDescription(longDescription);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter no more than 255 characters."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter no more than 500 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a title name and description longer than the allowed character length, mark post as important, add one Tag, upload invalid photo, leave other fields empty 
	@Test
	public void test75() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(longTitleName);
		addNewPostPage.insertDescription(longDescription);
		addNewPostPage.uploadLargePhotoFile();
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter no more than 255 characters."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter no more than 500 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error photo message is missing!", addNewPostPage.errorPhotoMessage("The photo size is not acceptable."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a title name and description longer than the allowed character length, mark post as important, add one Tag, upload valid photo, leave other fields empty 
	@Test
	public void test76() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(longTitleName);
		addNewPostPage.insertDescription(longDescription);
		addNewPostPage.uploadValidPhotoFile();
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter no more than 255 characters."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter no more than 500 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a title name and description longer than the allowed character length, mark post as important, add one Tag, upload valid photo, insert text in the Content
	@Test
	public void test77() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(longTitleName);
		addNewPostPage.insertDescription(longDescription);
		addNewPostPage.uploadValidPhotoFile();
		addNewPostPage.enterContent(contentText);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter no more than 255 characters."));
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter no more than 500 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
	}
	
	//Add new post with a title name longer than the allowed character length, and valid description, leave other fields empty
	@Test
	public void test78() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.insertTitleName(longTitleName);
		addNewPostPage.insertDescription(validDescription);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter no more than 255 characters."));
		assertTrue("Error tag message is misssing!", addNewPostPage.errorTagMessage("This field is required."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a title name longer than the allowed character length, and valid description, mark post as important, leave other fields empty 
	@Test
	public void test79() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.insertTitleName(longTitleName);
		addNewPostPage.insertDescription(validDescription);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter no more than 255 characters."));
		assertTrue("Error tag message is misssing!", addNewPostPage.errorTagMessage("This field is required."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a title name longer than the allowed character length, and valid description, mark post as important, add one Tag, leave other fields empty
	@Test
	public void test80() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(longTitleName);
		addNewPostPage.insertDescription(validDescription);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter no more than 255 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a title name longer than the allowed character length, and valid description, mark post as important, add one Tag, upload invalid photo, leave other fields empty 
	@Test
	public void test81() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(longTitleName);
		addNewPostPage.insertDescription(validDescription);
		addNewPostPage.uploadLargePhotoFile();
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter no more than 255 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error photo message is missing!", addNewPostPage.errorPhotoMessage("The photo size is not acceptable."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a title name longer than the allowed character length, and valid description, mark post as important, add one Tag, upload valid photo, leave other fields empty 
	@Test
	public void test82() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(longTitleName);
		addNewPostPage.insertDescription(validDescription);
		addNewPostPage.uploadValidPhotoFile();
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter no more than 255 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a title name longer than the allowed character length, and valid description, mark post as important, add one Tag, upload valid photo, insert text in the Content
	@Test
	public void test83() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(longTitleName);
		addNewPostPage.insertDescription(validDescription);
		addNewPostPage.uploadValidPhotoFile();
		addNewPostPage.enterContent(contentText);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error title message is missing!", addNewPostPage.errorTitleMessage("Please enter no more than 255 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
	}
	
	//Add new post with a valid title name, leave other fields empty
	@Test
	public void test84() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.insertTitleName(validTitleName);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("This field is required."));
		assertTrue("Error tag message is misssing!", addNewPostPage.errorTagMessage("This field is required."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a valid title name, description empty, mark post as important, leave other fields empty 
	@Test
	public void test85() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.insertTitleName(validTitleName);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("This field is required."));
		assertTrue("Error tag message is misssing!", addNewPostPage.errorTagMessage("This field is required."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a  valid title name, description empty, mark post as important, add one Tag, leave other fields empty 
	@Test
	public void test86() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(validTitleName);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("This field is required."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a  valid title name, description empty, mark post as important, add one Tag, upload invalid photo, leave other fields empty
	@Test
	public void test87() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(validTitleName);
		addNewPostPage.uploadLargePhotoFile();
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("This field is required."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error photo message is missing!", addNewPostPage.errorPhotoMessage("The photo size is not acceptable."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a valid title name, description empty, mark post as important, add one Tag, upload valid photo, leave other fields empty
	@Test
	public void test88() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(validTitleName);
		addNewPostPage.uploadValidPhotoFile();
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("This field is required."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a valid title name, description empty, mark post as important, add one Tag, upload valid photo, insert text in the Content
	@Test
	public void test89() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(validTitleName);
		addNewPostPage.uploadValidPhotoFile();
		addNewPostPage.enterContent(contentText);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("This field is required."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
	}
	
	//Add new post with a  valid title name, and description less than the allowed character length, leave other fields empty
	@Test
	public void test90() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.insertTitleName(validTitleName);
		addNewPostPage.insertDescription(shortDescription);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter at least 50 characters."));
		assertTrue("Error tag message is misssing!", addNewPostPage.errorTagMessage("This field is required."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with valid title name, and description less than the allowed character length, mark post as important, leave other fields empty 
	@Test
	public void test91() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.insertTitleName(validTitleName);
		addNewPostPage.insertDescription(shortDescription);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter at least 50 characters."));
		assertTrue("Error tag message is misssing!", addNewPostPage.errorTagMessage("This field is required."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a valid title name, and description less than the allowed character length, mark post as important, add one Tag, leave other fields empty
	@Test
	public void test92() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(validTitleName);
		addNewPostPage.insertDescription(shortDescription);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter at least 50 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a  valid title name, and description less than the allowed character length, mark post as important, add one Tag, upload invalid photo, leave other fields empty 
	@Test
	public void test93() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(validTitleName);
		addNewPostPage.insertDescription(shortDescription);
		addNewPostPage.uploadLargePhotoFile();
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter at least 50 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error photo message is missing!", addNewPostPage.errorPhotoMessage("The photo size is not acceptable."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a valid title name, and description less than the allowed character length, mark post as important, add one Tag, upload valid photo, leave other fields empty 
	@Test
	public void test94() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(validTitleName);
		addNewPostPage.insertDescription(shortDescription);
		addNewPostPage.uploadValidPhotoFile();
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter at least 50 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a valid title name, and description less than the allowed character length, mark post as important, add one Tag, upload valid photo, insert text in the Content
	@Test
	public void test95() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(validTitleName);
		addNewPostPage.insertDescription(shortDescription);
		addNewPostPage.uploadValidPhotoFile();
		addNewPostPage.enterContent(contentText);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter at least 50 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
	}
	
	//Add new post with a valid title name, and description longer than the allowed character length, leave other fields empty
	@Test
	public void test96() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.insertTitleName(validTitleName);
		addNewPostPage.insertDescription(longDescription);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter no more than 500 characters."));
		assertTrue("Error tag message is misssing!", addNewPostPage.errorTagMessage("This field is required."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a valid title name, and description longer than the allowed character length, mark post as important, leave other fields empty
	@Test
	public void test97() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.insertTitleName(validTitleName);
		addNewPostPage.insertDescription(longDescription);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter no more than 500 characters."));
		assertTrue("Error tag message is misssing!", addNewPostPage.errorTagMessage("This field is required."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a valid title name, and description longer than the allowed character length, mark post as important, add one Tag, leave other fields empty
	@Test
	public void test98() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(validTitleName);
		addNewPostPage.insertDescription(longDescription);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter no more than 500 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	//Add new post with a valid title name, and description longer than the allowed character length, mark post as important, add one Tag, upload invalid photo, leave other fields empty
	@Test
	public void test99() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(validTitleName);
		addNewPostPage.insertDescription(longDescription);
		addNewPostPage.uploadLargePhotoFile();
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter no more than 500 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error photo message is missing!", addNewPostPage.errorPhotoMessage("The photo size is not acceptable."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a valid title name, and description longer than the allowed character length, mark post as important, add one Tag, upload valid photo, leave other fields empty
	@Test
	public void test100() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(validTitleName);
		addNewPostPage.insertDescription(longDescription);
		addNewPostPage.uploadValidPhotoFile();
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter no more than 500 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	//Add new post with a valid title name, and description longer than the allowed character length, mark post as important, add one Tag, upload valid photo, insert text in the Content
	@Test
	public void test101() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(validTitleName);
		addNewPostPage.insertDescription(longDescription);
		addNewPostPage.uploadValidPhotoFile();
		addNewPostPage.enterContent(contentText);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error description message is missing!", addNewPostPage.errorDescriptionMessage("Please enter no more than 500 characters."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
	}
	
	//Add new post with a  valid title name, and valid description leave other fields empty
	@Test
	public void test102() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.insertTitleName(validTitleName);
		addNewPostPage.insertDescription(validDescription);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error tag message is misssing!", addNewPostPage.errorTagMessage("This field is required."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a  valid title name, and valid description, mark post as important, leave other fields empty
	@Test
	public void test103() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.insertTitleName(validTitleName);
		addNewPostPage.insertDescription(validDescription);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Error tag message is misssing!", addNewPostPage.errorTagMessage("This field is required."));
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a  valid title name, and valid description, mark post as important, add one Tag, leave other fields empty
	@Test
	public void test104() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(validTitleName);
		addNewPostPage.insertDescription(validDescription);
		addNewPostPage.clickOnSave();
		addNewPostPage.clickOnSave();
		
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));
	}
	
	//Add new post with a  valid title name, and valid description, mark post as important, add one Tag, invalid photo video,leave other fields empty
	@Test
	public void test105() {
		PostListPage postListpage = new PostListPage(driver);	
		postListpage.clickOnAddNewPostButton();
		AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
		assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
		
		addNewPostPage.selectImportantYes();
		addNewPostPage.selectTag(tagName);
		addNewPostPage.insertTitleName(validTitleName);
		addNewPostPage.insertDescription(validDescription);
		addNewPostPage.uploadVideoFile();
		
		try {
		    addNewPostPage.clickOnSave();
		} catch (StaleElementReferenceException e) {
		    addNewPostPage = new AddNewPostPage(driver);
		    addNewPostPage.clickOnSave();
		}
		
		assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
		assertTrue("Error photo message is missing!", addNewPostPage.errorPhotoMessage("The photo must be an image."));
		assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));	
	}
	
	//Add new post with a  valid title name, and valid description, mark post as important, add one Tag, invalid photo gif,leave other fields empty
		@Test
		public void test106() {
			PostListPage postListpage = new PostListPage(driver);	
			postListpage.clickOnAddNewPostButton();
			AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
			assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
			
			addNewPostPage.selectImportantYes();
			addNewPostPage.selectTag(tagName);
			addNewPostPage.insertTitleName(validTitleName);
			addNewPostPage.insertDescription(validDescription);
			addNewPostPage.uploadGifPhotoFile();
			
			try {
			    addNewPostPage.clickOnSave();
			} catch (StaleElementReferenceException e) {
			    addNewPostPage = new AddNewPostPage(driver);
			    addNewPostPage.clickOnSave();
			}
			
			assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
			assertTrue("Error photo message is missing!", addNewPostPage.errorPhotoMessage("The photo type is not supported."));
			assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));	
		}
		
		//Add new post with a  valid title name, and valid description, mark post as important, add one Tag, invalid photo png,leave other fields empty
				@Test
				public void test107() {
					PostListPage postListpage = new PostListPage(driver);	
					postListpage.clickOnAddNewPostButton();
					AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
					assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
					
					addNewPostPage.selectImportantYes();
					addNewPostPage.selectTag(tagName);
					addNewPostPage.insertTitleName(validTitleName);
					addNewPostPage.insertDescription(validDescription);
					addNewPostPage.uploadImagePngFile();
					
					try {
					    addNewPostPage.clickOnSave();
					} catch (StaleElementReferenceException e) {
					    addNewPostPage = new AddNewPostPage(driver);
					    addNewPostPage.clickOnSave();
					}
					
					assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
					assertTrue("Error photo message is missing!", addNewPostPage.errorPhotoMessage("The photo type is not supported."));
					assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));	
				}
				//Add new post with a  valid title name, and valid description, mark post as important, add one Tag, invalid photo large,leave other fields empty
				@Test
				public void test108() {
					PostListPage postListpage = new PostListPage(driver);	
					postListpage.clickOnAddNewPostButton();
					AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
					assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
					
					addNewPostPage.selectImportantYes();
					addNewPostPage.selectTag(tagName);
					addNewPostPage.insertTitleName(validTitleName);
					addNewPostPage.insertDescription(validDescription);
					addNewPostPage.uploadLargePhotoFile();
					
					try {
					    addNewPostPage.clickOnSave();
					} catch (StaleElementReferenceException e) {
					    addNewPostPage = new AddNewPostPage(driver);
					    addNewPostPage.clickOnSave();
					}
					
					assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
					assertTrue("Error photo message is missing!", addNewPostPage.errorPhotoMessage("The photo size is not acceptable."));
					assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));	
				}
	
				//Add new post with a  valid title name, and valid description, mark post as important, add one Tag, upload video, insert text in the Content
				@Test
				public void test109() {
					PostListPage postListpage = new PostListPage(driver);	
					postListpage.clickOnAddNewPostButton();
					AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
					assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
					
					addNewPostPage.selectImportantYes();
					addNewPostPage.selectTag(tagName);
					addNewPostPage.insertTitleName(validTitleName);
					addNewPostPage.insertDescription(validDescription);
					addNewPostPage.uploadVideoFile();
					addNewPostPage.enterContent(contentText);
					
					try {
					    addNewPostPage.clickOnSave();
					} catch (StaleElementReferenceException e) {
					    addNewPostPage = new AddNewPostPage(driver);
					    addNewPostPage.clickOnSave();
					}
					
					assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
					assertTrue("Error photo message is missing!", addNewPostPage.errorPhotoMessage("The photo must be an image."));
					
				}
				
				//Add new post with a  valid title name, and valid description, mark post as important, add one Tag, upload gif, insert text in the Content
				@Test
				public void test110() {
					PostListPage postListpage = new PostListPage(driver);	
					postListpage.clickOnAddNewPostButton();
					AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
					assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
					
					addNewPostPage.selectImportantYes();
					addNewPostPage.selectTag(tagName);
					addNewPostPage.insertTitleName(validTitleName);
					addNewPostPage.insertDescription(validDescription);
					addNewPostPage.uploadGifPhotoFile();
					addNewPostPage.enterContent(contentText);
					
					try {
					    addNewPostPage.clickOnSave();
					} catch (StaleElementReferenceException e) {
					    addNewPostPage = new AddNewPostPage(driver);
					    addNewPostPage.clickOnSave();
					}
					
					assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
					assertTrue("Error photo message is missing!", addNewPostPage.errorPhotoMessage("The photo type is not supported."));
					
				}
				//Add new post with a  valid title name, and valid description, mark post as important, add one Tag, upload png, insert text in the Content
				@Test
				public void test111() {
					PostListPage postListpage = new PostListPage(driver);	
					postListpage.clickOnAddNewPostButton();
					AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
					assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
					
					addNewPostPage.selectImportantYes();
					addNewPostPage.selectTag(tagName);
					addNewPostPage.insertTitleName(validTitleName);
					addNewPostPage.insertDescription(validDescription);
					addNewPostPage.uploadImagePngFile();
					addNewPostPage.enterContent(contentText);
					
					try {
					    addNewPostPage.clickOnSave();
					} catch (StaleElementReferenceException e) {
					    addNewPostPage = new AddNewPostPage(driver);
					    addNewPostPage.clickOnSave();
					}
					
					assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
					assertTrue("Error photo message is missing!", addNewPostPage.errorPhotoMessage("The photo type is not supported."));
					
				}
				
				//Add new post with a  valid title name, and valid description, mark post as important, add one Tag, upload large photo, insert text in the Content
				@Test
				public void test112() {
					PostListPage postListpage = new PostListPage(driver);	
					postListpage.clickOnAddNewPostButton();
					AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
					assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
					
					addNewPostPage.selectImportantYes();
					addNewPostPage.selectTag(tagName);
					addNewPostPage.insertTitleName(validTitleName);
					addNewPostPage.insertDescription(validDescription);
					addNewPostPage.uploadLargePhotoFile();
					addNewPostPage.enterContent(contentText);
					
					try {
					    addNewPostPage.clickOnSave();
					} catch (StaleElementReferenceException e) {
					    addNewPostPage = new AddNewPostPage(driver);
					    addNewPostPage.clickOnSave();
					}
					
					assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
					assertTrue("Error photo message is missing!", addNewPostPage.errorPhotoMessage("The photo size is not acceptable."));
					
				}
				
				//Add new post with a  valid title name, and valid description, mark post as important, add one Tag, upload valid photo, leave content field empty
				@Test
				public void test113() {
					PostListPage postListpage = new PostListPage(driver);	
					postListpage.clickOnAddNewPostButton();
					AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
					assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
					
					addNewPostPage.selectImportantYes();
					addNewPostPage.selectTag(tagName);
					addNewPostPage.insertTitleName(validTitleName);
					addNewPostPage.insertDescription(validDescription);
					addNewPostPage.uploadValidPhotoFile();
					addNewPostPage.clickOnSave();
						
					assertTrue("Yes option is not selected.", addNewPostPage.importantYesOptionIsSelected());
					assertTrue("Error content message is missing!", addNewPostPage.errorContentMessage("The content field is required."));				
				}
				
				//Add new post with all fields valid and click on “Cancel”button 
				@Test
				public void test114() {
					PostListPage postListpage = new PostListPage(driver);	
					postListpage.clickOnAddNewPostButton();
					AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
					assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
					
					addNewPostPage.selectImportantYes();
					addNewPostPage.selectTag(tagName);
					addNewPostPage.insertTitleName(validTitleName);
					addNewPostPage.insertDescription(validDescription);
					addNewPostPage.uploadValidPhotoFile();
					addNewPostPage.enterContent(contentText);
					addNewPostPage.clickOnCancel();
					
					assertTrue("User is not redirected to the posts list page", postListpage.isOnPage());				
				}
				
				//Add new post with all fields valid and click on “Save” button
				@Test
				public void test115() {
					PostListPage postListpage = new PostListPage(driver);	
					postListpage.clickOnAddNewPostButton();
					AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
					assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
					
					addNewPostPage.selectCategory("Irina");
					addNewPostPage.selectImportantYes();
					addNewPostPage.selectTag(tagName);
					addNewPostPage.insertTitleName(validTitleName);
					addNewPostPage.insertDescription(validDescription);
					addNewPostPage.uploadValidPhotoFile();
					addNewPostPage.enterContent(contentText);
					addNewPostPage.clickOnSave();
				
					assertTrue("Post is not created, user is on add post list page!", postListpage.isOnPage());
					
					postListpage.searchPostByTitleName(validTitleName);
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					assertTrue("Title name is not on the list", postListpage.isTitleNameOnPage(validTitleName));
				}
				
				//Add new post Create duplicate post				
				@Test
				public void test116() {
					PostListPage postListpage = new PostListPage(driver);	
					postListpage.clickOnAddNewPostButton();
					AddNewPostPage addNewPostPage = new AddNewPostPage(driver);
					assertTrue("Add new post page didn't open", addNewPostPage.isOnPage());
					
					addNewPostPage.selectCategory("Irina");
					addNewPostPage.selectImportantYes();
					addNewPostPage.selectTag(tagName);
					addNewPostPage.insertTitleName(validTitleName);
					addNewPostPage.insertDescription(validDescription);
					addNewPostPage.uploadValidPhotoFile();
					addNewPostPage.enterContent(contentText);
					addNewPostPage.clickOnSave();
				
					assertTrue("Duplicate post is created!", addNewPostPage.isOnPage());	
				}
			
}
