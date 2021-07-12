package ru.blc.cutlet.vk;

public enum ApiVersion {
	v5_124("5.124")
	;
	
	public static final ApiVersion LAST = ApiVersion.v5_124;
	
	private String text;
	
	ApiVersion(String value) {
		this.text = value;
	}

	public String getText() {
		return text;
	}
	
	public static ApiVersion byText(String text) {
		if (text==null) return LAST;
		if (text.equalsIgnoreCase("last")) return LAST;
		for (ApiVersion v : values()) {
			if (v.getText().equals(text)) return v;
		}
		return LAST;
	}
}
