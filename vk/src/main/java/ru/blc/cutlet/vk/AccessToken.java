package ru.blc.cutlet.vk;

import ru.blc.validate.Validate;

public class AccessToken {

	private final String value;
	private final AccessTokenType type;
	
	public AccessToken(String value, AccessTokenType type) {
		Validate.notEmpty(value, "Token value can not be empty or null");
		this.value = value;
		Validate.notNull(type, "Token type can not be null");
		this.type = type;
	}
	
	public String getValue() {
		return value;
	}

	public AccessTokenType getType() {
		return type;
	}

	public enum AccessTokenType {
		SERVICE,GROUP,USER
	}
}
