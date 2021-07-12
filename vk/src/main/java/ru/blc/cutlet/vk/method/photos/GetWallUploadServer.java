package ru.blc.cutlet.vk.method.photos;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import ru.blc.cutlet.vk.AccessToken.AccessTokenType;
import ru.blc.cutlet.vk.method.Method;
import ru.blc.cutlet.vk.method.ParamsSet;

import java.util.ArrayList;
import java.util.List;

public class GetWallUploadServer extends Method<GetWallUploadServer> {

	public GetWallUploadServer() {
		super("photos.getWallUploadServer", AccessTokenType.USER);
	}

	@Override
	public GetWallUploadServerParamsSet getNewParamsSet() {
		return new GetWallUploadServerParamsSet(this);
	}
	
	
	public static class GetWallUploadServerParamsSet extends ParamsSet<GetWallUploadServer> {

		private int groupId;
		
		public GetWallUploadServerParamsSet(Method<GetWallUploadServer> method) {
			super(method);
		}

		@Override
		public List<NameValuePair> getParams() {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("group_id", String.valueOf(getGroupId())));
			return params;
		}
		
		
		public int getGroupId() {
			return groupId;
		}
		
		public GetWallUploadServerParamsSet setGroupId(int groupId) {
			this.groupId = groupId;
			return this;
		}
	}

}
