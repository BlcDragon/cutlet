package ru.blc.cutlet.vk.method.messages;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import ru.blc.cutlet.vk.AccessToken.AccessTokenType;
import ru.blc.cutlet.vk.method.Method;
import ru.blc.cutlet.vk.method.ParamsSet;

import java.util.ArrayList;
import java.util.List;

public class GetByConversationMessageId extends Method<GetByConversationMessageId> {

	public GetByConversationMessageId() {
		super("messages.getByConversationMessageId", AccessTokenType.USER, AccessTokenType.GROUP);
	}

	@Override
	public GetByConversationMessageIdParamsSet getNewParamsSet() {
		return new GetByConversationMessageIdParamsSet(this);
	}

	public static class GetByConversationMessageIdParamsSet extends ParamsSet<GetByConversationMessageId> {

		public GetByConversationMessageIdParamsSet(Method<GetByConversationMessageId> method) {
			super(method);
		}

		private Integer peerId, groupId;
		private Integer[] conversationMessageIds;
		private Boolean extended;
		private String[] fields;
		
		@Override
		public List<NameValuePair> getParams() {
			List<NameValuePair> params = new ArrayList<>();
			if (getPeerId() != null) params.add(new BasicNameValuePair("peer_id", String.valueOf(getPeerId())));
			if (getGroupId() != null) params.add(new BasicNameValuePair("group_id", String.valueOf(getGroupId())));
			if (getExtended() != null) params.add(new BasicNameValuePair("extended", String.valueOf(getExtended())));
			if (getConversationMessageIds() != null) params.add(new BasicNameValuePair("conversation_message_ids", arrayToString(getConversationMessageIds())));
			if (getFields() != null) params.add(new BasicNameValuePair("fields", arrayToString(getFields())));
			return params;
		}

		public Integer getPeerId() {
			return peerId;
		}

		public GetByConversationMessageIdParamsSet setPeerId(Integer peerId) {
			this.peerId = peerId;
			return this;
		}

		public Integer[] getConversationMessageIds() {
			return conversationMessageIds;
		}

		public GetByConversationMessageIdParamsSet setConversationMessageIds(Integer... conversationMessageIds) {
			this.conversationMessageIds = conversationMessageIds;
			return this;
		}

		public Boolean getExtended() {
			return extended;
		}

		public GetByConversationMessageIdParamsSet setExtended(Boolean extended) {
			this.extended = extended;
			return this;
		}

		public String[] getFields() {
			return fields;
		}

		public GetByConversationMessageIdParamsSet setFields(String... fields) {
			this.fields = fields;
			return this;
		}

		public Integer getGroupId() {
			return groupId;
		}

		public GetByConversationMessageIdParamsSet setGroupId(Integer groupId) {
			this.groupId = groupId;
			return this;
		}
	}
}
