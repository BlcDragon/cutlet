package ru.blc.cutlet.vk.objects.media;

import ru.blc.objconfig.ConfigurationSection;

public class Gift extends MediaObject {

	private int id;
	private String thumb256, thumb96, thumb48;

	public Gift(ConfigurationSection config) {
		super(config);
	}

	public int getId() {
		return id;
	}
	
	@Override
	public int getOwnerId() {
		return 0;
	}
	
	public String getThumb256() {
		return thumb256;
	}
	
	public String getThumb96() {
		return thumb96;
	}
	
	public String getThumb48() {
		return thumb48;
	}
	
	@Override
	protected void loadData(ConfigurationSection config) {
		this.id = config.getInt("id");
		this.thumb256 = config.getString("thumb_256");
		this.thumb96 = config.getString("thumb_96");
		this.thumb48 = config.getString("thumb_48");
	}
}
