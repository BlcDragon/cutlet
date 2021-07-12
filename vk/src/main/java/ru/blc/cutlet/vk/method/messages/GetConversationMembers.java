package ru.blc.cutlet.vk.method.messages;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import ru.blc.cutlet.vk.AccessToken.AccessTokenType;
import ru.blc.cutlet.vk.method.Method;
import ru.blc.cutlet.vk.method.ParamsSet;

import java.util.ArrayList;
import java.util.List;

public class GetConversationMembers extends Method<GetConversationMembers>{

	public GetConversationMembers() {
		super("messages.getConversationMembers", AccessTokenType.GROUP, AccessTokenType.USER);
	}

	@Override
	public GetConversationsParamsSet getNewParamsSet() {
		return new GetConversationsParamsSet(this);
	}
	
	public static class GetConversationsParamsSet extends ParamsSet<GetConversationMembers> {

		private int peerId;
		private int groupId;
		private final List<String> fields = new ArrayList<>();
		
		
		public GetConversationsParamsSet(Method<GetConversationMembers> method) {
			super(method);
		}

		@Override
		public List<NameValuePair> getParams() {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			if (getPeerId()!=0) params.add(new BasicNameValuePair("peer_id", String.valueOf(getPeerId())));
//			if (getCount()!=20) params.add(new BasicNameValuePair("count", String.valueOf(getCount())));
//			offset
//			if (isExtended()) params.add(new BasicNameValuePair("extended", String.valueOf(isExtended())));
			if (!getFields().isEmpty()) params.add(new BasicNameValuePair("fields", collectionToString(getFields())));
			if (getGroupId()!=0) params.add(new BasicNameValuePair("group_id", String.valueOf(getGroupId())));
			return params;
		}

		public int getPeerId() {
			return peerId;
		}

		public GetConversationsParamsSet setPeerId(int peerId) {
			this.peerId = peerId;
			return this;
		}

		public List<String> getFields() {
			return fields;
		}
		
		public GetConversationsParamsSet addField(String field) {
			this.fields.add(field);
			return this;
		}
		
		public GetConversationsParamsSet removeField(String field) {
			this.fields.remove(field);
			return this;
		}
		
		public int getGroupId() {
			return groupId;
		}
		
		public GetConversationsParamsSet setGroupId(int groupId) {
			this.groupId = groupId;
			return this;
		}
		
	}

}
