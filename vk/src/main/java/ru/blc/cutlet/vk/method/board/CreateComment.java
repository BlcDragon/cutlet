package ru.blc.cutlet.vk.method.board;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import ru.blc.cutlet.vk.AccessToken;
import ru.blc.cutlet.vk.method.Method;
import ru.blc.cutlet.vk.method.ParamsSet;

import java.util.ArrayList;
import java.util.List;

public class CreateComment extends Method<CreateComment> {

	public CreateComment() {
		super("board.createComment", AccessToken.AccessTokenType.USER);
	}

	@Override
	public CreateCommentParamsSet getNewParamsSet() {
		return new CreateCommentParamsSet(this);
	}
	
	public static class CreateCommentParamsSet extends ParamsSet<CreateComment> {

		public CreateCommentParamsSet(Method<CreateComment> method) {
			super(method);
		}

		private int groupId, topicId;
		private String message;
		private String attachments;
		private boolean fromGroup;
		private Integer stickerId;
		private String guid;

		@Override
		public List<NameValuePair> getParams() {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("group_id", String.valueOf(getGroupId())));
			params.add(new BasicNameValuePair("topic_id", String.valueOf(getTopicId())));	
			if ((getMessage()!=null)&&(!getMessage().isEmpty())) params.add(new BasicNameValuePair("message", getMessage()));
			if ((getAttachments()!=null)&&(!getAttachments().isEmpty())) params.add(new BasicNameValuePair("attachments", getAttachments()));
			if (isFromGroup()) params.add(new BasicNameValuePair("from_group", String.valueOf(isFromGroup())));
			if (this.stickerId != null) params.add(new BasicNameValuePair("sticker_id", String.valueOf(getStickerId())));
			if ((getGuid()!=null)&&(!getGuid().isEmpty())) params.add(new BasicNameValuePair("guid", getGuid()));	
			return params;
		}

		public int getGroupId() {
			return groupId;
		}

		public CreateCommentParamsSet setGroupId(int groupId) {
			this.groupId = groupId;
			return this;
		}

		public int getTopicId() {
			return topicId;
		}

		public CreateCommentParamsSet setTopicId(int topicId) {
			this.topicId = topicId;
			return this;
		}

		public String getMessage() {
			return message;
		}

		public CreateCommentParamsSet setMessage(String message) {
			this.message = message;
			return this;
		}

		public String getAttachments() {
			return attachments;
		}

		public CreateCommentParamsSet setAttachments(String attachments) {
			this.attachments = attachments;
			return this;
		}

		public boolean isFromGroup() {
			return fromGroup;
		}

		public CreateCommentParamsSet setFromGroup(boolean fromGroup) {
			this.fromGroup = fromGroup;
			return this;
		}

		public int getStickerId() {
			return stickerId;
		}

		public CreateCommentParamsSet setStickerId(int stickerId) {
			this.stickerId = stickerId;
			return this;
		}

		public String getGuid() {
			return guid;
		}

		public CreateCommentParamsSet setGuid(String guid) {
			this.guid = guid;
			return this;
		}
		
	}

}
