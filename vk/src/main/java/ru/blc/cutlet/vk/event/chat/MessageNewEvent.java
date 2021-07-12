package ru.blc.cutlet.vk.event.chat;

import ru.blc.cutlet.api.event.HandlerList;
import ru.blc.cutlet.vk.command.VkCommandSender;
import ru.blc.cutlet.vk.objects.main.Message;

public class MessageNewEvent extends MessageEvent{

	private static final HandlerList handlers = new HandlerList();
	private final VkCommandSender sender;

	@Override
	public HandlerList getHandlers() {
        return handlers;
    }

	public static HandlerList getHandlerList() {
        return handlers;
    }

	public MessageNewEvent(Message message, VkCommandSender sender) {
		super(message);
		this.sender = sender;
	}

	public VkCommandSender getSender() {
		return sender;
	}
}
