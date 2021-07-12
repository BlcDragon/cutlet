package ru.blc.cutlet.vk.objects.media;

import ru.blc.objconfig.ConfigurationSection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MarketItem extends MediaObject {
	
	private int id, ownerId;
	private String title, descritpion;
	private Price price;
	private Category category;
	private String thumbPhoto;
	private int date;
	private MarketItemAvailability availability;
	private boolean favorite;
	private List<Photo> photos;
	private boolean canComment,canRepost;
	private Likes likes;
	
	
	public MarketItem(ConfigurationSection config) {
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
	
	public String getDescritpion() {
		return descritpion;
	}
	
	public Price getPrice() {
		return price;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public String getThumbPhoto() {
		return thumbPhoto;
	}
	
	public int getDate() {
		return date;
	}
	
	public MarketItemAvailability getAvailability() {
		return availability;
	}
	
	public boolean isFavorite() {
		return favorite;
	}
	
	public List<Photo> getPhotos() {
		return photos;
	}
	
	public boolean canComment() {
		return canComment;
	}
	
	public boolean canRepost() {
		return canRepost;
	}
	
	public Likes getLikes() {
		return likes;
	}
	
	@Override
	protected void loadData(ConfigurationSection config) {
		this.id = config.getInt("id");
		this.ownerId = config.getInt("owner_id");
		this.title = config.getString("title");
		this.descritpion = config.getString("descritpion");
		this.price = new Price(config.getConfigurationSection("price"));
		this.category = new Category(config.getConfigurationSection("category"));
		this.thumbPhoto = config.getString("thumb_photo");
		this.date = config.getInt("date");
		this.availability = MarketItemAvailability.byCode(config.getInt("availability"));
		this.favorite = config.getBoolean("is_favorite");
		this.photos = new ArrayList<>();
		for (ConfigurationSection section : config.getConfigurationSectionList("photos")) {
			photos.add(new Photo(section));
		}
		this.canComment = config.getInt("can_comment")==1;
		this.canRepost = config.getInt("can_repost")==1;
		this.likes = config.hasValue("likes")? new Likes(config.getConfigurationSection("likes")) : null;
	}
	
	public enum MarketItemAvailability{
		AVAILABLE(0),DELETED(1),UNAVAILABLE(2),
		;
		
		private int code;
		
		private MarketItemAvailability(int code) {
			this.code = code;
		}
		
		public int getCode() {
			return code;
		}
		
		public static MarketItemAvailability byCode(int code) {
			return Arrays.asList(MarketItemAvailability.values())
			.stream()
			.filter(g -> g.getCode()==code)
			.findFirst()
			.orElse(UNAVAILABLE);
		}
	}
	
	public static class Likes { 
		
		private boolean userLikes;
		private int count;
		
		public Likes(ConfigurationSection section) {
			this.userLikes = section.getInt("user_likes")==1;
			this.count = section.getInt("count");
		}
		
		public boolean isUserLikes() {
			return userLikes;
		}
		
		public int getCount() {
			return count;
		}
	}
	
	public class Category {
		
		private int id;
		private String name;
		private Section section;
		
		public Category(ConfigurationSection section) {
			this.id = section.getInt("id");
			this.name = section.getString("name");
			this.section = new Section(section.getConfigurationSection("section"));
		}
		
		public int getId() {
			return id;
		}
		
		public String getName() {
			return name;
		}
		
		public Section getSection() {
			return section;
		}		
		
		public class Section {
			
			private int id;
			private String name;
			
			public Section(ConfigurationSection section) {
				this.id = section.getInt("id");
				this.name = section.getString("name");
			}
			
			public int getId() {
				return id;
			}
			
			public String getName() {
				return name;
			}			
		}
	}
	
	public static class Price {
		
		private int amount;
		private Currency currecny;
		private String text;
		
		public Price(ConfigurationSection section) {
			this.amount = section.getInt("amount");
			this.currecny = new Currency(section.getConfigurationSection("currency"));
			this.text = section.getString("text");
		}
		
		public int getAmount() {
			return amount;
		}
		
		public Currency getCurrecny() {
			return currecny;
		}
		
		public String getText() {
			return text;
		}			
		
		public class Currency {
			
			private int id;
			private String name;
			
			public Currency(ConfigurationSection section) {
				this.id = section.getInt("id");
				this.name = section.getString("name");
			}
			
			public int getId() {
				return id;
			}
			
			public String getName() {
				return name;
			}
		}
	}

}
