package pages;

import org.openqa.selenium.WebDriver;

import automation.GetPage;

public class GamePage extends GetPage{
    public GamePage(WebDriver driver) {
        super(driver, "gamepage");
    }   
    
    public String getTournamentCount() {
    	String count = "";
    	try {
    		if(element("txt_count").isDisplayed()) {
    			count = element("txt_count").getText();
    		}
    	}catch(Exception e) {
    		count = "0";
    	}
    	return count;
    	
    }
    
}
