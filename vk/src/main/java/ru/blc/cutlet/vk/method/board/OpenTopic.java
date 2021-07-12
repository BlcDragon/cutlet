package ru.blc.cutlet.vk.method.board;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import ru.blc.cutlet.vk.AccessToken.AccessTokenType;
import ru.blc.cutlet.vk.method.Method;
import ru.blc.cutlet.vk.method.ParamsSet;

import java.util.ArrayList;
import java.util.List;

public class OpenTopic extends Method<OpenTopic> {

	public OpenTopic() {
		super("board.openTopic", AccessTokenType.USER);
	}

	@Override
	public OpenTopicParamsSet getNewParamsSet() {
		return new OpenTopicParamsSet(this);
	}

	public static class OpenTopicParamsSet extends ParamsSet<OpenTopic>{

		private int groupId, topicId;

		public OpenTopicParamsSet(Method<OpenTopic> method) {
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

		public OpenTopicParamsSet setGroupId(int groupId) {
			this.groupId = groupId;
			return this;
		}

		public int getTopicId() {
			return topicId;
		}

		public OpenTopicParamsSet setTopicId(int topicId) {
			this.topicId = topicId;
			return this;
		}
		
	}
}
