package ru.blc.cutlet.vk.method.messages;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import ru.blc.cutlet.vk.AccessToken.AccessTokenType;
import ru.blc.cutlet.vk.method.Method;
import ru.blc.cutlet.vk.method.ParamsSet;

import java.util.ArrayList;
import java.util.List;

public class IsMessagesFromGroupAllowed extends Method<IsMessagesFromGroupAllowed> {

	public IsMessagesFromGroupAllowed() {
		super("messages.isMessagesFromGroupAllowed", AccessTokenType.USER, AccessTokenType.GROUP);
	}

	@Override
	public IsMessagesFromGroupAllowedParamsSet getNewParamsSet() {
		return new IsMessagesFromGroupAllowedParamsSet(this);
	}

	public static class IsMessagesFromGroupAllowedParamsSet extends ParamsSet<IsMessagesFromGroupAllowed> {

		public IsMessagesFromGroupAllowedParamsSet(Method<IsMessagesFromGroupAllowed> method) {
			super(method);
		}

		private Integer groupId, userId;
		
		@Override
		public List<NameValuePair> getParams() {
			List<NameValuePair> params = new ArrayList<>();
			if (getGroupId() != null) params.add(new BasicNameValuePair("group_id", String.valueOf(getGroupId())));
			if (getUserId() != null) params.add(new BasicNameValuePair("user_id", String.valueOf(getUserId())));
			return params;
		}

		public Integer getGroupId() {
			return groupId;
		}

		public IsMessagesFromGroupAllowedParamsSet setGroupId(Integer groupId) {
			this.groupId = groupId;
			return this;
		}

		public Integer getUserId() {
			return userId;
		}

		public IsMessagesFromGroupAllowedParamsSet setUserId(Integer userId) {
			this.userId = userId;
			return this;
		}
	}
}
