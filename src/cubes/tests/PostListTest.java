package cubes.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import cubes.helper.MyWebDriver;
import cubes.pages.EditPostPage;
import cubes.pages.LoginPage;
import cubes.pages.PostListPage;

class PostListTest {

	private static WebDriver driver;
	private static String titleName = "Inspire/Plan/Discover/Experience";
	private static String description = "Ovo je post za zavrsni projekat, blablablablabalba.";
	private static String updatedTitleName = "Wonders of the World";
	private static String updatedDescription = "Friends who used to meet him at the pub are now hiking and skiing every weekend";
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		driver = MyWebDriver.getDriver("chrome");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginSuccess();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	//	driver.close();
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	//Search posts “Search by title”functionality
	@Test
	void test01() {
		PostListPage postListPage = new PostListPage(driver);	
		postListPage.searchPostByTitleName(titleName);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue("Title name is not on the list", postListPage.isTitleNameOnPage(titleName));		
	}
	
	//Search post “Choose Author” functionality
	@Test
	public void test02() {
		PostListPage postListPage = new PostListPage(driver);		
		postListPage.searchPostByAuthorName("Polaznik Kursa");
	}
	
	//Search posts “Choose Category” functionality
	@Test
	public void test03() {
		PostListPage postListPage = new PostListPage(driver);		
		postListPage.searchPostByCategoryName("Irina");
	}
	
	//Search post “Important” “yes” option functionality
	@Test
	public void test04() {
		PostListPage postListPage = new PostListPage(driver);		
		postListPage.searchPostByImportantYes();
	}
	
	//Search posts “Important” “no” option functionality
	@Test
	public void test05() {
		PostListPage postListPage = new PostListPage(driver);		
		postListPage.searchPostByImportantNo();
	}
	
	//Search posts “Status” “enabled” option functionality
	@Test
	public void test06() {
		PostListPage postListPage = new PostListPage(driver);		
		postListPage.searchByStatusEnabled();
	}
	
	//Search posts “Status” “disabled” option functionality
	@Test
	public void test07() {
		PostListPage postListPage = new PostListPage(driver);		
		postListPage.searchByStatusDisabled();;
	}
	
	//Search posts “With Tag” functionality
	@Test
	public void test08() {
		PostListPage postListPage = new PostListPage(driver);	
		postListPage.searchByTagName("Tag name Irina");
	}
	
