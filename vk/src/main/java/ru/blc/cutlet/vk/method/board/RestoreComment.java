package ru.blc.cutlet.vk.method.board;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import ru.blc.cutlet.vk.AccessToken.AccessTokenType;
import ru.blc.cutlet.vk.method.Method;
import ru.blc.cutlet.vk.method.ParamsSet;

import java.util.ArrayList;
import java.util.List;

public class RestoreComment extends Method<RestoreComment> {

	public RestoreComment() {
		super("board.restoreComment", AccessTokenType.USER, AccessTokenType.GROUP);
	}

	@Override
	public RestoreCommentParamsSet getNewParamsSet() {
		return new RestoreCommentParamsSet(this);
	}

	public static class RestoreCommentParamsSet extends ParamsSet<RestoreComment> {

		private int groupId, topicId, commentId;

		public RestoreCommentParamsSet(Method<RestoreComment> method) {
				super(method);
			}

		@Override
		public List<NameValuePair> getParams() {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("group_id", String.valueOf(getGroupId())));
			params.add(new BasicNameValuePair("topic_id", String.valueOf(getTopicId())));		
			params.add(new BasicNameValuePair("comment_id", String.valueOf(getCommentId())));		
			return params;
		}

		public int getGroupId() {
			return groupId;
		}

		public RestoreCommentParamsSet setGroupId(int groupId) {
			this.groupId = groupId;
			return this;
		}

		public int getTopicId() {
			return topicId;
		}

		public RestoreCommentParamsSet setTopicId(int topicId) {
			this.topicId = topicId;
			return this;
		}

		public int getCommentId() {
			return commentId;
		}

		public RestoreCommentParamsSet setCommentId(int commentId) {
			this.commentId = commentId;
			return this;
		}

	}

}
