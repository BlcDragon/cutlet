package ru.blc.cutlet.vk.objects.media;

import ru.blc.objconfig.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class Photo extends MediaObject {

	private int id;
	private int ownerId;
	
	private int albumId,userId;
	private String description;
	private int date;
	private List<Size> sizes;
	private int width, height;
	
	
	public Photo(ConfigurationSection config) {
		super(config);
	}
	
	public int getId() {
		return id;
	}
	
	public int getOwnerId() {
		return ownerId;
	}
	
	public int getAlbumId() {
		return albumId;
	}

	public int getUserId() {
		return userId;
	}

	public String getDescription() {
		return description;
	}

	public int getDate() {
		return date;
	}

	public List<Size> getSizes() {
		return sizes;
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
		this.albumId = config.getInt("album_id");
		this.userId = config.getInt("user_id", -1);
		this.description = config.getString("text");
		this.date = config.getInt("date");
		this.sizes = new ArrayList<>();
		for (ConfigurationSection s : config.getConfigurationSectionList("sizes")) {
			sizes.add(new Size(s));
		}
		this.width = config.getInt("width", 0);
		this.height = config.getInt("height", 0);		
	}

	public static class Size {
		private String src;
		private int width, height;
		private String type;
		
		public Size(ConfigurationSection section) {
			this(section.getString("url"), section.getInt("width"), section.getInt("height"), section.getString("type"));
		}
		
		public Size(String src, int width, int height, String type) {
			this.src = src;
			this.width = width;
			this.height = height;
			this.type = type;
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
		
		public String getType() {
			return type;
		}
	}
}
