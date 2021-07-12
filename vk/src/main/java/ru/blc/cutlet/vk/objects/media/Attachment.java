package ru.blc.cutlet.vk.objects.media;

import ru.blc.objconfig.ConfigurationSection;
import ru.blc.objconfig.json.JsonConfiguration;

public class Attachment {

	private AttachmentType type;
	private MediaObject media;

	public Attachment(AttachmentType type, MediaObject media) {
		this.type = type;
		this.media = media;
	}

	public AttachmentType getType() {
		return type;
	}

	public MediaObject getMedia() {
		return media;
	}

	public static Attachment load(String json) {
		return Attachment.load(JsonConfiguration.loadConfiguration(json));
	}

	public static Attachment load(ConfigurationSection config) {
		AttachmentType type = AttachmentType.valueOf(config.getString("type").toUpperCase());
		switch (type) {
		case PHOTO:
			return new Attachment(type, new Photo(config.getConfigurationSection(type.name().toLowerCase())));
		case VIDEO:
			return new Attachment(type, new Video(config.getConfigurationSection(type.name().toLowerCase())));
		case AUDIO:
			return new Attachment(type, new Audio(config.getConfigurationSection(type.name().toLowerCase())));
		case DOC:
			return new Attachment(type, new Document(config.getConfigurationSection(type.name().toLowerCase())));
		case LINK:
			return new Attachment(type, new Link(config.getConfigurationSection(type.name().toLowerCase())));
		case MARKET:
			return new Attachment(type, new MarketItem(config.getConfigurationSection(type.name().toLowerCase())));
		case MARKET_ALBUM:
			return new Attachment(type, new MarketAlbum(config.getConfigurationSection(type.name().toLowerCase())));
		case WALL:
			return new Attachment(type, new Post(config.getConfigurationSection(type.name().toLowerCase())));
		case WALL_REPLY:
			return new Attachment(type, new Comment(config.getConfigurationSection(type.name().toLowerCase())));
		case STICKER:
			return new Attachment(type, new Sticker(config.getConfigurationSection(type.name().toLowerCase())));
		case GIFT:
			return new Attachment(type, new Gift(config.getConfigurationSection(type.name().toLowerCase())));
		case AUDIO_MESSAGE:
			return new Attachment(type, new AudioMessage(config.getConfigurationSection(type.name().toLowerCase())));
		case GRAFFITI:
			return new Attachment(type, new Graffiti(config.getConfigurationSection(type.name().toLowerCase())));
		case POLL:
			return new Attachment(type, new Poll(config.getConfigurationSection(type.name().toLowerCase())));
		default:
			return null;
		}
	}

	public static String parse(Attachment attachment){
		switch (attachment.getType()) {
			case PHOTO:
			case VIDEO:
			case AUDIO:
			case DOC:
			case POLL:
			case MARKET:
			case MARKET_ALBUM:
				return attachment.getType().name().toLowerCase()+attachment.getMedia().getOwnerId()+"_"+attachment.getMedia().getId()
						+(attachment.getMedia().hasAccessKey()?"_"+attachment.getMedia().getAccessKey():"");
			case STICKER:
			case WALL:
			case WALL_REPLY:
			case AUDIO_MESSAGE:
			case GRAFFITI:
			case GIFT:
			case LINK:
			default:
				return "";
		}
	}

	public enum AttachmentType {
		PHOTO, VIDEO, AUDIO, DOC, LINK, MARKET, MARKET_ALBUM, WALL, WALL_REPLY, STICKER, GIFT, AUDIO_MESSAGE, GRAFFITI, POLL;
	}
}
