package ru.blc.cutlet.vk.objects.media;

import ru.blc.objconfig.ConfigurationSection;

import java.util.List;

public class AudioMessage extends MediaObject{
	
	private int id;
	private int ownerId;
	private int duration;
	private List<Integer> waveform;
	private String linkOgg, linkMp3; 
	
	public AudioMessage(ConfigurationSection config) {
		super(config);
	}
	
	public int getId() {
		return id;
	}
	
	public int getOwnerId() {
		return ownerId;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public List<Integer> getWaveform() {
		return waveform;
	}
	
	public String getLinkOgg() {
		return linkOgg;
	}
	
	public String getLinkMp3() {
		return linkMp3;
	}

	@Override
	protected void loadData(ConfigurationSection config) {
		this.id = config.getInt("id");
		this.ownerId = config.getInt("owner_id");
		this.duration = config.getInt("duration");
		this.waveform = config.getIntegerList("waveform");
		this.linkOgg = config.getString("link_ogg");
		this.linkMp3 = config.getString("link_mp3");
	}

}
