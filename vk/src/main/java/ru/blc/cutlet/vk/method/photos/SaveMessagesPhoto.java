package ru.blc.cutlet.vk.method.photos;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import ru.blc.cutlet.vk.AccessToken.AccessTokenType;
import ru.blc.cutlet.vk.method.Method;
import ru.blc.cutlet.vk.method.ParamsSet;

import java.util.ArrayList;
import java.util.List;

public class SaveMessagesPhoto extends Method<SaveMessagesPhoto> {

	public SaveMessagesPhoto() {
		super("photos.saveMessagesPhoto", AccessTokenType.USER, AccessTokenType.GROUP);
	}

	@Override
	public SaveMessagesPhotoParamsSet getNewParamsSet() {
		return new SaveMessagesPhotoParamsSet(this);
	}
	
	public static class SaveMessagesPhotoParamsSet extends ParamsSet<SaveMessagesPhoto> {

		private int server;
		private String photo, hash;
		
		public SaveMessagesPhotoParamsSet(Method<SaveMessagesPhoto> method) {
			super(method);
		}

		@Override
		public List<NameValuePair> getParams() {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("server", String.valueOf(getServer())));
			if(getPhoto()!=null) params.add(new BasicNameValuePair("photo", getPhoto()));
			if(getHash()!=null) params.add(new BasicNameValuePair("hash", getHash()));
			return params;
		}
		
		public int getServer() {
			return server;
		}
		
		public SaveMessagesPhotoParamsSet setServer(int server) {
			this.server = server;
			return this;
		}
		
		public String getPhoto() {
			return photo;
		}
		
		public SaveMessagesPhotoParamsSet setPhoto(String photo) {
			this.photo = photo;
			return this;
		}
		
		public String getHash() {
			return hash;
		}
		
		public SaveMessagesPhotoParamsSet setHash(String hash) {
			this.hash = hash;
			return this;
		}
	}
}
