package ru.blc.cutlet.vk.objects.media;

import ru.blc.objconfig.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class Sticker extends MediaObject {

	private int packId;
	private int stickerId;
	private List<StickerImage> images;
	private List<StickerImage> imagesWithBackground;
	
	public Sticker(ConfigurationSection config) {
		super(config);
	}
	
	@Override
	public int getId() {
		return stickerId;
	}
	
	@Override
	public int getOwnerId() {
		return 0;
	}

	public int getPackId() {
		return packId;
	}
	
	public int getStickerId() {
		return stickerId;
	}
	
	public List<StickerImage> getImages() {
		return images;
	}
	
	public List<StickerImage> getImagesWithBackground() {
		return imagesWithBackground;
	}
	
	@Override
	protected void loadData(ConfigurationSection config) {
		this.packId = config.getInt("product_id");
		this.stickerId = config.getInt("sticker_id");
		this.images = new ArrayList<>();
		for (ConfigurationSection section : config.getConfigurationSectionList("images")) {
			this.images.add(new StickerImage(section));
		}
		this.imagesWithBackground = new ArrayList<>();
		for (ConfigurationSection section : config.getConfigurationSectionList("images_with_background")) {
			this.imagesWithBackground.add(new StickerImage(section));
		}
	}

	public class StickerImage {
		
		private String url;
		private int width, height;
		
		public StickerImage(ConfigurationSection section) {
			this.url = section.getString("url");
			this.width = section.getInt("width");
			this.height = section.getInt("height");
		}
		
		public String getUrl() {
			return url;
		}
		
		public int getHeight() {
			return height;
		}
		
		public int getWidth() {
			return width;
		}
	}
}
