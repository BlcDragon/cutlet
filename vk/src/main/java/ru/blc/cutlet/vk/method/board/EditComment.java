package ru.blc.cutlet.vk.method.board;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import ru.blc.cutlet.vk.AccessToken.AccessTokenType;
import ru.blc.cutlet.vk.method.Method;
import ru.blc.cutlet.vk.method.ParamsSet;

import java.util.ArrayList;
import java.util.List;

public class EditComment extends Method<EditComment> {

	public EditComment() {
		super("board.editComment", AccessTokenType.USER);
	}

	@Override
	public EditCommentParamsSet getNewParamsSet() {
		return new EditCommentParamsSet(this);
	}

	public static class EditCommentParamsSet extends ParamsSet<EditComment> {
		
		private int groupId, topicId, commentId;
		private String message;
		private String attachments;

		public EditCommentParamsSet(Method<EditComment> method) {
			super(method);
		}

		@Override
		public List<NameValuePair> getParams() {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("group_id", String.valueOf(getGroupId())));
			params.add(new BasicNameValuePair("topic_id", String.valueOf(getTopicId())));	
			params.add(new BasicNameValuePair("comment_id", String.valueOf(getCommentId())));	
			if ((getMessage()!=null)&&(!getMessage().isEmpty())) params.add(new BasicNameValuePair("message", getMessage()));
			if ((getAttachments()!=null)&&(!getAttachments().isEmpty())) params.add(new BasicNameValuePair("attachments", getAttachments()));	
			return params;
		}
		
		public int getGroupId() {
			return groupId;
		}

		public EditCommentParamsSet setGroupId(int groupId) {
			this.groupId = groupId;
			return this;
		}

		public int getTopicId() {
			return topicId;
		}

		public EditCommentParamsSet setTopicId(int topicId) {
			this.topicId = topicId;
			return this;
		}

		public int getCommentId() {
			return commentId;
		}

		public EditCommentParamsSet setCommentId(int commentId) {
			this.commentId = commentId;
			return this;
		}

		public String getMessage() {
			return message;
		}

		public EditCommentParamsSet setMessage(String message) {
			this.message = message;
			return this;
		}

		public String getAttachments() {
			return attachments;
		}

		public EditCommentParamsSet setAttachments(String attachments) {
			this.attachments = attachments;
			return this;
		}

	}
}
