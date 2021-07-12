package ru.blc.cutlet.vk.method.messages;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import ru.blc.cutlet.vk.AccessToken.AccessTokenType;
import ru.blc.cutlet.vk.method.Method;
import ru.blc.cutlet.vk.method.ParamsSet;

import java.util.ArrayList;
import java.util.List;

public class GetConversations extends Method<GetConversations>{

	public GetConversations() {
		super("messages.getConversations", AccessTokenType.GROUP, AccessTokenType.USER);
	}

	@Override
	public GetConversationsParamsSet getNewParamsSet() {
		return new GetConversationsParamsSet(this);
	}
	
	public static class GetConversationsParamsSet extends ParamsSet<GetConversations> {

		private int offset;
		private int count = 20;
		private String filter = "all";
		private boolean extended = false;
		private int startMessageId = 0;
		private List<String> fields = new ArrayList<>();
		private int groupId = 0;
		
		
		public GetConversationsParamsSet(Method<GetConversations> method) {
			super(method);
		}

		@Override
		public List<NameValuePair> getParams() {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			if (getOffset()!=0) params.add(new BasicNameValuePair("offset", String.valueOf(getOffset())));
			if (getCount()!=20) params.add(new BasicNameValuePair("count", String.valueOf(getCount())));
			if (!getFilter().equals("all")) params.add(new BasicNameValuePair("filter", getFilter()));
			if (isExtended()) params.add(new BasicNameValuePair("extended", String.valueOf(isExtended())));
			if (getStartMessageId()!=0) params.add(new BasicNameValuePair("start_message_id", String.valueOf(getStartMessageId())));
			if (!getFields().isEmpty()) params.add(new BasicNameValuePair("fields", collectionToString(getFields())));
			if (getGroupId()!=0) params.add(new BasicNameValuePair("group_id", String.valueOf(getGroupId())));
			return params;
		}
		
		public int getOffset() {
			return offset;
		}
		
		public GetConversationsParamsSet setOffset(int offset) {
			this.offset = offset;
			return this;
		}
		
		public int getCount() {
			return count;
		}
		
		public GetConversationsParamsSet setCount(int count) {
			this.count = count;
			return this;
		}
		
		public String getFilter() {
			return filter;
		}
		
		public GetConversationsParamsSet setFilter(String filter) {
			this.filter = filter;
			return this;
		}
		
		public boolean isExtended() {
			return extended;
		}
		
		public GetConversationsParamsSet setExtended(boolean extended) {
			this.extended = extended;
			return this;
		}
		
		public int getStartMessageId() {
			return startMessageId;
		}
		
		public GetConversationsParamsSet setStartMessageId(int startMessageId) {
			this.startMessageId = startMessageId;
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
