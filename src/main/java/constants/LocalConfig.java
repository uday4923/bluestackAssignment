package constants;

public class LocalConfig {
	public static enum YamlKey {
		URLS("urls");

		public String value;

		private YamlKey(String value) {
			this.value = value;
		}
	}
}
