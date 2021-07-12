package ru.blc.cutlet.vk.method.board;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import ru.blc.cutlet.vk.AccessToken.AccessTokenType;
import ru.blc.cutlet.vk.method.Method;
import ru.blc.cutlet.vk.method.ParamsSet;

import java.util.ArrayList;
import java.util.List;

public class EditTopic extends Method<EditTopic> {

	public EditTopic() {
		super("board.editTopic", AccessTokenType.USER);
	}

	@Override
	public EditTopicParamsSet getNewParamsSet() {
		return new EditTopicParamsSet(this);
	}

	public static class EditTopicParamsSet extends ParamsSet<EditTopic>{

		private int groupId, topicId;
		private String title;
		
		public EditTopicParamsSet(Method<EditTopic> method) {
			super(method);
		}

		@Override
		public List<NameValuePair> getParams() {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("group_id", String.valueOf(getGroupId())));
			params.add(new BasicNameValuePair("topic_id", String.valueOf(getTopicId())));	
			params.add(new BasicNameValuePair("title", getTitle()));	
			return params;
		}

		public int getGroupId() {
			return groupId;
		}

		public EditTopicParamsSet setGroupId(int groupId) {
			this.groupId = groupId;
			return this;
		}

		public int getTopicId() {
			return topicId;
		}

		public EditTopicParamsSet setTopicId(int topicId) {
			this.topicId = topicId;
			return this;
		}

		public String getTitle() {
			return title;
		}

		public EditTopicParamsSet setTitle(String title) {
			this.title = title;
			return this;
		}
		
	}
}
