package ru.blc.cutlet.vk.objects.media;

import ru.blc.objconfig.ConfigurationSection;

public class Video extends MediaObject {

	private int id;
	private int ownerId;
	
	private String title;
	private String description;
	private int duration;
	private String photo130, photo320, photo640, photo800, photo1280;
	private String firstFrame130, firstFrame320, firstFrame640, firstFrame800, firstFrame1280;
	private int date, addingDate;
	private int views;
	private int comments;
	private String player;
	private String platform;
	private boolean canEdit, canAdd, isPrivate; //=true if there is fields
	private boolean processing, live, upcoming; //=true if there is fields
	private boolean isFavorite; //normal boolean input 
	
	public Video(ConfigurationSection config) {
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
	
	public String getDescription() {
		return description;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public String getPhoto130() {
		return photo130;
	}
	
	public String getPhoto320() {
		return photo320;
	}
	
	public String getPhoto640() {
		return photo640;
	}
	
	public String getPhoto800() {
		return photo800;
	}
	
	public String getPhoto1280() {
		return photo1280;
	}
	
	public String getFirstFrame130() {
		return firstFrame130;
	}
	
	public String getFirstFrame320() {
		return firstFrame320;
	}
	
	public String getFirstFrame640() {
		return firstFrame640;
	}
	
	public String getFirstFrame800() {
		return firstFrame800;
	}
	
	public String getFirstFrame1280() {
		return firstFrame1280;
	}
	
	public int getDate() {
		return date;
	}
	
	public int getAddingDate() {
		return addingDate;
	}
	
	public int getViews() {
		return views;
	}
	
	public int getComments() {
		return comments;
	}
	
	public String getPlayer() {
		return player;
	}
	
	public String getPlatform() {
		return platform;
	}
	
	public boolean canEdit() {
		return canEdit;
	}
	
	public boolean canAdd() {
		return canAdd;
	}
	
	public boolean isPrivate() {
		return isPrivate;
	}
	
	public boolean isProcessing() {
		return processing;
	}
	
	public boolean isLive() {
		return live;
	}
	
	public boolean isUpcoming() {
		return upcoming;
	}
	
	public boolean isFavorite() {
		return isFavorite;
	}

	@Override
	protected void loadData(ConfigurationSection config) {
		this.id = config.getInt("id");
		this.ownerId = config.getInt("owner_id");
		this.title = config.getString("title");
		this.description = config.getString("description");
		this.duration = config.getInt("duration");
		this.photo130 = config.getString("photo_130");
		this.photo320 = config.getString("photo_320");
		this.photo640 = config.getString("photo_640");
		this.photo800 = config.getString("photo_800");
		this.photo1280 = config.getString("photo_1280");
		this.firstFrame130 = config.getString("first_frame_130");
		this.firstFrame320 = config.getString("first_frame_320");
		this.firstFrame640 = config.getString("first_frame_640");
		this.firstFrame800 = config.getString("first_frame_800");
		this.firstFrame1280 = config.getString("first_frame_1280");
		this.date = config.getInt("date");
		this.addingDate = config.getInt("adding_date");
		this.views = config.getInt("views");
		this.comments = config.getInt("comments");
		this.player = config.getString("player");
		this.platform = config.getString("platform");
		this.canEdit = config.hasValue("can_edit");
		this.canAdd = config.hasValue("can_add");
		this.isPrivate = config.hasValue("is_private");
		this.processing = config.hasValue("processing");
		this.live = config.hasValue("live");
		this.upcoming = config.hasValue("upcoming");
		this.isFavorite = config.getBoolean("is_favorite");
	}

}
