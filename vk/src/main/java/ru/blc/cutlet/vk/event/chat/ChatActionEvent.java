package ru.blc.cutlet.vk.event.chat;

import ru.blc.cutlet.vk.objects.main.ChatAction;
import ru.blc.cutlet.vk.objects.main.Message;

public abstract class ChatActionEvent extends MessageEvent{
	
	private final ChatAction action;

	public ChatActionEvent(Message message, ChatAction action) {
		super(message);
		this.action = action;
	}
	
	public ChatAction getAction() {
		return action;
	}
}
