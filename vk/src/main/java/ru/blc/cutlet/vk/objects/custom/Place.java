package ru.blc.cutlet.vk.objects.custom;

import ru.blc.cutlet.vk.objects.VkApiObject;
import ru.blc.objconfig.ConfigurationSection;
import ru.blc.objconfig.json.JsonConfiguration;

public class Place extends VkApiObject {

	private final int id;
	private final String title;
	private final int latitude;
	private final int longitude;
	private final int created;
	private final String icon;
	private final int checkins;
	private final int updated;
	private final int type;
	private final int country;
	private final int city;
	private final String address;
	

	protected Place(ConfigurationSection section) {
		this.id = section.getInt("id");
		this.title = section.getString("title");
		this.latitude = section.getInt("latitude");
		this.longitude = section.getInt("longitude");
		this.created = section.getInt("created");
		this.icon = section.getString("icon");
		this.checkins = section.getInt("checkins");
		this.updated = section.getInt("updated");
		this.type = section.getInt("type");
		this.country = section.getInt("country");
		this.city = section.getInt("city");
		this.address = section.getString("address");
	}
	
	public int getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public int getLatitude() {
		return latitude;
	}
	
	public int getLongitude() {
		return longitude;
	}
	
	public int getCreated() {
		return created;
	}
	
	public String getIcon() {
		return icon;
	}
	
	public int getCheckins() {
		return checkins;
	}
	
	public int getUpdated() {
		return updated;
	}
	
	public int getType() {
		return type;
	}
	
	public int getCountry() {
		return country;
	}
	
	public int getCity() {
		return city;
	}
	
	public String getAddress() {
		return address;
	}
	
	public static Place load(String json) {
		return Place.load(JsonConfiguration.loadConfiguration(json));
	}
	
	public static Place load(ConfigurationSection json) {
		if (json==null) return null;
		return new Place(json);
	}

}
