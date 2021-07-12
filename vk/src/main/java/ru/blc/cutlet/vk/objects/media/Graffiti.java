package ru.blc.cutlet.vk.objects.media;

import ru.blc.objconfig.ConfigurationSection;

public class Graffiti extends MediaObject{

	private int id;
	private int ownerId;
	private String src;
	private int width, height;
	
	public Graffiti(ConfigurationSection config) {
		super(config);
	}
	
	public int getId() {
		return id;
	}
	
	public int getOwnerId() {
		return ownerId;
	}

	public String getSrc() {
		return src;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	@Override
	protected void loadData(ConfigurationSection config) {
		this.id = config.getInt("id");
		this.ownerId = config.getInt("owner_id");
		this.src = config.getString("src");
		this.width = config.getInt("width");
		this.height = config.getInt("height");
		
	}

}
