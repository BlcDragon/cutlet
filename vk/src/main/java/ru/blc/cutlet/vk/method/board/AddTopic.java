package ru.blc.cutlet.vk.method.board;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import ru.blc.cutlet.vk.AccessToken.AccessTokenType;
import ru.blc.cutlet.vk.method.Method;
import ru.blc.cutlet.vk.method.ParamsSet;

import java.util.ArrayList;
import java.util.List;

public class AddTopic extends Method<AddTopic> {

	public AddTopic() {
		super("board.addTopic", AccessTokenType.USER);
	}

	@Override
	public AddTopicParamsSet getNewParamsSet() {
		return new AddTopicParamsSet(this);
	}
	
	public static class AddTopicParamsSet extends ParamsSet<AddTopic> {
		
		private int groupId;
		private String title, text;
		private boolean fromGroup;
		private String attachments;
		
		public AddTopicParamsSet(Method<AddTopic> method) {
			super(method);
		}

		@Override
		public List<NameValuePair> getParams() {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("group_id", String.valueOf(getGroupId())));
			params.add(new BasicNameValuePair("title", getTitle()));
			params.add(new BasicNameValuePair("text", getText()));
			if (isFromGroup()) params.add(new BasicNameValuePair("from_group", String.valueOf(isFromGroup())));
			if ((getAttachments()!=null)&&(!getAttachments().isEmpty())) params.add(new BasicNameValuePair("attachments", getAttachments()));
			return params;
		}

		public int getGroupId() {
			return groupId;
		}

		public AddTopicParamsSet setGroupId(int groupId) {
			this.groupId = groupId;
			return this;
		}

		public String getTitle() {
			return title;
		}

		public AddTopicParamsSet setTitle(String title) {
			this.title = title;
			return this;
		}

		public String getText() {
			return text;
		}

		public AddTopicParamsSet setText(String text) {
			this.text = text;
			return this;
		}

		public boolean isFromGroup() {
			return fromGroup;
		}

		public AddTopicParamsSet setFromGroup(boolean fromGroup) {
			this.fromGroup = fromGroup;
			return this;
		}

		public String getAttachments() {
			return attachments;
		}

		public AddTopicParamsSet setAttachments(String attachments) {
			this.attachments = attachments;
			return this;
		}
		
	}

}
