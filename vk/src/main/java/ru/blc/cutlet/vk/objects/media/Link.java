package ru.blc.cutlet.vk.objects.media;

import ru.blc.objconfig.ConfigurationSection;

public class Link extends MediaObject {

	private int id, ownerId;
	private String url;
	private String title;
	private String caption; //can be null
	private String description;
	private Photo photo; //can be null
	private Product product; //can be null
	private Button button; //can be null
	private String previewPage;
	private String previewUrl;
	
	
	public Link(ConfigurationSection config) {
		super(config);
	}
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public int getOwnerId() {
		return ownerId;
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getCaption() {
		return caption;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Photo getPhoto() {
		return photo;
	}
	
	public Product getProduct() {
		return product;
	}
	
	public Button getButton() {
		return button;
	}
	
	public String getPreviewPage() {
		return previewPage;
	}
	
	public String getPreviewUrl() {
		return previewUrl;
	}

	@Override
	protected void loadData(ConfigurationSection config) {
		this.id = config.getInt("id");
		this.url = config.getString("url");
		this.title = config.getString("title");
		this.caption = config.getString("caption");
		this.description = config.getString("description");
		this.photo = config.hasValue("photo")? new Photo(config.getConfigurationSection("photo")):null;
		this.product = config.hasValue("product")? new Product(config.getConfigurationSection("product")):null;
		this.button= config.hasValue("button")? new Button(config.getConfigurationSection("button")):null;
		this.previewPage = config.getString("preview_page");
		this.previewUrl = config.getString("previe_url");
	}

	public class Product { 
		
		private MarketItem.Price price;
		
		public Product(ConfigurationSection section) {
			this.price = new MarketItem.Price(section.getConfigurationSection("price"));
		}
		
		public MarketItem.Price getPrice() {
			return price;
		}
	}
	
	public static class Button { 
		
		private String title;
		private Action action; 
		
		public Button(ConfigurationSection section) {
			this.title = section.getString("title");
			this.action = new Action(section.getConfigurationSection("action"));
		}
		
		public String getTitle() {
			return title;
		}
		
		public Action getAction() {
			return action;
		}
		
		public static class Action {
			
			private ButtonActionType type;
			private String url;
			
			public Action(ConfigurationSection section) {
				this.type = ButtonActionType.valueOf(section.getString("type").toUpperCase());
				this.url = section.getString("url");
			}
			
			public ButtonActionType getType() {
				return type;
			}
			
			public String getUrl() {
				return url;
			}
			
			public enum ButtonActionType{
				OPEN_URL,
				;
			}
		}
	}
}
