package api;

import java.io.IOException;

public class GameLink extends BaseApi {

	public GameLink(String url) throws IOException {
		generateRequest(url);
	}

	public void generateRequest(String url) {
		setMethod(MethodType.GET);
		setBaseUri(url);
	}
	
	public String executeAndGetStatus() {
		execute();
		return String.valueOf(getApiResponse().getStatusCode());
	}
}
