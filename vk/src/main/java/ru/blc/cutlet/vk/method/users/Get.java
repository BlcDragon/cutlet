package ru.blc.cutlet.vk.method.users;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import ru.blc.cutlet.vk.AccessToken.AccessTokenType;
import ru.blc.cutlet.vk.method.Method;
import ru.blc.cutlet.vk.method.ParamsSet;

import java.util.ArrayList;
import java.util.List;

public class Get extends Method<Get> {

	public Get() {
		super("users.get", AccessTokenType.SERVICE, AccessTokenType.GROUP, AccessTokenType.USER);
	}

	@Override
	public GetParamsSet getNewParamsSet() {
		return new GetParamsSet(this);
	}

	public static class GetParamsSet extends ParamsSet<Get> {

		private List<String> userIds = new ArrayList<>();
		private List<String> fields = new ArrayList<>();
		private NameCase nameCase;

		public GetParamsSet(Method<Get> method) {
			super(method);
		}

		@Override
		public List<NameValuePair> getParams() {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("user_ids", collectionToString(getUserIds())));
			if (!getFields().isEmpty()) params.add(new BasicNameValuePair("fields", collectionToString(getFields())));
			if (getNameCase() != null) params.add(new BasicNameValuePair("name_case", getNameCase().name().toLowerCase()));
			return params;
		}

		public List<String> getUserIds() {
			return userIds;
		}

		public GetParamsSet addUserId(int userId) {
			return addUserId(String.valueOf(userId));
		}

		public GetParamsSet addUserId(String userId) {
			userIds.add(userId);
			return this;
		}

		public GetParamsSet removeUserId(int userId) {
			return removeUserId(String.valueOf(userId));
		}

		public GetParamsSet removeUserId(String userId) {
			userIds.remove(userId);
			return this;
		}

		public List<String> getFields() {
			return fields;
		}

		public GetParamsSet addField(String field) {
			fields.add(field);
			return this;
		}

		public GetParamsSet removeField(String field) {
			fields.remove(field);
			return this;
		}

		public NameCase getNameCase() {
			return nameCase;
		}

		public GetParamsSet setNameCase(NameCase nameCase) {
			this.nameCase = nameCase;
			return this;
		}

	}

	public enum NameCase {
		NOM, GET, DAT, ACCINS, ABL
	}
}
