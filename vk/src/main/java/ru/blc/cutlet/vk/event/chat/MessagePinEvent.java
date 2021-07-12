package ru.blc.cutlet.vk.event.chat;

import ru.blc.cutlet.api.event.HandlerList;
import ru.blc.cutlet.vk.objects.main.ChatAction;
import ru.blc.cutlet.vk.objects.main.Message;

public class MessagePinEvent extends ChatActionEvent {
	private static final HandlerList handlers = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	private int pinnedBy, conversationMessageId;
	private String text;


	public MessagePinEvent(Message message, ChatAction action, int pinnedBy, int conversationMessageId, String messageText) {
		super(message, action);
		this.pinnedBy = pinnedBy;
		this.conversationMessageId = conversationMessageId;
		this.text = messageText;
	}
	
	public String getText() {
		return text;
	}
	
	public int getPinnedBy() {
		return pinnedBy;
	}
	
	public int getConversationMessageId() {
		return conversationMessageId;
	}
}
