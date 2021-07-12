package ru.blc.cutlet.vk.method.photos;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import ru.blc.cutlet.vk.AccessToken.AccessTokenType;
import ru.blc.cutlet.vk.method.Method;
import ru.blc.cutlet.vk.method.ParamsSet;

import java.util.ArrayList;
import java.util.List;

public class SaveWallPhoto extends Method<SaveWallPhoto> {

	public SaveWallPhoto() {
		super("photos.saveWallPhoto", AccessTokenType.USER);
	}

	@Override
	public SaveWallPhotoParamsSet getNewParamsSet() {
		return new SaveWallPhotoParamsSet(this);
	}
	
	public static class SaveWallPhotoParamsSet extends ParamsSet<SaveWallPhoto> {

		private int groupId, userId, server;
		private String photo, hash, caption;
		private double latitude, longitude;
		
		public SaveWallPhotoParamsSet(Method<SaveWallPhoto> method) {
			super(method);
		}

		@Override
		public List<NameValuePair> getParams() {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("group_id", String.valueOf(getGroupId())));
			params.add(new BasicNameValuePair("user_id", String.valueOf(getUserId())));
			params.add(new BasicNameValuePair("server", String.valueOf(getServer())));
			if(getPhoto()!=null) params.add(new BasicNameValuePair("photo", getPhoto()));
			if(getHash()!=null) params.add(new BasicNameValuePair("hash", getHash()));
			if(getCaption()!=null) params.add(new BasicNameValuePair("caption", getCaption()));
			return params;
		}
				
		public int getGroupId() {
			return groupId;
		}
		
		public SaveWallPhotoParamsSet setGroupId(int groupId) {
			this.groupId = groupId;
			return this;
		}
		
		public int getUserId() {
			return userId;
		}
		
		public SaveWallPhotoParamsSet setUserId(int userId) {
			this.userId = userId;
			return this;
		}
		
		public int getServer() {
			return server;
		}
		
		public SaveWallPhotoParamsSet setServer(int server) {
			this.server = server;
			return this;
		}
		
		public String getPhoto() {
			return photo;
		}
		
		public SaveWallPhotoParamsSet setPhoto(String photo) {
			this.photo = photo;
			return this;
		}
		
		public String getHash() {
			return hash;
		}
		
		public SaveWallPhotoParamsSet setHash(String hash) {
			this.hash = hash;
			return this;
		}
		
		public String getCaption() {
			return caption;
		}
		
		public SaveWallPhotoParamsSet setCaption(String caption) {
			this.caption = caption;
			return this;
		}
		
		public double getLatitude() {
			return latitude;
		}
		
		public SaveWallPhotoParamsSet setLatitude(double latitude) {
			this.latitude = latitude;
			return this;
		}
		
		public double getLongitude() {
			return longitude;
		}
		
		public SaveWallPhotoParamsSet setLongitude(double longitude) {
			this.longitude = longitude;
			return this;
		}
	}
}
