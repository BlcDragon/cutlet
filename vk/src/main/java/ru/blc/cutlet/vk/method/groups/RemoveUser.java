package ru.blc.cutlet.vk.method.groups;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import ru.blc.cutlet.vk.AccessToken.AccessTokenType;
import ru.blc.cutlet.vk.method.Method;
import ru.blc.cutlet.vk.method.ParamsSet;

import java.util.ArrayList;
import java.util.List;

public class RemoveUser extends Method<RemoveUser> {

	public RemoveUser() {
		super("groups.removeUser", AccessTokenType.USER);
	}

	@Override
	public RemoveUserParamsSet getNewParamsSet() {
		return new RemoveUserParamsSet(this);
	}

	public static class RemoveUserParamsSet extends ParamsSet<RemoveUser> {

		private int groupId,userId;
		
		public RemoveUserParamsSet(Method<RemoveUser> method) {
			super(method);
		}

		@Override
		public List<NameValuePair> getParams() {
			ArrayList<NameValuePair> result = new ArrayList<>();
			result.add(new BasicNameValuePair("group_id", String.valueOf(groupId)));
			result.add(new BasicNameValuePair("user_id", String.valueOf(userId)));
			return result;
		}

		public int getGroupId() {
			return groupId;
		}

		public RemoveUserParamsSet setGroupId(int groupId) {
			this.groupId = groupId;
			return this;
		}

		public int getUserId() {
			return userId;
		}

		public RemoveUserParamsSet setUserId(int userId) {
			this.userId = userId;
			return this;
		}
		
	}
}
