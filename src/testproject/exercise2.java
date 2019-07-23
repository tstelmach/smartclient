package testproject;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.isomorphic.webdriver.ByScLocator;
import com.isomorphic.webdriver.SmartClientChromeDriver;
import com.isomorphic.webdriver.SmartClientFirefoxDriver;
import com.isomorphic.webdriver.SmartClientIEDriver;

public class exercise2 {

	 public static void main(String[] args) {
		 
		 DesiredCapabilities caps = DesiredCapabilities.chrome();
	     caps.setCapability("nativeEvents",false);
		 
		 System.setProperty("webdriver.chrome.driver", "lib/chromedriver.exe");
		 System.setProperty("webdriver.ie.driver", "lib/IEDriverServer.exe");		 
		 
		 SmartClientChromeDriver driver = new SmartClientChromeDriver(caps);
		 
		 driver.get("http://www.smartclient.com/smartgwt/showcase/#featured_dropdown_grid_category");		 
		 driver.manage().window().maximize();
		 		 
		 //wait for loading
		 By picker = ByScLocator.scLocator("scLocator=//testRoot[]/member[Class=DynamicForm||index=0||length=1||classIndex=0||classLength=1]/item[name=itemID||title=Item||index=0||Class=SelectItem]/[icon=\"picker\"]");		 
		 driver.waitForElementPresent(picker);		 
		 driver.click(picker);
		 
		 By grid = ByScLocator.scLocator("scLocator=//testRoot[]/member[Class=DynamicForm||index=0||length=1||classIndex=0||classLength=1]/item[name=itemID||title=Item||index=0||Class=SelectItem]/pickList/body");
		 driver.waitForElementPresent(grid);		 
		 
		 By arrowDown = ByScLocator.scLocator("scLocator=//testRoot[]/member[Class=DynamicForm||index=0||length=1||classIndex=0||classLength=1]/item[name=itemID||title=Item||index=0||Class=SelectItem]/pickList/body/vscrollbar/blank10");
		 By scroll = ByScLocator.scLocator("scLocator=//testRoot[]/member[Class=DynamicForm||index=0||length=1||classIndex=0||classLength=1]/item[name=itemID||title=Item||index=0||Class=SelectItem]/pickList/body/vscrollbar/peer[Class=SpritedVSimpleScrollThumb||index=0||length=1||classIndex=0||classLength=1||title=%26nbsp%3B||name=main]/");
		 		
		 By bar = ByScLocator.scLocator("scLocator=//testRoot[]/member[Class=DynamicForm||index=0||length=1||classIndex=0||classLength=1]/item[name=itemID||title=Item||index=0||Class=SelectItem]/pickList/body/vscrollbar/blank");
		 
		 driver.click(scroll);
		 
		 Integer barHeight = Integer.parseInt(driver.findElement(bar).getCssValue("height").replace("px", ""));
		 Integer topScroll = 0;
		 Integer found = 0;
		 
		 while(barHeight>topScroll && found==0){
			 driver.click(arrowDown);			 
			 topScroll = Integer.parseInt(driver.findElement(scroll).getCssValue("top").replace("px", ""));
			 
			 List<WebElement> rows = driver.findElements(By.xpath("//*[@id='isc_3Ctable']/tbody/tr"));

			 Integer row_num=0;
			 
			 for(WebElement trElement : rows)
		        {
		            List<WebElement> td_collection=trElement.findElements(By.xpath("td"));
		            Integer col_num=1;
		            
		            for(WebElement tdElement : td_collection)		            	
		            {
		            	if(col_num == 1 && tdElement.getText().contains("Exercise")){
		            		found++;
		            	};
		            	
		            	if(col_num == 2 && tdElement.getText().contains("Ea")){
		            		found++;
		            	};
		            	
		            	if(col_num == 3){
		            		try{
		            			Double value = Double.parseDouble(tdElement.getText());
		            			if(value > 1.1){
		            				found++;
		            			}
		            		}catch(Exception e){}		            		
		            		
		            	}
		                col_num++;
		            }
		            
		            if(found == 3) {
		            	System.out.println("found"  + row_num);
		            	JavascriptExecutor executor = (JavascriptExecutor)driver;
		            	executor.executeScript("arguments[0].click();", trElement);
		            	break;
		            }		            
		            
		            row_num++;
		            found =0;
		        } 
			 
		 } 
		 driver.close();	 		 
		 }
}
