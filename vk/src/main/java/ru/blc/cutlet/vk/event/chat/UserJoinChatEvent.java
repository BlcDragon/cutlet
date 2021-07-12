package ru.blc.cutlet.vk.event.chat;

import ru.blc.cutlet.api.event.HandlerList;
import ru.blc.cutlet.vk.objects.main.ChatAction;
import ru.blc.cutlet.vk.objects.main.Message;

public class UserJoinChatEvent extends ChatActionEvent {
	private static final HandlerList handlers = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	private final int invitedUserId;
	private final Reason reason;
	
	public UserJoinChatEvent(Message message, ChatAction action, int invitedUserId, Reason joinReason) {
		super(message, action);
		this.invitedUserId = invitedUserId;
		this.reason = joinReason;
	}
	
	public int getInvitedUserId() {
		return invitedUserId;
	}
	
	public Reason getReason() {
		return reason;
	}
	/**
	 * Возвращает айди пользователя, который пригласил
	 * Если приглашенный пользователь зашел по ссылке - 0
	 * Если приглашенный пользователь вернулся в диалог - айди этого пользователя
	 * @return айди приглашающего
	 */
	public int getInviterId() {
		switch (getReason()) {
		case LINK_JOIN:
			return 0;
		case INVITED_BY_OTHER:
			return getAction().getData().getInt("member_id");
		case RETURN:
			return getInvitedUserId();
		default:
			return -1;
		}
	}
		
	public enum Reason {
		/**
		 * Вход по ссылке
		 */
		LINK_JOIN,
		/**
		 * Приглашен другим пользователем
		 */
		INVITED_BY_OTHER,
		/**
		 * Вернулся сам после выхода
		 */
		RETURN,
		;
	}

}
