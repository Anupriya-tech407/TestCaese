package com.medpay.test;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class MedPayTest {
	static WebDriver driver;
   static WebDriverWait wait;
		
		public static void main(String[] args) throws InterruptedException {
			
		    
		    
//=================1. Initialization============================================
		    
		    System.setProperty("webdriver.chrome.driver", "C:\\driver\\chromedriver_win32\\chromedriver.exe");;
		    driver = new ChromeDriver();
		    wait = new WebDriverWait(driver, 10);

//======================2. window settings=======================================
		    driver.manage().window().maximize();
		    driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		    driver.get("https://testing.d3eymc78cqte40.amplifyapp.com/login");

//============================3. login scenario====================================
		    driver.findElement(By.id("username")).sendKeys("EditorUser123");
		    driver.findElement(By.id("password")).sendKeys("123456");
		    driver.findElement(By.xpath("//button[@type='submit']")).click();
	
//=======================4. click Place order button================================
    		
		    driver.findElement(By.xpath("//button[@class='chakra-button css-11hsy1o']")).click();
		   
		  
  //===================5. Array of medicine names to be added=================================================
	    String[] listOfMedicineName = 		   new String[] {
		          "Crocin Pain Relief Strip Of 15 Tablets"
		        };

		    // Generic xpath of medicines from the drop down
		    String medicineXpath = "//p[text()='<replace>']";

//===================7. Addition of medicines================================================================
		    for (String medicineName : listOfMedicineName) {
		      driver.findElement(By.id("medicineName")).sendKeys("crocin");
		      String medicineNameXpath = medicineXpath.replace("<replace>", medicineName);
		      wait.until(ExpectedConditions.elementToBeClickable(By.xpath(medicineNameXpath)));
		      driver.findElement(By.xpath(medicineNameXpath)).click();
		    }

//======================8. Taking total cost of added machine==============================================
		    double totalExpected = 0;

		    List<WebElement> quantityElement =
		        driver.findElements(By.xpath("//div[@placeholder='Quantity']/input"));//1
		    List<WebElement> priceElement = driver.findElements(By.xpath("//input[@placeholder='MRP']"));//56

		    for (int i = 0; i < quantityElement.size(); i++) {
		      totalExpected += Double.parseDouble(quantityElement.get(i).getAttribute("value").trim())
		              * Double.parseDouble(priceElement.get(i).getAttribute("value").trim());
		      System.out.println(totalExpected);
		    }
		     

		    WebElement totalElement = driver.findElement(By.xpath("//p[@class='chakra-text css-mch0gu']"));
		    double totalActual = Double.parseDouble(totalElement.getText().trim());
		    
		    System.out.println(totalActual);
		    
//====================9. verify the expected ========================================================

		    if (totalExpected == totalActual) {
		      System.out.println("Total matching");
		    } else System.out.println("Total not matching");  


//====================== valid path =============================//		    
		    Thread.sleep(5000);
		    JavascriptExecutor js = (JavascriptExecutor) driver;
		    js.executeScript("window.scrollBy(0,0)", "");
		    Select select1 = new Select(driver.findElement(By.id("order_partner")));
		    select1.selectByVisibleText("Medpay");
		    driver
		        .findElement(By.id("partner_order_id"))
		        .click(); // added click so that partnerOrderId have some value to avoid element not
		    // intractable exception
		    driver.findElement(By.id("name")).sendKeys("anu");
		    driver.findElement(By.id("mobile")).sendKeys("9168976431");
		    driver.findElement(By.id("email")).sendKeys("anukarn98@gmail.com");
		    driver.findElement(By.id("address")).sendKeys("Test address");
		    driver.findElement(By.id("landmark")).sendKeys("test");
		    driver.findElement(By.id("pin_code")).sendKeys("888888");
		    driver.findElement(By.id("city")).sendKeys("Pune");
		    driver.findElement(By.id("state")).sendKeys("Maharashtra");
		    Select select = new Select(driver.findElement(By.id("payment_collection")));
		    select.selectByVisibleText("Medpay");
		    driver.findElement(By.xpath("//button[@type='submit']")).click();
		    Thread.sleep(5000);

		    System.out.println("=========order placed============");

		    // =================manage orders=====================

		    // click on Hamburger icon
		    driver.findElement(By.xpath("//button[@class='chakra-button css-zke2o8']")).click();
		    System.out.println("hamburger clicked");

		    // click on Manage order option
		    driver.findElement(By.xpath("//button[@class='css-jaumwa'][2]")).click();
		    System.out.println("2nd option selected");

		    driver.findElement(By.xpath("//div[@class='chakra-modal__overlay css-14rxmsw']")).click();
		    System.out.println("partner id clicked");

		    // ================Pending tab=====================//

		    // partner Id
		    String pendingAddress = driver.findElement(By.xpath("//td[@role='gridcell']/button")).getText();
		    driver.findElement(By.xpath("//td[@role='gridcell']/button")).click();
		    System.out.println("partneradress stored");

		    // check and assign first nearby pharmacy
		    driver.findElement(By.xpath("//label[@class='chakra-checkbox css-1uiwwan']")).click();
		    driver.findElement(By.xpath("//button[@class='chakra-button css-17ite0f']")).click();

		    // click on Assigned tab
		    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[text()='Assigned']/..")));
		    js.executeScript(
		        "arguments[0].click()", driver.findElement(By.xpath("//p[text()='Assigned']/..")));

		    List<WebElement> assigned_Id =
		        driver.findElements(By.xpath("//button[@class='chakra-text css-z28hru']"));
		    for (WebElement webElement : assigned_Id) {
		      String assignedAddress = webElement.getText();
		      if (pendingAddress.equals(assignedAddress)) {
		        System.out.println("Pending value and Assigned value matching");
		        break;
		      }
		    }
		    System.out.println("Pending value and Assigned value matching");
		    driver.quit();
		  }
}