	//Important button functionality **** test for post which is already set up as important
	@Test
	public void test09() {
		PostListPage postListPage = new PostListPage(driver);
		postListPage.searchPostByTitleName(titleName);		
		postListPage.clickOnUnimportantButton(titleName);
		try {Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue("Post is set as Important", postListPage.verifyImportantNoStatus(titleName, "No"));		
		postListPage.clickOnImportantButton(titleName);	
		try {Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		assertTrue("Post is not set as Important", postListPage.verifyImportantYesStatus(titleName, "Yes"));
	}
	
	//Enable or Disable button functionality
	@Test
	public void test10() {
		PostListPage postListPage = new PostListPage(driver);
		postListPage.searchPostByTitleName(titleName);	
		try {Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		postListPage.clickOnDisabledButton(titleName);
		try {Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue("Post is not disabled!", postListPage.verifyPostDisabledStatus(titleName, "disabled"));
		
		postListPage.clickOnEnabledButton(titleName);
		try {Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue("Post is not enabled", postListPage.verifyPostEnabledStatus(titleName, "enabled"));
	}
	
	//“View” button functionality
	@Test
	public void test11() {
		PostListPage postListPage = new PostListPage(driver);
		postListPage.searchPostByTitleName(titleName);
		try {Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		postListPage.clickOnViewButton(titleName);	
		assertTrue("View page doesn't open!", postListPage.isOnViewPage(titleName));	
	}
	
	//“Edit” button functionality
	@Test
	public void test12() {
		PostListPage postListPage = new PostListPage(driver);
		postListPage.searchPostByTitleName(titleName);
		try {Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		String id = postListPage.getPostId(titleName);;
		postListPage.clickOnEditButton(titleName);	
		
		assertTrue("Edit post page doesn't open!",postListPage.isOnEditPostPage(id) );
	}
	
	//“Edit Post” page values
	@Test
	public void test13() {
		PostListPage postListPage = new PostListPage(driver);
		postListPage.searchPostByTitleName(titleName);
		try {Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		postListPage.clickOnEditButton(titleName);	
		
		EditPostPage editPostPage = new EditPostPage(driver);
		
		assertTrue("Category name is wrong!", editPostPage.getSelectedCategoryName().equalsIgnoreCase("Irina"));
		assertTrue("Title name is not correct!", editPostPage.getTitleName().equalsIgnoreCase(titleName));
		assertTrue("Description is not correct!", editPostPage.getDescription().equalsIgnoreCase(description));
		assertTrue("Post is not marked as important", editPostPage.importantYesOptionIsSelected());
		assertTrue("Tag is not correct!", editPostPage.tagIsSelected("Tag name Irina"));
		assertTrue("Photo is not correct!", editPostPage.isPhotoDisplayed().contains("validPhoto"));
		assertTrue("Content text is not correct!", editPostPage.getEnteredTextContentField().equalsIgnoreCase("Once you know what to look for, there’s no mistaking a wolf for a dog."));
	}
	
	//“Edit Post” page do not change any values click on “Cancel”
	@Test
	public void test14() {
		PostListPage postListPage = new PostListPage(driver);
		postListPage.searchPostByTitleName(titleName);
		try {Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		postListPage.clickOnEditButton(titleName);	
		
		EditPostPage editPostPage = new EditPostPage(driver);
		editPostPage.clickOnCancel();
		
		assertTrue("User is not redirected to the post list page!", postListPage.isOnPage());
		postListPage.searchPostByTitleName(titleName);
		try {Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue("Photo is changed", postListPage.isPhotoDisplayed(titleName).contains("validPhoto"));
		assertTrue("Title name is changed", postListPage.isTitleNameOnPage(titleName));
		assertTrue("Category name is changed", postListPage.getCategoryName(titleName).equalsIgnoreCase("Irina"));
		assertTrue("Tag is changed", postListPage.getTagName(titleName).equalsIgnoreCase("Tag name Irina"));
	}
	
	//“Edit Post” page do not change any values click on “Save”
	@Test
	public void test15() {
		PostListPage postListPage = new PostListPage(driver);
		postListPage.searchPostByTitleName(titleName);
		try {Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		postListPage.clickOnEditButton(titleName);	
		
		EditPostPage editPostPage = new EditPostPage(driver);
		editPostPage.clickOnSave();
		
		assertTrue("User is not redirected to the post list page!", postListPage.isOnPage());
		postListPage.searchPostByTitleName(titleName);
		try {Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue("Photo is changed", postListPage.isPhotoDisplayed(titleName).contains("validPhoto"));
		assertTrue("Title name is changed", postListPage.isTitleNameOnPage(titleName));
		assertTrue("Category name is changed", postListPage.getCategoryName(titleName).equalsIgnoreCase("Irina"));
		assertTrue("Tag is changed", postListPage.getTagName(titleName).equalsIgnoreCase("Tag name Irina"));
	}
	
	//“Edit Post” - Category field
	@Test
	public void test16() {
		PostListPage postListPage = new PostListPage(driver);
		postListPage.searchPostByTitleName(titleName);
		try {Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		postListPage.clickOnEditButton(titleName);	
		
		EditPostPage editPostPage = new EditPostPage(driver);
		editPostPage.selectCategory("-- Choose Category --");;
		editPostPage.clickOnSave();
		
		assertTrue("User is not redirected to the post list page!", postListPage.isOnPage());
		postListPage.searchPostByTitleName(titleName);
		try {Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue("Category name is not changed", postListPage.getCategoryName(titleName).equalsIgnoreCase("UNCATEGORIZED"));
	}
	
	//“Edit Post” - Title field empty 
	@Test
	public void test17() {
		PostListPage postListPage = new PostListPage(driver);
		postListPage.searchPostByTitleName(titleName);
		try {Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		postListPage.clickOnEditButton(titleName);	
		
		EditPostPage editPostPage = new EditPostPage(driver);
		editPostPage.insertTitleName("");
		editPostPage.clickOnSave();
		
		assertTrue("Title error message is missing!", editPostPage.errorTitleMessage("This field is required."));
	}
	
	//“Edit Post” - Title field less than allowed length
	@Test
	public void test18() {
		PostListPage postListPage = new PostListPage(driver);
		postListPage.searchPostByTitleName(titleName);
		try {Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		postListPage.clickOnEditButton(titleName);	
		
		EditPostPage editPostPage = new EditPostPage(driver);
		editPostPage.insertTitleName("Alaska");
		editPostPage.clickOnSave();
		
		assertTrue("Title error message is missing!", editPostPage.errorTitleMessage("Please enter at least 20 characters."));
	}
	
	//“Edit Post” - Title field more than allowed length
	@Test
	public void test19() {
		PostListPage postListPage = new PostListPage(driver);
		postListPage.searchPostByTitleName(titleName);
		try {Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		postListPage.clickOnEditButton(titleName);	
		
		EditPostPage editPostPage = new EditPostPage(driver);
		editPostPage.insertTitleName("Blablablablablablablablablablabalblabalbalbalbalblabalbalblablabalbalbalbalbalblablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablab");
		editPostPage.clickOnSave();
		
		assertTrue("Title error message is missing!", editPostPage.errorTitleMessage("Please enter no more than 255 characters."));
	}
	
	//“Edit Post” - Title field edited
	@Test
	public void test20() {
		PostListPage postListPage = new PostListPage(driver);
		postListPage.searchPostByTitleName(titleName);
		try {Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		postListPage.clickOnEditButton(titleName);	
		
		EditPostPage editPostPage = new EditPostPage(driver);
		editPostPage.insertTitleName("Wonders of the World");
		editPostPage.clickOnSave();
		
		postListPage.searchPostByTitleName("Wonders of the World");
		try {Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		assertTrue("Title name is changed", postListPage.isTitleNameOnPage("Wonders of the World"));
	}
	
	//“Edit Post” - Description field empty
	@Test
	public void test21() {
		PostListPage postListPage = new PostListPage(driver);
		postListPage.searchPostByTitleName("Wonders of the World");
		try {Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		postListPage.clickOnEditButton("Wonders of the World");	
		
		EditPostPage editPostPage = new EditPostPage(driver);
		editPostPage.insertDescription("");
		editPostPage.clickOnSave();
		
		assertTrue("description error message is missing!", editPostPage.errorDescriptionMessage("This field is required."));
	}
	
	//“Edit Post” - Description field less than allowed length
	@Test
	public void test22() {
		PostListPage postListPage = new PostListPage(driver);
		postListPage.searchPostByTitleName("Wonders of the World");
		try {Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		postListPage.clickOnEditButton("Wonders of the World");	
		
		EditPostPage editPostPage = new EditPostPage(driver);
		editPostPage.insertDescription("A book to keep a reader up nights.");
		editPostPage.clickOnSave();
		
		assertTrue("description error message is missing!", editPostPage.errorDescriptionMessage("Please enter at least 50 characters."));
	}
	
	//“Edit Post” - Description field more than allowed length
	@Test
	public void test23() {
		PostListPage postListPage = new PostListPage(driver);
		postListPage.searchPostByTitleName("Wonders of the World");
		try {Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		postListPage.clickOnEditButton("Wonders of the World");	
		
		EditPostPage editPostPage = new EditPostPage(driver);
		editPostPage.insertDescription("Blablablablablablablablablablablablablablabalbalablbalbalbalbalbalbalbalbalblabalbalbalbalbalbalblabalblablablabalbalbalblablabalblablablabalblablabalbalbalblablabalbalbalbalbalblablabalblablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablabalblablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablabla");
		editPostPage.clickOnSave();
		
		assertTrue("description error message is missing!", editPostPage.errorDescriptionMessage("Please enter no more than 500 characters."));
	}
	
	//“Edit Post” - Description field edited
	@Test
	public void test24() {
		PostListPage postListPage = new PostListPage(driver);
		postListPage.searchPostByTitleName("Wonders of the World");
		try {Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		postListPage.clickOnEditButton("Wonders of the World");	
		
		EditPostPage editPostPage = new EditPostPage(driver);
		editPostPage.insertDescription("Friends who used to meet him at the pub are now hiking and skiing every weekend");
		editPostPage.clickOnSave();
		
	}
	
	//“Edit Post” - remove Tag
	@Test
	public void test25() {
		PostListPage postListPage = new PostListPage(driver);
		postListPage.searchPostByTitleName("Wonders of the World");
		try {Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		postListPage.clickOnEditButton("Wonders of the World");	
		
		EditPostPage editPostPage = new EditPostPage(driver);
		editPostPage.selectTag("Tag name Irina");
		editPostPage.clickOnSave();
		
		assertTrue("Error tag message is missing!", editPostPage.errorTagMessage("This field is required."));
		
	}
	
	//“Edit Post” - select different Tag
	@Test
	public void test26() {
		PostListPage postListPage = new PostListPage(driver);
		postListPage.searchPostByTitleName("Wonders of the World");
		try {Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		postListPage.clickOnEditButton("Wonders of the World");	
		
		EditPostPage editPostPage = new EditPostPage(driver);
		editPostPage.selectTag("Tag name Irina");
		editPostPage.selectTag("eos");
		editPostPage.clickOnSave();
		
		assertTrue("User is not redirected do the post list page!", postListPage.isOnPage());
		postListPage.searchPostByTitleName("Wonders of the World");
		try {Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue("Tag is not updated!", postListPage.getTagName("Wonders of the World").equalsIgnoreCase("eos"));	
	}
	
	//“Edit Post” - upload invalid photo file
	@Test
	public void test27() {
		PostListPage postListPage = new PostListPage(driver);
		postListPage.searchPostByTitleName("Wonders of the World");
		try {Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		postListPage.clickOnEditButton("Wonders of the World");	
		
		EditPostPage editPostPage = new EditPostPage(driver);
		editPostPage.uploadVideoFile();
		editPostPage.clickOnSave();
		
		assertTrue("Error photo message is missing!", editPostPage.errorPhotoMessage("The photo must be an image."));		
	}
	
	//“Edit Post” - delete photo 
	@Test
	public void test28() {
		PostListPage postListPage = new PostListPage(driver);
		postListPage.searchPostByTitleName("Wonders of the World");
		try {Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		postListPage.clickOnEditButton("Wonders of the World");	
		
		EditPostPage editPostPage = new EditPostPage(driver);
		editPostPage.clickOnDeletePhotoButton();
		editPostPage.clickOnSave();
		
		assertTrue("User is not redirected to the post list page!", postListPage.isOnPage());
		
		postListPage.searchPostByTitleName("Wonders of the World");
		try {Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		assertFalse("Photo is not deleted!", postListPage.isPhotoDisplayed("Wonders of the World").contains("validPhoto"));
	}
	
	//“Post List” page delete post
	@Test
	public void test29() {
		PostListPage postListPage = new PostListPage(driver);
		postListPage.searchPostByTitleName(updatedTitleName);
		try {Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		postListPage.clickOnDeleteButton(updatedTitleName);
		assertTrue("Post is not deleted", postListPage.isPostDeleted(updatedTitleName));
	}
			
}
