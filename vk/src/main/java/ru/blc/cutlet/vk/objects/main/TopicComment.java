package ru.blc.cutlet.vk.objects.main;

import ru.blc.cutlet.vk.objects.media.Attachment;
import ru.blc.cutlet.vk.objects.media.MarketItem;
import ru.blc.objconfig.ConfigurationSection;
import ru.blc.objconfig.json.JsonConfiguration;

import java.util.ArrayList;
import java.util.List;

public class TopicComment {

	private JsonConfiguration source;
	
	private int id, fromId, date;
	private String text;
	private List<Attachment> attachments;
	private MarketItem.Likes likes;

	protected TopicComment() {
	}
	
	public int getId() {
		return id;
	}
	
	public int getFromId() {
		return fromId;
	}
	
	public int getDate() {
		return date;
	}
	
	public String getText() {
		return text;
	}
	
	public List<Attachment> getAttachments() {
		return attachments;
	}
	
	public MarketItem.Likes getLikes() {
		return likes;
	}
	
	public String toJson() {
		return source.saveToString();
	}
	
	public static TopicComment load(String json) {
		return TopicComment.load(JsonConfiguration.loadConfiguration(json));
	}
	
	public static TopicComment load(ConfigurationSection json) {
		if (json==null) return null;
		TopicComment t = new TopicComment();
		t.source = JsonConfiguration.createFromSection(JsonConfiguration.class, json);
		t.id = json.getInt("id");
		t.fromId = json.getInt("from_id");
		t.date = json.getInt("date");
		t.text = json.getString("text");
		t.attachments = new ArrayList<>();
		for (ConfigurationSection s : json.getConfigurationSectionList("attachments")) {
			t.attachments.add(Attachment.load(s));
		}
		t.likes = json.hasValue("likes")? new MarketItem.Likes(json.getConfigurationSection("likes")):null;
		return t;
	}
}
