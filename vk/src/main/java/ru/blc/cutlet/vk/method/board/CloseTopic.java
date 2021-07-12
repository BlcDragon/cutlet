package ru.blc.cutlet.vk.method.board;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import ru.blc.cutlet.vk.AccessToken.AccessTokenType;
import ru.blc.cutlet.vk.method.Method;
import ru.blc.cutlet.vk.method.ParamsSet;

import java.util.ArrayList;
import java.util.List;

public class CloseTopic extends Method<CloseTopic> {

	public CloseTopic() {
		super("board.closeTopic", AccessTokenType.USER);
	}

	@Override
	public CloseTopicParamsSet getNewParamsSet() {
		return new CloseTopicParamsSet(this);
	}
	
	public static class CloseTopicParamsSet extends ParamsSet<CloseTopic> {

		private int groupId, topicId;

		public CloseTopicParamsSet(Method<CloseTopic> method) {
			super(method);
		}

		@Override
		public List<NameValuePair> getParams() {
			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("group_id", String.valueOf(getGroupId())));
			params.add(new BasicNameValuePair("topic_id", String.valueOf(getTopicId())));			
			return params;
		}

		public int getGroupId() {
			return groupId;
		}

		public CloseTopicParamsSet setGroupId(int groupId) {
			this.groupId = groupId;
			return this;
		}

		public int getTopicId() {
			return topicId;
		}

		public CloseTopicParamsSet setTopicId(int topicId) {
			this.topicId = topicId;
			return this;
		}
		
	}

}
