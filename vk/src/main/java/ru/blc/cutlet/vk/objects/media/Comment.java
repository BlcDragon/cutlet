package ru.blc.cutlet.vk.objects.media;

import ru.blc.objconfig.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class Comment extends MediaObject{

	private int id, fromId, date;
	private String text;
	private int replyToUser, replyToComment;
	private List<Attachment> attachments;
	private List<Comment> parentStack;
	private Thread thread;
	
	public Comment(ConfigurationSection config) {
		super(config);
	}

	@Override
	public int getId() {
		return id;
	}
	
	@Override
	public int getOwnerId() {
		return getFromId();
	}
	
	public int getFromId() {
		return fromId;
	}
	
	public int getDate() {
		return date;
	}
	
	public String getText() {
		return text;
	}
	
	public int getReplyToUser() {
		return replyToUser;
	}
	
	public int getReplyToComment() {
		return replyToComment;
	}
	
	public List<Attachment> getAttachments() {
		return attachments;
	}
	
	public List<Comment> getParentStack() {
		return parentStack;
	}
	
	public Thread getThread() {
		return thread;
	}
	
	@Override
	protected void loadData(ConfigurationSection config) {
		this.id = config.getInt("id");	
		this.fromId = config.getInt("from_id");	
		this.date = config.getInt("date");
		this.text = config.getString("text");
		this.replyToUser = config.getInt("reply_to_user");
		this.replyToComment = config.getInt("reply_to_comment");
		this.attachments = new ArrayList<>();
		for (ConfigurationSection section : config.getConfigurationSectionList("attachments")) {
			this.attachments.add(Attachment.load(section));
		}
		this.parentStack = new ArrayList<>();
		for (ConfigurationSection section : config.getConfigurationSectionList("parent_stack")) {
			this.parentStack.add(new Comment(section));
		}
		this.thread = new Thread(config.getConfigurationSection("thread"));
	}
	
	public class Thread {
		
		private int count;
		private List<Comment> items;
		private boolean canPost,showReplyButton, groupsCanPost;
		
		public Thread(ConfigurationSection section) {
			this.count = section.getInt("count");
			this.items = new ArrayList<>();
			for (ConfigurationSection s : section.getConfigurationSectionList("items")) {
				this.items.add(new Comment(s));
			}
			this.canPost = section.getBoolean("can_post");
			this.showReplyButton = section.getBoolean("show_reply_button");
			this.groupsCanPost = section.getBoolean("groups_can_post");
		}
		
		public int getCount() {
			return count;
		}
		
		public List<Comment> getItems() {
			return items;
		}
		
		public boolean canPost() {
			return canPost;
		}
		
		public boolean showReplyButton() {
			return showReplyButton;
		}
		
		public boolean isGroupsCanPost() {
			return groupsCanPost;
		}		
	}

}
