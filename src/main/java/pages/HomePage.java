package pages;

import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.basic.BasicSliderUI.ScrollListener;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import automation.GetPage;

public class HomePage extends GetPage {

    public HomePage(WebDriver driver) {
        super(driver, "homepage");
    }   
    
    private String getGameNameFromTile(WebElement element) {
    	return element(element, "txt_game_name").getText();
    }
    
    public List<String> getGameList() {
    	List<String> gameNameList = new ArrayList<String>(); 
    	elements("games_section").stream().forEach(element -> {
    		String gameName = getGameNameFromTile(element);
    		gameNameList.add(gameName);
    	});
    	return gameNameList;
    }
    
    public void ClickGameTile(String gameName) {
    	//scrollDown(element("seciton_game"));
    	clickLink(element("game_tile", gameName));
    	wait.waitForPageToLoad();
    }
}
