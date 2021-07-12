package ru.blc.cutlet.vk.objects.main;

import ru.blc.objconfig.ConfigurationSection;
import ru.blc.objconfig.json.JsonConfiguration;

public class ChatAction {

	private ChatActionType type;
	private ConfigurationSection data;
	
	public static ChatAction load(String json) {
		return ChatAction.load(JsonConfiguration.loadConfiguration(json));
	}
	
	public static ChatAction load(ConfigurationSection json) {
		if (json==null) return null;
		ChatAction a = new ChatAction();
		a.data = json;
		a.type = ChatActionType.valueOf(json.getString("type").toUpperCase());
		return a;
	}
	
	public ChatActionType getType() {
		return type;
	}

	public ConfigurationSection getData() {
		return data;
	}

	public static enum ChatActionType{ 
		CHAT_PHOTO_UPDATE,
		CHAT_PHOTO_REMOVE,
		CHAT_CREATE,
		CHAT_TITLE_UPDATE,
		CHAT_INVITE_USER,
		CHAT_KICK_USER,
		CHAT_PIN_MESSAGE,
		CHAT_UNPIN_MESSAGE,
		CHAT_INVITE_USER_BY_LINK,
		CHAT_SCREENSHOT,
		;
		
		
	}
}
