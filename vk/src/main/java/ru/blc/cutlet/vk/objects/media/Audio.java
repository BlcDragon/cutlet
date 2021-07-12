package ru.blc.cutlet.vk.objects.media;

import ru.blc.objconfig.ConfigurationSection;

import java.util.Arrays;

public class Audio extends MediaObject {

	private int id;
	private int ownerId;
	
	private String artist;
	private String title;
	private int duration;
	private String url;
	private int lyricsId;
	private int albumId;
	private Genre genre;
	private int date;
	private boolean noSearch, isHq;
	
	
	public Audio(ConfigurationSection config) {
		super(config);
	}

	public int getId() {
		return id;
	}
	
	public int getOwnerId() {
		return ownerId;
	}
	
	public String getArtist() {
		return artist;
	}
	
	public String getTitle() {
		return title;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public String getUrl() {
		return url;
	}
	
	public int getLyricsId() {
		return lyricsId;
	}
	
	public int getAlbumId() {
		return albumId;
	}
	
	public Genre getGenre() {
		return genre;
	}
	
	public int getDate() {
		return date;
	}
	
	public boolean isNoSearch() {
		return noSearch;
	}

	public boolean isHq() {
		return isHq;
	}
	
	@Override
	protected void loadData(ConfigurationSection config) {
		this.id = config.getInt("id");
		this.ownerId = config.getInt("owner_id");
		this.artist = config.getString("artist");
		this.title = config.getString("title");
		this.duration = config.getInt("duration");
		this.url = config.getString("url");
		this.lyricsId = config.getInt("lyrics_id");
		this.albumId = config.getInt("album_id");
		this.genre = Genre.byCode(config.getInt("genre_id"));
		this.date = config.getInt("date");
		this.noSearch = config.hasValue("no_search");
		this.isHq = config.hasValue("is_hq");
	}

	public enum Genre {
		ROCK(1, "Rock"),
		POP(2, "Pop"),
		RAPHIPHOP(3, "Rap & Hip-Hop"),
		EASYLISTENING(4, "Easy Listening"),
		HOUSEDANCE(5, "House & Dance"),
		INSTRUMENTAL(6, "Instrumental"),
		METAL(7, "Metal"),
		ALTERNATIVE(21, "Alternative"),
		DUBSTEP(8, "Dubstep"),
		JAZZBLUES(1001, "Jazz & Blues"),
		DRUMMBASS(10, "Drum & Bass"),
		TRANCE(11, "Trance"),
		CHANCON(12, "Chanson"),
		ETHNIC(13, "Ethnic"),
		ACOUSTICVOCAL(14, "Acoustic & Vocal"),
		REGGAE(15, "Reggae"),
		CLASSICAL(16, "Classical"),
		INDIEPOP(17, "Indie Pop"),
		SPEECH(19, "Speech"),
		ELECTROPOPDISCO(22, "Electropop & Disco"),
		OTHER(18, "Other"),
		;
		
		private int code;
		private String name;
		
		private Genre(int code, String name) {
			this.code = code;
			this.name = name;
		}
		
		public int getCode() {
			return code;
		}
		
		public String getName() {
			return name;
		}
		
		public static Genre byCode(int code) {
			return Arrays.asList(Genre.values())
			.stream()
			.filter(g -> g.getCode()==code)
			.findFirst()
			.orElse(OTHER);
		}
	}
}
