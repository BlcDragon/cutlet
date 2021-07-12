package ru.blc.cutlet.vk.method.messages;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import ru.blc.cutlet.vk.AccessToken.AccessTokenType;
import ru.blc.cutlet.vk.VkModule;
import ru.blc.cutlet.vk.method.Method;
import ru.blc.cutlet.vk.method.ParamsSet;

import java.util.ArrayList;
import java.util.List;

public class EditChat extends Method<EditChat> {

	public EditChat() {
		super("messages.editChat", AccessTokenType.GROUP, AccessTokenType.USER);
	}

	@Override
	public RemoveChatUserParamsSet getNewParamsSet() {
		return new RemoveChatUserParamsSet(this);
	}
	
	public static class RemoveChatUserParamsSet extends ParamsSet<EditChat> {
		
		private static final int chatAdd = VkModule.CONVERSATIONS_IDS_ADD;

		private int chatId;
		private String title;
		
		public RemoveChatUserParamsSet(Method<EditChat> method) {
			super(method);
		}

		@Override
		public List<NameValuePair> getParams() {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("chat_id", String.valueOf(getChatId())));
			params.add(new BasicNameValuePair("title", String.valueOf(getTitle())));
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

		public String getTitle() {
			return title;
		}
		
		public RemoveChatUserParamsSet setTitle(String title) {
			this.title = title;
			return this;
		}
		
	}

}
