package ru.blc.cutlet.vk.objects.media;

import ru.blc.cutlet.vk.objects.VkApiObject;
import ru.blc.objconfig.ConfigurationSection;

public abstract class MediaObject extends VkApiObject {

	private String accessKey;
	
	public MediaObject(ConfigurationSection config) {
		this.accessKey = config.getString("access_key");
		this.loadData(config);
	}

	public String getAccessKey() {
		return accessKey;
	}
	
	public boolean hasAccessKey() {
		return getAccessKey()!=null;
	}
	
	public abstract int getId();
	
	public abstract int getOwnerId();
	
	protected abstract void loadData(ConfigurationSection config);
}
