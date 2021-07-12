package ru.blc.cutlet.vk.method.messages;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import ru.blc.cutlet.vk.AccessToken.AccessTokenType;
import ru.blc.cutlet.vk.method.Method;
import ru.blc.cutlet.vk.method.ParamsSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Delete extends Method<Delete> {

	public Delete() {
		super("messages.delete", AccessTokenType.USER, AccessTokenType.GROUP);
	}

	@Override
	public DeleteParamsSet getNewParamsSet() {
		return new DeleteParamsSet(this);
	}

	public static class DeleteParamsSet extends ParamsSet<Delete> {

		public DeleteParamsSet(Method<Delete> method) {
			super(method);
		}

		private Integer[] messageIds;
		private Integer[] conversationMessageIds;
		private Integer peerId;
		private Boolean deleteForAll;
		
		@Override
		public List<NameValuePair> getParams() {
			List<NameValuePair> params = new ArrayList<>();
			if (getMessageIds() != null) params.add(new BasicNameValuePair("message_ids", collectionToString(Arrays.asList(getMessageIds()))));
			if (getConversationMessageIds() != null) params.add(new BasicNameValuePair("conversation_message_ids", collectionToString(Arrays.asList(getConversationMessageIds()))));
			if (getPeerId()!=null) params.add(new BasicNameValuePair("peer_id", String.valueOf(getPeerId())));
			if (getDeleteForAll() != null) params.add(new BasicNameValuePair("delete_for_all",getDeleteForAll()?"1":"0"));
			return params;
		}

		public Integer[] getMessageIds() {
			return messageIds;
		}

		public DeleteParamsSet setMessageIds(Integer... messageIds) {
			this.messageIds = messageIds;
			return this;
		}

		public Integer[] getConversationMessageIds() {
			return conversationMessageIds;
		}

		public DeleteParamsSet setConversationMessageIds(Integer... conversationMessageIds) {
			this.conversationMessageIds = conversationMessageIds;
			return this;
		}

		public Integer getPeerId() {
			return peerId;
		}

		public DeleteParamsSet setPeerId(Integer peerId) {
			this.peerId = peerId;
			return this;
		}

		public Boolean getDeleteForAll() {
			return deleteForAll;
		}

		public DeleteParamsSet setDeleteForAll(Boolean deleteForAll) {
			this.deleteForAll = deleteForAll;
			return this;
		}
	}
}
