package ru.blc.cutlet.vk.objects.main;

import ru.blc.objconfig.ConfigurationSection;
import ru.blc.objconfig.json.JsonConfiguration;

public class User {
	
	private JsonConfiguration source;

	private int id;
	private String firstName,lastName;
	private String deactivated;
	private boolean closed,canAccessClosed;
	
	protected User() {
		// TODO Auto-generated constructor stub
	}

	
	public static User load(String json) {
		return User.load(JsonConfiguration.loadConfiguration(json));
	}
	
	public static User load (ConfigurationSection config) {
		User result = new User();
		
		result.source = JsonConfiguration.createFromSection(config);
		
		result.id = config.getInt("id");
		result.firstName = config.getString("first_name");
		result.lastName = config.getString("last_name");
		result.deactivated = config.getString("deactivated");
		result.closed = config.getBoolean("is_closed");
		result.canAccessClosed = config.getBoolean("can_access_closed");
		return result;
	}
	
	public JsonConfiguration getSource() {
		return source;
	}
	
	public int getId() {
		return id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getDeactivated() {
		return deactivated;
	}
	
	public boolean isClosed() {
		return closed;
	}
	
	public boolean canAccessClosed() {
		return canAccessClosed;
	}
}
