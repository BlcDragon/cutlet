package ru.blc.cutlet.vk.objects.main;

import ru.blc.objconfig.ConfigurationSection;
import ru.blc.objconfig.json.JsonConfiguration;

public class Topic {

	private JsonConfiguration source;
	
	private int id;
	private String title;
	private int creationTime, createdBy, updateTime, updatedBy;
	private boolean isClosed, isFixed;
	private int commentsCount;
	private String previewComment;

	protected Topic() {
	}
	
	public int getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public int getCreationTime() {
		return creationTime;
	}
	
	public int getCreatedBy() {
		return createdBy;
	}
	
	public int getUpdateTime() {
		return updateTime;
	}
	
	public int getUpdatedBy() {
		return updatedBy;
	}
	
	public boolean isClosed() {
		return isClosed;
	}
	
	public boolean isFixed() {
		return isFixed;
	}
	
	public int getCommentsCount() {
		return commentsCount;
	}
	
	public String getPreviewComment() {
		return previewComment;
	}
	
	public String toJson() {
		return source.saveToString();
	}
	
	public static Topic load(String json) {
		return Topic.load(JsonConfiguration.loadConfiguration(json));
	}
	
	public static Topic load(ConfigurationSection json) {
		if (json==null) return null;
		Topic t = new Topic();
		t.source = JsonConfiguration.createFromSection(JsonConfiguration.class, json);
		t.id = json.getInt("id");
		t.title = json.getString("title");
		t.creationTime = json.getInt("created");
		t.createdBy = json.getInt("created_by");
		t.updateTime = json.getInt("updated");
		t.updatedBy = json.getInt("updated_by");
		t.isClosed = json.getInt("is_closed")==1;
		t.isFixed = json.getInt("is_fixed")==1;
		t.commentsCount = json.getInt("comments");
		t.previewComment = json.hasValue("first_comment")? json.getString("first_comment"):
			json.hasValue("last_comment")? json.getString("last_comment") : null;
		return t;
	}
}
