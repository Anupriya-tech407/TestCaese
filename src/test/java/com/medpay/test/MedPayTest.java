package com.medpay.test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MedPayTest {
		
		public static void main(String[] args) throws InterruptedException {
			
		    WebDriver driver;
		    WebDriverWait wait;
		    
//=================1. Initialization============================================
		    
		    System.setProperty("webdriver.chrome.driver", "C:\\driver\\chromedriver_win32\\chromedriver.exe");;
		    driver = new ChromeDriver();
		    wait = new WebDriverWait(driver, 8);

//======================2. window settings=======================================
		    driver.manage().window().maximize();
		    driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		    driver.get("https://testing.d3eymc78cqte40.amplifyapp.com/login");

//============================3. login scenario====================================
		    driver.findElement(By.id("username")).sendKeys("TestUser123");
		    driver.findElement(By.id("password")).sendKeys("123456");
		    driver.findElement(By.xpath("//button[@type='submit']")).click();
	
//=======================4. click Place order button================================
    		
		    driver.findElement(By.xpath("//button[@class='chakra-button css-11hsy1o']")).click();
		    
//=======================5. Input fields on place order button ============================================
		    
		    List <WebElement> all_field = driver.findElements(By.xpath("//div[@role='group']"));
		    System.out.println(all_field.size());
		    
		    for(int i=0; i<all_field.size(); i++) {
		    	System.out.println(all_field.get(i).getText());
		    }
		    
		   driver.findElement(By.xpath("//button[@type='submit']")).click();
		
		  
 //===================6. Array of medicine names to be added=================================================
		    String[] listOfMedicineName =
		        new String[] {
		          "Cipla Maxirich Multi-Vitamin Capsules Box Of 30", "Cipla Maxirich Tulsi Drops - 30 Ml"
		        };

		    // Generic xpath of medicines from the drop down
		    String medicineXpath = "//p[text()='<replace>']";

//===================7. Addition of medicines================================================================
		    for (String medicineName : listOfMedicineName) {
		      driver.findElement(By.id("medicineName")).sendKeys("cipla");
		      String medicineNameXpath = medicineXpath.replace("<replace>", medicineName);
		      wait.until(ExpectedConditions.elementToBeClickable(By.xpath(medicineNameXpath)));
		      driver.findElement(By.xpath(medicineNameXpath)).click();
		    }

//======================8. Taking total cost of added machine==============================================
		    double totalExpected = 0;

		    List<WebElement> quantityElement =
		        driver.findElements(By.xpath("//div[@placeholder='Quantity']/input"));
		    List<WebElement> priceElement = driver.findElements(By.xpath("//input[@placeholder='MRP']"));

		    for (int i = 0; i < quantityElement.size(); i++) {
		      totalExpected += Double.parseDouble(quantityElement.get(i).getAttribute("value").trim())
		              * Double.parseDouble(priceElement.get(i).getAttribute("value").trim());
		      System.out.println(totalExpected);
		    }
		     

		    WebElement totalElement = driver.findElement(By.xpath("//p[@class='chakra-text css-mch0gu']"));
		    double totalActual = Double.parseDouble(totalElement.getText().trim());
		    
		    System.out.println(totalActual);
		    
//====================8. verify the expected ========================================================

		    if (totalExpected == totalActual) {
		      System.out.println("Total matching");
		    } else System.out.println("Total not matching");

//==================9. close the browser================================================================	    
		    driver.quit();
		  }

}

