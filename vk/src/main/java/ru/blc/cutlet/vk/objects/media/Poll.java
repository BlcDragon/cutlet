package ru.blc.cutlet.vk.objects.media;

import ru.blc.objconfig.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class Poll extends MediaObject{

	private int id, ownerId, created;
	private String question;
	private int votes;
	private List<Answer> answers;
	private boolean anonymous, multiple;
	private List<Integer> answerIds;
	private int endDate;
	private boolean closed, isBoard, canEdit, canRepost, canShare;
	private int authorId;
	private Photo photo;
	private Background background;
	private List<Integer> friends;
	
	public Poll(ConfigurationSection config) {
		super(config);
	}

	@Override
	protected void loadData(ConfigurationSection config) {
		this.id = config.getInt("id");
		this.ownerId = config.getInt("owner_id");
		this.created = config.getInt("created");
		this.question = config.getString("question");
		this.votes = config.getInt("votes");
		this.answers = new ArrayList<>();
		for (ConfigurationSection section : config.getConfigurationSectionList("answers")) {
			answers.add(new Answer(section));
		}
		this.anonymous = config.getBoolean("anonymous");
		this.multiple = config.getBoolean("multiple");
		this.answerIds = config.getIntegerList("answer_ids");
		this.endDate = config.getInt("end_date");
		this.closed = config.getBoolean("closed");
		this.isBoard = config.getBoolean("is_board");
		this.canEdit = config.getBoolean("can_edit");
		this.canRepost = config.getBoolean("can_repost");
		this.canShare = config.getBoolean("can_share");
		this.authorId = config.getInt("author_id");
		this.photo = config.hasValue("photo")? new Photo(config.getConfigurationSection("photo")):null;
		this.background = new Background(config.getConfigurationSection("background"));
		this.friends = config.getIntegerList("friends");
	}
	
	public int getId() {
		return id;
	}
	
	public int getOwnerId() {
		return ownerId;
	}
	
	public int getCreated() {
		return created;
	}
	
	public String getQuestion() {
		return question;
	}
	
	public int getVotes() {
		return votes;
	}
	
	public List<Answer> getAnswers() {
		return answers;
	}
	
	public boolean isAnonymous() {
		return anonymous;
	}
	
	public boolean isMultiple() {
		return multiple;
	}
	
	public List<Integer> getAnswerIds() {
		return answerIds;
	}
	
	public int getEndDate() {
		return endDate;
	}
	
	public boolean isClosed() {
		return closed;
	}
	
	public boolean isBoard() {
		return isBoard;
	}
	
	public boolean canEdit() {
		return canEdit;
	}
	
	public boolean canRepost() {
		return canRepost;
	}
	
	public boolean canShare() {
		return canShare;
	}
	
	public int getAuthorId() {
		return authorId;
	}
	
	public Photo getPhoto() {
		return photo;
	}
	
	public Background getBackground() {
		return background;
	}
	
	public List<Integer> getFriends() {
		return friends;
	}
	
	public class Answer{
		
		private int id;
		private String text;
		private int votes;
		private double rate;
		
		
		public Answer(ConfigurationSection section) {
			this.id = section.getInt("id");
			this.text = section.getString("text");
			this.votes = section.getInt("votes");
			this.rate = section.getDouble("rate");
		}
		
		public int getId() {
			return id;
		}
		
		public String getText() {
			return text;
		}
		
		public int getVotes() {
			return votes;
		}
		
		public double getRate() {
			return rate;
		}
	}
	
	public class Background{
		
		private ConfigurationSection section;
				
		public Background(ConfigurationSection section) {
			this.section = section;
		}
		
		public ConfigurationSection getSection() {
			return section;
		}
	}

}
