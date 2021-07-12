package ru.blc.cutlet.vk.method.board;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import ru.blc.cutlet.vk.AccessToken.AccessTokenType;
import ru.blc.cutlet.vk.method.Method;
import ru.blc.cutlet.vk.method.ParamsSet;

import java.util.ArrayList;
import java.util.List;

public class DeleteTopic extends Method<DeleteTopic>{

	public DeleteTopic() {
		super("board.deleteTopic", AccessTokenType.USER);
	}

	@Override
	public DeleteTopicParamsSet getNewParamsSet() {
		return new DeleteTopicParamsSet(this);
	}

	public static class DeleteTopicParamsSet extends ParamsSet<DeleteTopic> {

		private int groupId, topicId;		
		
		public DeleteTopicParamsSet(Method<DeleteTopic> method) {
			super(method);
		}
		
		@Override
		public List<NameValuePair> getParams() {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("group_id", String.valueOf(getGroupId())));
			params.add(new BasicNameValuePair("topic_id", String.valueOf(getTopicId())));			
			return params;
		}

		public int getGroupId() {
			return groupId;
		}

		public DeleteTopicParamsSet setGroupId(int groupId) {
			this.groupId = groupId;
			return this;
		}

		public int getTopicId() {
			return topicId;
		}

		public DeleteTopicParamsSet setTopicId(int topicId) {
			this.topicId = topicId;
			return this;
		}
		
	}
}
