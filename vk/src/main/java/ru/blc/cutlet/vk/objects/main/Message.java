package ru.blc.cutlet.vk.objects.main;

import ru.blc.cutlet.vk.objects.VkApiObject;
import ru.blc.cutlet.vk.objects.custom.Place;
import ru.blc.cutlet.vk.objects.media.Attachment;
import ru.blc.objconfig.ConfigurationSection;
import ru.blc.objconfig.FileConfiguration;
import ru.blc.objconfig.json.JsonConfiguration;

import java.util.ArrayList;
import java.util.List;

public class Message extends VkApiObject {
	
	private JsonConfiguration source;

	private int id;
	private int conversationMessageId;
	private int fromId;
	private int date;
	private int peerId;
	private String text;
	private int randomId;
	private String ref;
	private String refSource;
	private List<Attachment> attachments = new ArrayList<>();
	private boolean important;
	private Place geo;
	private ConfigurationSection payload;
	private List<Message> fwdMessages;
	private Message replyMessage;
	private ChatAction action;

	protected Message() {
	}
	
	public int getId() {
		return id;
	}

	public int getConversationMessageId() {
		return conversationMessageId;
	}

	public int getFromId() {
		return fromId;
	}

	public int getDate() {
		return date;
	}

	public int getPeerId() {
		return peerId;
	}

	public String getText() {
		return text;
	}

	public int getRandomId() {
		return randomId;
	}

	public String getRef() {
		return ref;
	}

	public String getRefSource() {
		return refSource;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public boolean isImportant() {
		return important;
	}

	public Place getGeo() {
		return geo;
	}

	public ConfigurationSection getPayload() {
		return payload;
	}

	public List<Message> getFwdMessages() {
		return fwdMessages;
	}

	public Message getReplyMessage() {
		return replyMessage;
	}

	public ChatAction getAction() {
		return action;
	}
	
	public String toJson() {
		return source.saveToString();
	}

	public static Message load(String json) {
		return Message.load(JsonConfiguration.loadConfiguration(json));
	}
	
	public static Message load(ConfigurationSection json) {
		if (json==null) return null;
		Message m = new Message();
		m.source = FileConfiguration.createFromSection(JsonConfiguration.class, json);
		m.id = json.getInt("id",0);
		m.conversationMessageId = json.getInt("conversation_message_id",0);
		m.fromId = json.getInt("from_id");
		m.date = json.getInt("date");
		m.peerId = json.getInt("peer_id");
		m.text = json.getString("text");
		m.randomId = json.getInt("random_id");
		m.ref = json.getString("ref", "");
		m.refSource = json.getString("ref_source", "");
		ArrayList<Attachment> attachments = new ArrayList<>();
		for (ConfigurationSection s : json.getConfigurationSectionList("attachments")) {
			try {
				attachments.add(Attachment.load(s));
			}catch (Exception e) {
				e.printStackTrace();
				System.out.println(FileConfiguration.createFromSection(JsonConfiguration.class, s).saveToString());
			}
		}
		m.attachments = attachments;
		m.important = json.getBoolean("important");
		m.geo = Place.load(json.getConfigurationSection("geo"));
		m.payload = json.getConfigurationSection("payload");
		if (m.payload==null){
			try {
				String payL = json.getString("payload");
				if (payL!=null){
					m.payload = JsonConfiguration.loadConfiguration(payL);
				}
			}catch (Exception ignore){}
		}

		ArrayList<Message> fwd = new ArrayList<>();
		for (Object s : json.getList("fwd_messages", new ArrayList<>())) {
			if (s instanceof ConfigurationSection) {
				ConfigurationSection sect = (ConfigurationSection) s;
				fwd.add(Message.load(sect));
			}
		}
		m.fwdMessages = fwd;
		m.replyMessage = Message.load(json.getConfigurationSection("reply_message"));
		m.action = ChatAction.load(json.getConfigurationSection("action"));
		return m;
	}
}
