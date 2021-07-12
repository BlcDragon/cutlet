package ru.blc.cutlet.vk.method.messages;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import ru.blc.cutlet.vk.AccessToken.AccessTokenType;
import ru.blc.cutlet.vk.VkModule;
import ru.blc.cutlet.vk.method.Method;
import ru.blc.cutlet.vk.method.ParamsSet;

import java.util.ArrayList;
import java.util.List;

public class RemoveChatUser extends Method<RemoveChatUser> {

	public RemoveChatUser() {
		super("messages.removeChatUser", AccessTokenType.GROUP, AccessTokenType.USER);
	}

	@Override
	public RemoveChatUserParamsSet getNewParamsSet() {
		return new RemoveChatUserParamsSet(this);
	}
	
	public static class RemoveChatUserParamsSet extends ParamsSet<RemoveChatUser> {
		
		private static final int chatAdd = VkModule.CONVERSATIONS_IDS_ADD;

		private int chatId,userId,memberId;
		
		public RemoveChatUserParamsSet(Method<RemoveChatUser> method) {
			super(method);
		}

		@Override
		public List<NameValuePair> getParams() {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("chat_id", String.valueOf(getChatId())));
			if (getUserId() != 0) params.add(new BasicNameValuePair("user_id", String.valueOf(getUserId())));
			if (getMemberId() != 0) params.add(new BasicNameValuePair("member_id", String.valueOf(getMemberId())));
			return params;
		}

		public int getChatId() {
			return chatId;
		}

		public RemoveChatUserParamsSet setChatId(int chatId) {
			this.chatId = chatId;
			return this;
		}

		public RemoveChatUserParamsSet setChatIdByPeerId(int peerId) {
			this.chatId = peerId-chatAdd;
			return this;
		}

		public int getUserId() {
			return userId;
		}

		public RemoveChatUserParamsSet setUserId(int userId) {
			this.userId = userId;
			return this;
		}

		public int getMemberId() {
			return memberId;
		}

		public RemoveChatUserParamsSet setMemberId(int memberId) {
			this.memberId = memberId;
			return this;
		}
		
	}

}
