package testproject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.isomorphic.webdriver.ByScLocator;
import com.isomorphic.webdriver.SmartClientChromeDriver;
import com.isomorphic.webdriver.SmartClientFirefoxDriver;
import com.isomorphic.webdriver.SmartClientIEDriver;

public class exercise1 {

	 public static void main(String[] args) {
		 
		 DesiredCapabilities caps = DesiredCapabilities.chrome();
	     caps.setCapability("nativeEvents",false);
		 
		 System.setProperty("webdriver.chrome.driver", "lib/chromedriver.exe");
		 System.setProperty("webdriver.ie.driver", "lib/IEDriverServer.exe");		 
		 
		 
		 SmartClientChromeDriver driver = new SmartClientChromeDriver(caps);
		 
		 driver.get("http://www.smartclient.com/smartgwt/showcase/#featured_tile_filtering");		 
		 driver.manage().window().maximize();
		 
		 //wait for loading
		 By grid = ByScLocator.scLocator("//testRoot[]/member[Class=VStack||index=0||length=1||classIndex=0||classLength=1]/member[Class=TileGrid||index=0||length=4||classIndex=0||classLength=1]");		 
		 driver.waitForElementPresent(grid);
		 
		 //set text field
		 By animalField = ByScLocator.scLocator("//testRoot[]/member[Class=VStack||index=0||length=1||classIndex=0||classLength=1]/member[Class=DynamicForm||index=1||length=4||classIndex=0||classLength=2]/item[name=commonName||title=Animal||index=0||Class=TextItem]/element");		 
		 driver.sendKeys(animalField, "a");
		 
		 //set slider to 40		 
		 By value = ByScLocator.scLocator("scLocator=//testRoot[]/member[Class=VStack||index=0||length=1||classIndex=0||classLength=1]/member[Class=DynamicForm||index=1||length=4||classIndex=0||classLength=2]/item[name=lifeSpan||title=Max%20Life%20Span||value=42||index=1||Class=SliderItem]/slider/valueLabel[Class=Label||index=0||length=1||classIndex=0||classLength=1||roleIndex=0||roleLength=1||scRole=label]");
		 By thumb = ByScLocator.scLocator("scLocator=//testRoot[]/member[Class=VStack||index=0||length=1||classIndex=0||classLength=1]/member[Class=DynamicForm||index=1||length=4||classIndex=0||classLength=2]/item[name=lifeSpan||title=Max%20Life%20Span||value=60||index=1||Class=SliderItem]/slider/thumb[Class=StatefulCanvas||index=0||length=1||classIndex=0||classLength=1]");
		 By clear = ByScLocator.scLocator("scLocator=//testRoot[]/member[Class=VStack||index=0||length=1||classIndex=0||classLength=1]/member[Class=HLayout||index=3||length=4||classIndex=0||classLength=1]/member[Class=IButton||index=1||length=2||classIndex=1||classLength=2||roleIndex=1||roleLength=2||title=Clear||scRole=button]");
		 
		 Integer actualValue = Integer.parseInt(driver.getText(value));
		 if(actualValue == 0 || actualValue ==null ) System.exit(1); //something wrong		 		
		 
		 while(actualValue != 40 && actualValue > 2){
			 //driver.dragAndDrop(thumb, "-2,+0").release(thumb);			 
			 driver.dragAndDrop(thumb, "-2,0");
			 driver.release(thumb);
			 actualValue = Integer.parseInt(driver.getText(value));
			 //System.out.println(actualValue);			 
		 }
		 
		 //set combobox		 
		 By picker = ByScLocator.scLocator("scLocator=//testRoot[]/member[Class=VStack||index=0||length=1||classIndex=0||classLength=1]/member[Class=DynamicForm||index=2||length=4||classIndex=1||classLength=2]/item[name=sortBy||title=Sort%20by||value=commonName||index=0||Class=SelectItem]/[icon=\"picker\"]");
		 By desiredValue = ByScLocator.scLocator("scLocator=//testRoot[]/member[Class=VStack||index=0||length=1||classIndex=0||classLength=1]/member[Class=DynamicForm||index=2||length=4||classIndex=1||classLength=2]/item[name=sortBy||title=Sort%20by||value=commonName||index=0||Class=SelectItem]/pickList/body/row[sortBy=lifeSpan||1]/col[fieldName=sortBy||0]"); 
		 driver.waitForElementClickable(picker);
		 driver.click(picker);
		 driver.waitForElementClickable(desiredValue);
		 driver.click(desiredValue);
		 
		 //check checkbox		 
		 By check = ByScLocator.scLocator("scLocator=//testRoot[]/member[Class=VStack||index=0||length=1||classIndex=0||classLength=1]/member[Class=DynamicForm||index=2||length=4||classIndex=1||classLength=2]/item[name=chkSortDir||title=Ascending||index=1||Class=CheckboxItem]/valueicon");
		 driver.waitForElementClickable(check);
		 if(driver.getValue(check) == null) 
			 driver.click(check);
		 
		 driver.close();	 		 
		 }
}
