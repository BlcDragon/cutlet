package ru.blc.cutlet.vk.method.photos;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import ru.blc.cutlet.vk.AccessToken.AccessTokenType;
import ru.blc.cutlet.vk.method.Method;
import ru.blc.cutlet.vk.method.ParamsSet;

import java.util.ArrayList;
import java.util.List;

public class GetMessagesUploadServer extends Method<GetMessagesUploadServer> {

	public GetMessagesUploadServer() {
		super("photos.getMessagesUploadServer", AccessTokenType.USER, AccessTokenType.GROUP);
	}

	@Override
	public GetMessagesUploadServerParamsSet getNewParamsSet() {
		return new GetMessagesUploadServerParamsSet(this);
	}
	
	
	public static class GetMessagesUploadServerParamsSet extends ParamsSet<GetMessagesUploadServer> {

		private int peer_id;
		
		public GetMessagesUploadServerParamsSet(Method<GetMessagesUploadServer> method) {
			super(method);
		}

		@Override
		public List<NameValuePair> getParams() {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("group_id", String.valueOf(getPeerId())));
			return params;
		}
		
		
		public int getPeerId() {
			return peer_id;
		}
		
		public GetMessagesUploadServerParamsSet setPeerId(int peerId) {
			this.peer_id = peerId;
			return this;
		}
	}

}
