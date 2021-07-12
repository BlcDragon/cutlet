package ru.blc.cutlet.vk.event.chat;

import ru.blc.cutlet.api.event.HandlerList;
import ru.blc.cutlet.vk.objects.main.ChatAction;
import ru.blc.cutlet.vk.objects.main.Message;

public class UserQuitChatEvent extends ChatActionEvent {
	private static final HandlerList handlers = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	private int quitedUserId;
	private Reason reason;
	
	public UserQuitChatEvent(Message message, ChatAction action, int quitedUserId, Reason quitReason) {
		super(message, action);
		this.quitedUserId = quitedUserId;
		this.reason = quitReason;
	}
	
	public int getQuitedUserId() {
		return quitedUserId;
	}
	
	public Reason getReason() {
		return reason;
	}
	/**
	 * Возвращает айди пользователя, который кикнул
	 * Если пользователь вышел сам - айди этого пользователя
	 * @return айди кикавшего
	 */
	public int getKickerId() {
		switch (getReason()) {
		case KICKED:
			return getAction().getData().getInt("member_id");
		case LEAVE:
			return getQuitedUserId();
		default:
			return -1;
		}
	}
		
	public static enum Reason {
		/**
		 * Вышел сам
		 */
		LEAVE,
		/**
		 * Кикнут другим пользователем
		 */
		KICKED,
		;
	}

}
