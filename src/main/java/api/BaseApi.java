package api;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.testng.Reporter;

import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseApi {
	
	private RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
	private MethodType method;
	private String baseUri;
	private String basePath;
    private Response response;

	public RequestSpecBuilder getSpec() {
		return this.requestSpecBuilder;
	}

	public enum MethodType {
		POST, GET, PUT, DELETE, PATCH
	}

	public MethodType getMethod() {
		return method;
	}

	public void setMethod(MethodType method) {
		this.method = method;
	}

    public void setBaseUri(String baseUri){
	    this.baseUri = baseUri;
	    requestSpecBuilder.setBaseUri(baseUri);
    }

    public String getBaseUri(){
	    return this.baseUri;
    }

    public void setBasePath(String basePath){
        this.basePath = basePath;
        requestSpecBuilder.setBasePath(basePath);
    }

    public String getBasePath(){
        return this.basePath;
    }


	public RequestSpecBuilder getRequestSpecBuilder() {
		return requestSpecBuilder;
	}

	public Response execute() {
		RequestSpecification requestSpecification = requestSpecBuilder.build();
		Response response;
		RestAssuredConfig config = new RestAssuredConfig();
		RestAssured.defaultParser = Parser.JSON;
		switch (method) {
		case GET:
			response = given().config(config).spec(requestSpecification).when().log().all().get().then().log().all().extract().response();
			break;
		case POST:
			response = given().config(config).spec(requestSpecification).when().log().all().post().then().log().all().extract().response();
			break;
		case PUT:
			response = given().config(config).spec(requestSpecification).when().log().all().put().then().log().all().extract().response();
			break;
		case DELETE:
			response = given().config(config).spec(requestSpecification).when().log().all().delete().then().log().all().extract().response();
			break;
		case PATCH:
			response = given().config(config).spec(requestSpecification).when().log().all().patch().then().log().all().extract().response();
			break;
		default:
			throw new RuntimeException("API method not specified");

		}
		this.response=response;
		return response;
	}
	
	public Response getApiResponse() {
		return this.response;
	}
}

