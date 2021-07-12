package ru.blc.cutlet.vk.method.board;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import ru.blc.cutlet.vk.AccessToken.AccessTokenType;
import ru.blc.cutlet.vk.method.Method;
import ru.blc.cutlet.vk.method.ParamsSet;

import java.util.ArrayList;
import java.util.List;

public class FixTopic extends Method<FixTopic> {

	public FixTopic() {
		super("board.fixTopic", AccessTokenType.USER);
	}

	@Override
	public FixTopicParamsSet getNewParamsSet() {
		return new FixTopicParamsSet(this);
	}

	public static class FixTopicParamsSet extends ParamsSet<FixTopic>{

		private int groupId, topicId;

		public FixTopicParamsSet(Method<FixTopic> method) {
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

		public FixTopicParamsSet setGroupId(int groupId) {
			this.groupId = groupId;
			return this;
		}

		public int getTopicId() {
			return topicId;
		}

		public FixTopicParamsSet setTopicId(int topicId) {
			this.topicId = topicId;
			return this;
		}
		
	}

}
