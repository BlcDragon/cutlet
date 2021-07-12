package ru.blc.cutlet.vk.event.chat;

import ru.blc.cutlet.vk.event.VkEvent;
import ru.blc.cutlet.vk.objects.main.Message;

public abstract class MessageEvent extends VkEvent {
	
	private final Message message;

	public MessageEvent(Message message) {
		super(message);
		this.message = message;
	}

	public Message getMessage() {
		return this.message;
	}
}
