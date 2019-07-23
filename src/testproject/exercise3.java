package testproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.isomorphic.webdriver.ByScLocator;
import com.isomorphic.webdriver.SmartClientChromeDriver;
import com.isomorphic.webdriver.SmartClientFirefoxDriver;
import com.isomorphic.webdriver.SmartClientIEDriver;

public class exercise3 {
	
	static String alphabet = "abcdefghijklmnoprstuwxyz";

	 public static void main(String[] args) {
		 
		 DesiredCapabilities caps = DesiredCapabilities.chrome();
	     caps.setCapability("nativeEvents",false);
		 
		 System.setProperty("webdriver.chrome.driver", "lib/chromedriver.exe");
		 System.setProperty("webdriver.ie.driver", "lib/IEDriverServer.exe");		 
		 
		 SmartClientChromeDriver driver = new SmartClientChromeDriver(caps);
		 
		 driver.get("http://www.smartclient.com/smartgwt/showcase/#featured_nested_grid");		 
		 driver.manage().window().maximize();
		 
		 ((JavascriptExecutor) driver).executeScript("isc.AutoTest.implicitNetworkWait=true;");
		 
		 		 
		 //wait for loading
		 By grid = ByScLocator.scLocator("scLocator=//testRoot[]/member[Class=ListGrid||index=0||length=1||classIndex=0||classLength=1||roleIndex=0||roleLength=1||scRole=list]");
		 driver.waitForElementPresent(grid);		 
		 
		 By arrowDown = ByScLocator.scLocator("scLocator=//testRoot[]/member[Class=ListGrid||index=0||length=1||classIndex=0||classLength=1||roleIndex=0||roleLength=1||scRole=list]/body/vscrollbar/blank10");
		 
		 By scroll = ByScLocator.scLocator("scLocator=//testRoot[]/member[Class=ListGrid||index=0||length=1||classIndex=0||classLength=1||roleIndex=0||roleLength=1||scRole=list]/body/vscrollbar/peer[Class=SpritedVSimpleScrollThumb||index=0||length=1||classIndex=0||classLength=1||title=%26nbsp%3B||name=main]/");
		 		
		 By bar = ByScLocator.scLocator("scLocator=//testRoot[]/member[Class=ListGrid||index=0||length=1||classIndex=0||classLength=1||roleIndex=0||roleLength=1||scRole=list]/body/vscrollbar/blank");
		 
		 driver.click(scroll);
		 
		 Integer barHeight = Integer.parseInt(driver.findElement(bar).getCssValue("height").replace("px", ""));
		 Integer topScroll = 0;
		 Integer found = 0;
		 Integer row_num=0;
		 Integer expanded=0;
		 Random r = new Random();
		 
		 ArrayList<String> read_list = new ArrayList<String>();
		 
		 String tablexpath="//div[contains(@id,'GridRowExpansionRelatedRecordsSample')]/following-sibling::table[1]/tbody/tr";
		 
		 List<WebElement> rows = driver.findElements(By.xpath(tablexpath));		 
		 List<WebElement> initial_rows = driver.findElements(By.xpath(tablexpath));
		 
		 while(barHeight>topScroll){
			 found=0;
			 if(row_num!=0) {
				 driver.click(arrowDown);
				 //driver.waitForSystemDone(10);
				 ((JavascriptExecutor) driver).executeScript("!isc.RPCManager.requestsArePending();");
				 rows = driver.findElements(By.xpath(tablexpath));
			 }
			 topScroll = Integer.parseInt(driver.findElement(scroll).getCssValue("top").replace("px", ""));
			 
			 
			 //if(initial_rows.containsAll(rows) && row_num++ !=0){
			 /*
			 if(initial_rows.size() == rows.size() && row_num++ !=0){
				 continue; //still same list
			 }
			*/
			 
			 for(WebElement trElement : rows){
		            List<WebElement> td_collection=trElement.findElements(By.xpath("td"));
		            Integer col_num=1;
		            
		            for(WebElement tdElement : td_collection)		            	
		            {	     
		            	String current_cell= tdElement.getText();
		            	System.out.println(current_cell);
		            	if(col_num == 2 && current_cell.contains("Correction") && !read_list.contains(current_cell.trim())){
		            		
		            		WebElement expander = td_collection.get(0);
			            	expander.findElement(By.xpath("div")).click();
			            	read_list.add(current_cell);
			            	found++;
			            	
			            	//change description			            	
			            	List<WebElement> rows_collection = driver.findElements(By.xpath("//div[contains(@eventproxy,\"isc_ListGrid_"+ expanded++ +"_body\")]/div/table/tbody/tr"));
			            	
			            	if(rows_collection.size()==1) continue;
			            	
			            	Integer order=1;
			            				            	
			            	for(WebElement tr : rows_collection){			            		
			 		            List<WebElement> columns=tr.findElements(By.xpath("td"));
			 		            //System.out.println(columns.get(0).findElement(By.xpath("div")).getText());
			 		            if(columns.size() > 0){
			 		            	while(!columns.get(2).isDisplayed()){
			 		            		 driver.click(arrowDown);
			 		            	}
			 		            	if(columns.get(2).isDisplayed()){
			 		            		columns.get(2).click();
			 		            	}
			 		            }			 		            	
          
				            	Actions action = new Actions(driver);
				                action.sendKeys(order++ + " " + generate_random_n(10));
				                action.perform();		
				                
				                columns.get(1).click();

			 		        } 

			            	By save= ByScLocator.scLocator("scLocator=//testRoot[]/member[Class=ListGrid||index=0||length=1||classIndex=0||classLength=1||roleIndex=0||roleLength=1||scRole=list]/expansionLayout[Class=VLayout||index=0||length=2||classIndex=0||classLength=2]/member[Class=VLayout||index=0||length=1||classIndex=0||classLength=1]/member[Class=HLayout||index=1||length=2||classIndex=0||classLength=1]/member[Class=IButton||index=0||length=3||classIndex=0||classLength=3||roleIndex=0||roleLength=3||title=Save||scRole=button]/");
				            driver.click(save);
			                
			                //colapse
			                //driver.findElement(By.xpath("//*[contains(@id,'table')]/tbody/tr[9]/td[1]/div/span")).click();			                
			                
			            	break;		            		
		            	};
		            	col_num++;  	
		            }		      
		            row_num++;
		            if(found==1){
		            	break;
		            }

		        }			 
		 } 
		 //driver.close();	 		 
		 }

	 public static String generate_random_n(Integer n){
		 return RandomStringUtils.randomAlphabetic(n);		 
	 }
}
