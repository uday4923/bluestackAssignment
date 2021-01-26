package tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import api.GameLink;

public class GameTest extends BaseTest {
	List<String> gameList;
	GameLink gameLink;
	Map<String, List<String>> resultData = new HashMap<String, List<String>>();

	@DataProvider(name = "getData")
	public String[][] gameData() {
		String[][] gameData = new String[gameList.size()][1];
		for (int i = 0; i < gameList.size(); i++) {
			gameData[i][0] = gameList.get(i);
		}
		return gameData;
	}

	@Test
	public void Test_01VerifyHomePage() throws Exception {
		homePage.openUrl();
		gameList = homePage.getGameList();
		System.out.println(gameList);
	}
	
	@Test(dataProvider="getData")
	public void Test_02CheckGameSpecificDetails(String gameName) throws Exception{
		List<String> tempData = new ArrayList<String>();
		homePage.ClickGameTile(gameName);
		String url = gamePage.getPageUrl();
		tempData.add(url);
		
		//assert page status
		gameLink = new GameLink(url);
		String status = gameLink.executeAndGetStatus();
		tempData.add(status);
		
		String tournamentCount = gamePage.getTournamentCount();
		gamePage.navigateToPreviousPage();
		
		tempData.add(tournamentCount);
		resultData.put(gameName, tempData);
	}
	
	@Test
	public void Test_03PrintOutput() throws Exception{
		printResult(this.resultData);
	}

}
