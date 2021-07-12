package ru.blc.cutlet.vk.objects.media;

import ru.blc.objconfig.ConfigurationSection;

public class MarketAlbum extends MediaObject {

	private int id, ownerId;
	private String title;
	private Photo photo;
	private int count;
	private int updatedTime;
	
	public MarketAlbum(ConfigurationSection config) {
		super(config);
	}
	
	public int getId() {
		return id;
	}
	
	public int getOwnerId() {
		return ownerId;
	}
	
	public String getTitle() {
		return title;
	}
	
	public Photo getPhoto() {
		return photo;
	}
	
	public int getCount() {
		return count;
	}
	
	public int getUpdatedTime() {
		return updatedTime;
	}
	
	@Override
	protected void loadData(ConfigurationSection config) {
		this.id = config.getInt("id");
		this.ownerId = config.getInt("owner_id");
		this.title = config.getString("title");
		this.photo = new Photo(config.getConfigurationSection("photo"));
		this.count = config.getInt("count");
		this.updatedTime = config.getInt("updated_time");
	}

}
