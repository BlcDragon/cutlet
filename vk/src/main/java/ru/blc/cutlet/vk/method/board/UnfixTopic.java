package ru.blc.cutlet.vk.method.board;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import ru.blc.cutlet.vk.AccessToken.AccessTokenType;
import ru.blc.cutlet.vk.method.Method;
import ru.blc.cutlet.vk.method.ParamsSet;

import java.util.ArrayList;
import java.util.List;

public class UnfixTopic extends Method<UnfixTopic> {

	public UnfixTopic() {
		super("board.unfixTopic", AccessTokenType.USER);
	}

	@Override
	public UnfixTopicParamsSet getNewParamsSet() {
		return new UnfixTopicParamsSet(this);
	}

	public static class UnfixTopicParamsSet extends ParamsSet<UnfixTopic>{

		private int groupId, topicId;

		public UnfixTopicParamsSet(Method<UnfixTopic> method) {
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

		public UnfixTopicParamsSet setGroupId(int groupId) {
			this.groupId = groupId;
			return this;
		}

		public int getTopicId() {
			return topicId;
		}

		public UnfixTopicParamsSet setTopicId(int topicId) {
			this.topicId = topicId;
			return this;
		}
		
	}
}
