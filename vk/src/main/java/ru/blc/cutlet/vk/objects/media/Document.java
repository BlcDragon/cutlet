package ru.blc.cutlet.vk.objects.media;

import ru.blc.cutlet.vk.objects.media.Photo.Size;
import ru.blc.objconfig.ConfigurationSection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Document extends MediaObject{

	private int id;
	private int ownerId;
	
	private String title;
	private int size;
	private String ext;
	private String url;
	private int date;
	private DocumentType type;
	private Preview preview;
	
	
	public Document(ConfigurationSection config) {
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
	
	public int getSize() {
		return size;
	}
	
	public String getExt() {
		return ext;
	}
	
	public String getUrl() {
		return url;
	}
	
	public int getDate() {
		return date;
	}
	
	public DocumentType getType() {
		return type;
	}
	
	public Preview getPreview() {
		return preview;
	}
	
	@Override
	protected void loadData(ConfigurationSection config) {
		this.id = config.getInt("id");
		this.ownerId = config.getInt("owner_id");
		this.title = config.getString("title");
		this.size = config.getInt("size");
		this.ext = config.getString("ext");
		this.url = config.getString("url");
		this.date = config.getInt("date");
		this.type = DocumentType.byCode(config.getInt("type"));
		if (config.hasValue("preview")) this.preview = new Preview(config.getConfigurationSection("preview"));
	}
		
	public enum DocumentType{
		TXT(1),
		ARCHIVE(2),
		GIF(3),
		IMAGE(4),
		AUDIO(5),
		VIDEO(6),
		ELBOOK(7),
		UNKNOWN(8),
		;
		
		private int id;
		
		private DocumentType(int id) {
			this.id = id;
		}
		
		public int getId() {
			return id;
		}
		
		public static DocumentType byCode(int code) {
			return Arrays.asList(DocumentType.values())
			.stream()
			.filter(g -> g.getId()==code)
			.findFirst()
			.orElse(UNKNOWN);
		}
	}
	
	public class Preview {
		
		private Photo photo;
		
		public Preview(ConfigurationSection config) {
			if (config.hasValue("photo")) this.photo = new Photo(config.getConfigurationSection("photo"));
		}
		
		public Photo getPhoto() {
			return photo;
		}
		
		public class Photo {
			
			private List<Size> sizes = new ArrayList<>();
			
			public Photo(ConfigurationSection config) {
				for (ConfigurationSection s : config.getConfigurationSectionList("sizes")) {
					sizes.add(new Size(s));
				}
			}
			
			public List<Size> getSizes() {
				return sizes;
			}
		}
	}
}
