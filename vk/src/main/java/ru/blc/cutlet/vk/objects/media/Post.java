package ru.blc.cutlet.vk.objects.media;

import ru.blc.cutlet.vk.objects.custom.Place;
import ru.blc.objconfig.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class Post extends MediaObject {

	private int id, ownerId;
	private int fromId;
	private int createdBy;
	private int date;
	private String text;
	private int replyOwnerId, replyPostId;
	private boolean friendsOnly;
	private Comments comments;
	private Likes likes;
	private Reposts reposts;
	private Views views;
	private PostType postType;
	private PostSource postSource;
	private List<Attachment> attachments;
	private Geo geo;
	private int signerId;
	private List<Post> copyHistory;
	private boolean canPin, canDelete, canEdit;
	private boolean isPinned;
	private boolean markedAsAds;
	private boolean isFavorite;

	public Post(ConfigurationSection config) {
		super(config);
	}

	public int getId() {
		return id;
	}
	
	public int getOwnerId() {
		return ownerId;
	}
	
	public int getFromId() {
		return fromId;
	}
	
	public int getCreatedBy() {
		return createdBy;
	}
	
	public int getDate() {
		return date;
	}
	
	public String getText() {
		return text;
	}
	
	public int getReplyOwnerId() {
		return replyOwnerId;
	}
	
	public int getReplyPostId() {
		return replyPostId;
	}
	
	public boolean isFriendsOnly() {
		return friendsOnly;
	}
	
	public Comments getComments() {
		return comments;
	}
	
	public Likes getLikes() {
		return likes;
	}
	
	public Reposts getReposts() {
		return reposts;
	}
	
	public Views getViews() {
		return views;
	}
	
	public PostType getPostType() {
		return postType;
	}
	
	public PostSource getPostSource() {
		return postSource;
	}
	
	public List<Attachment> getAttachments() {
		return attachments;
	}
	
	public Geo getGeo() {
		return geo;
	}
	
	public int getSignerId() {
		return signerId;
	}
	
	public List<Post> getCopyHistory() {
		return copyHistory;
	}
	
	public boolean canPin() {
		return canPin;
	}
	
	public boolean canDelete() {
		return canDelete;
	}
	
	public boolean canEdit() {
		return canEdit;
	}
	
	public boolean isPinned() {
		return isPinned;
	}
	
	public boolean isMarkedAsAds() {
		return markedAsAds;
	}
	
	public boolean isFavorite() {
		return isFavorite;
	}
	
	@Override
	protected void loadData(ConfigurationSection config) {
		this.id = config.getInt("id");
		this.ownerId = config.getInt("owner_id");
		this.fromId = config.getInt("from_id");
		this.createdBy = config.getInt("created_by");
		this.date = config.getInt("date");
		this.text = config.getString("text");
		this.replyOwnerId = config.getInt("reply_owner_id");
		this.replyPostId = config.getInt("reply_post_id");
		this.friendsOnly = config.hasValue("friends_only");
		this.comments = config.hasValue("comments")?new Comments(config.getConfigurationSection("comments")):null;
		this.likes = config.hasValue("likes")?new Likes(config.getConfigurationSection("likes")):null;
		this.reposts = config.hasValue("reposts")?new Reposts(config.getConfigurationSection("reposts")):null;
		this.views = config.hasValue("views")?new Views(config.getConfigurationSection("views")):null;
		this.postType = PostType.valueOf(config.getString("post_type").toUpperCase());
		this.postSource = config.hasValue("post_source")? new PostSource(config.getConfigurationSection("post_source")):null;
		this.attachments = new ArrayList<>();
		for (ConfigurationSection section : config.getConfigurationSectionList("attachments")) {
			this.attachments.add(Attachment.load(section));
		}
		this.geo = config.hasValue("geo")? new Geo(config.getConfigurationSection("geo")):null;
		this.signerId = config.getInt("signer_id");
		this.copyHistory = new ArrayList<>();
		for (ConfigurationSection section : config.getConfigurationSectionList("copy_history")) {
			this.copyHistory.add(new Post(section));
		}
		this.canPin = config.getInt("can_pin")==1;
		this.canDelete = config.getInt("can_delete")==1;
		this.canEdit = config.getInt("can_edit")==1;
		this.isPinned = config.hasValue("is_pinned");
		this.markedAsAds = config.getInt("marked_as_ads")==1;
		this.isFavorite = config.getBoolean("is_favorite");		
	}

	public class Comments {

		private int count;
		private boolean canPost, grousCanPost, canClose, canOpen;
		
		public Comments(ConfigurationSection section) {
			this.count = section.getInt("count");
			this.canPost = section.getInt("can_post")==1;
			this.grousCanPost = section.getInt("grous_can_post")==1;
			this.canClose = section.getBoolean("can_close");
			this.canOpen = section.getBoolean("can_open");
		}
		
		public int getCount() {
			return count;
		}
		
		public boolean canPost() {
			return canPost;
		}
		
		public boolean grousCanPost() {
			return grousCanPost;
		}
		
		public boolean canClose() {
			return canClose;
		}
		
		public boolean canOpen() {
			return canOpen;
		}
	}

	public class Likes {
		
		private int count;
		private boolean likedByUser, canLike, canPublish;
		
		public Likes(ConfigurationSection section) {
			this.count = section.getInt("count");
			this.likedByUser = section.getInt("user_likes")==1;
			this.canLike = section.getInt("can_like")==1;
			this.canPublish = section.getInt("can_publish")==1;
		}
		
		public int getCount() {
			return count;
		}
		
		public boolean isLikedByUser() {
			return likedByUser;
		}
		
		public boolean canLike() {
			return canLike;
		}
		
		public boolean canPublish() {
			return canPublish;
		}
	}

	public class Reposts {

		private int count;
		private boolean repostedByUser;
		
		public Reposts(ConfigurationSection section) {
			this.count = section.getInt("count");
			this.repostedByUser = section.getInt("user_reposted")==1;
		}
		
		public int getCount() {
			return count;
		}
		
		public boolean isRepostedByUser() {
			return repostedByUser;
		}
	}

	public class Views {

		private int count;
		
		public Views(ConfigurationSection section) {
			this.count = section.getInt("count");
		}
		
		public int getCount() {
			return count;
		}
	}
	
	public enum PostType {
		POST, COPY, REPLY, POSTPONE, SUGGEST 
		;
	}
	
	public static class PostSource{
		
		private PostSourceType type;
		private String platform, data, url;
		
		
		public PostSource(ConfigurationSection section) {
			this.type = PostSourceType.valueOf(section.getString("type").toUpperCase());
			this.platform = section.getString("platform");
			this.data = section.getString("data");
			this.url = section.getString("url");
		}
		
		public PostSourceType getType() {
			return type;
		}
		
		public String getPlatform() {
			return platform;
		}
		
		public String getData() {
			return data;
		}
		
		public String getUrl() {
			return url;
		}
		
		public enum PostSourceType {
			VK, WIDGET, API, RSS, SMS
			;
		}
	}
	
	public class Geo {

		private String type;
		private String coordinates;
		private Place place;
		
		public Geo(ConfigurationSection section) {
			this.type = section.getString("type");
			this.coordinates = section.getString("coordinates");
			this.place = Place.load(section.getConfigurationSection("place"));
		}
		
		public String getType() {
			return type;
		}
		
		public String getCoordinates() {
			return coordinates;
		}
		
		public Place getPlace() {
			return place;
		}
	}
}
