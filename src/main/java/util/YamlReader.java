package util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import automation.LoadProp;

public class YamlReader {
	public static String yamlFilePath = LoadProp.getproperty("yamlFilePath");

	public static String setYamlFilePath() {
		System.out.println("Yaml file path ::" + yamlFilePath);
		try {
			new FileReader(yamlFilePath);
		} catch (FileNotFoundException e) {
			System.exit(0);
		}
		return yamlFilePath;
	}

	public static String getYamlValue(String token) {
		try {
			return getValue(token);
		} catch (FileNotFoundException ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}
	
	public static List<String> getYamlList(String token) {
		Reader doc = null;
		try {
			doc = new FileReader(yamlFilePath);
		} catch (FileNotFoundException e) {
			return null;
		}
		Yaml yaml = new Yaml();
		Map<String, Object> object = (Map<String, Object>) yaml.load(doc);
		return (ArrayList<String>)object.get(token);
	}

	private static String getValue(String token) throws FileNotFoundException {
		Reader doc = null;
		try {
			doc = new FileReader(yamlFilePath);
		} catch (FileNotFoundException e) {
			return null;
		}
		Yaml yaml = new Yaml();
		Map<String, Object> object = (Map<String, Object>) yaml.load(doc);
		return getMapValue(object, token);
	}

	public static String getMapValue(Map<String, Object> object, String token) {
		String[] st = token.split("\\.");
		return parseMap(object, token).get(st[st.length - 1]).toString();
	}

	private static Map<String, Object> parseMap(Map<String, Object> object, String token) {
		if (token.contains(".")) {
			String[] st = token.split("\\.");
			object = parseMap((Map<String, Object>) object.get(st[0]), token.replace(st[0] + ".", ""));
		}
		return object;
	}
}
