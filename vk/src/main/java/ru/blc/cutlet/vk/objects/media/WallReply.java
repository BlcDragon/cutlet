package ru.blc.cutlet.vk.objects.media;

import ru.blc.objconfig.ConfigurationSection;

public class WallReply extends Comment{

	private int postId;
	private int ownerId;
	
	public WallReply(ConfigurationSection config) {
		super(config);
	}
	
	public int getPostId() {
		return postId;
	}
	
	public int getOwnerId() {
		return ownerId;
	}
	
	@Override
	protected void loadData(ConfigurationSection config) {
		this.postId = config.getInt("psot_id");
		this.ownerId = config.getInt("owner_id");
		super.loadData(config);
	}
}
