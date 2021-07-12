package ru.blc.cutlet.vk.event.chat;

import ru.blc.cutlet.api.event.HandlerList;
import ru.blc.cutlet.vk.objects.main.ChatAction;
import ru.blc.cutlet.vk.objects.main.Message;

public class ChatTitleUpdateEvent extends ChatActionEvent {
	private static final HandlerList handlers = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	private final int updatedBy;
	private final String newTitle;


	public ChatTitleUpdateEvent(Message message, ChatAction action, int updatedBy, String newTitle) {
		super(message, action);
		this.updatedBy = updatedBy;
		this.newTitle = newTitle;
	}
	
	public String getNewTitle() {
		return newTitle;
	}
	
	public int getUpdatedBy() {
		return updatedBy;
	}
}
