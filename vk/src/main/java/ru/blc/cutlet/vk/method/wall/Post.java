package ru.blc.cutlet.vk.method.wall;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import ru.blc.cutlet.vk.AccessToken.AccessTokenType;
import ru.blc.cutlet.vk.method.Method;
import ru.blc.cutlet.vk.method.ParamsSet;
import ru.blc.cutlet.vk.objects.media.Attachment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Post extends Method<Post> {

	public Post() {
		super("wall.post", AccessTokenType.USER);
	}

	@Override
	public PostParamsSet getNewParamsSet() {
		return new PostParamsSet(this);
	}
	
	public static class PostParamsSet extends ParamsSet<Post> {

		private int ownerId;
		private Boolean friedsOnly, fromGroup;
		private String message;
		private List<String> attachments = new ArrayList<>();
		private List<String> services = new ArrayList<>();
		private Boolean signed;
		private Integer publishDate;
		private Double lat,longg;
		private Integer placeId;
		private Integer postId;
		private String guid;
		private Boolean markAsAds, closeComments;
		
		public PostParamsSet(Method<Post> method) {
			super(method);
		}

		@Override
		public List<NameValuePair> getParams() {
			ArrayList<NameValuePair> result = new ArrayList<>();
			result.add(new BasicNameValuePair("owner_id", String.valueOf(getOwnerId())));
			if (friedsOnly!=null) result.add(new BasicNameValuePair("friends_only", String.valueOf(isFriedsOnly())));
			if (fromGroup!=null) result.add(new BasicNameValuePair("from_group", String.valueOf(isFromGroup())));
			if (getMessage()!=null) result.add(new BasicNameValuePair("message", getMessage()));
			if (!attachments.isEmpty()) result.add(new BasicNameValuePair("attachments", collectionToString(attachments)));
			if (!services.isEmpty()) result.add(new BasicNameValuePair("services", collectionToString(services)));
			if (signed!=null) result.add(new BasicNameValuePair("signed", String.valueOf(isSigned())));
			if (publishDate!=null)result.add(new BasicNameValuePair("publish_date", String.valueOf(getPublishDate())));
			if (lat!=null)result.add(new BasicNameValuePair("lat", String.valueOf(getLat())));
			if (longg!=null)result.add(new BasicNameValuePair("long", String.valueOf(getLong())));
			if (placeId!=null)result.add(new BasicNameValuePair("place_id", String.valueOf(getPlaceId())));
			if (postId!=null)result.add(new BasicNameValuePair("post_id", String.valueOf(getPostId())));
			if (getGuid()!=null) result.add(new BasicNameValuePair("guid", getGuid()));
			if (markAsAds!=null)result.add(new BasicNameValuePair("mark_as_ads", String.valueOf(isMarkAsAds())));
			if (closeComments!=null)result.add(new BasicNameValuePair("close_comments", String.valueOf(isClosedComments())));
			return result;
		}

		public int getOwnerId() {
			return ownerId;
		}

		public PostParamsSet setOwnerId(int ownerId) {
			this.ownerId = ownerId;
			return this;
		}

		public boolean isFriedsOnly() {
			return friedsOnly;
		}

		public PostParamsSet setFriedsOnly(boolean friedsOnly) {
			this.friedsOnly = friedsOnly;
			return this;
		}

		public boolean isFromGroup() {
			return fromGroup;
		}

		public PostParamsSet setFromGroup(boolean fromGroup) {
			this.fromGroup = fromGroup;
			return this;
		}

		public String getMessage() {
			return message;
		}

		public PostParamsSet setMessage(String message) {
			this.message = message;
			return this;
		}

		public List<String> getAttachments() {
			return attachments;
		}

		public PostParamsSet addAttachment(String attachment) {
			if ((attachment==null)||attachment.isEmpty()) return this;
			attachments.add(attachment);
			return this;
		}		

		public PostParamsSet addAttachment(Attachment attachment) {
			return addAttachment(Attachment.parse(attachment));
		}
		
		public PostParamsSet addAllAttachments(Collection<Attachment> attachment) {
			for (Attachment a : attachment) addAttachment(a);
			return this;
		}
		
		public PostParamsSet removeAttachment(String attachment) {
			if ((attachment==null)||attachment.isEmpty()) return this;
			attachments.remove(attachment);		
			return this;
		}		

		public PostParamsSet removeAttachment(Attachment attachment) {
			return removeAttachment(Attachment.parse(attachment));
		}

		public PostParamsSet setAttachments(List<String> attachments) {
			this.attachments = attachments;
			return this;
		}

		public List<String> getServices() {
			return services;
		}

		public PostParamsSet setServices(List<String> services) {
			this.services = services;
			return this;
		}

		public boolean isSigned() {
			return signed;
		}

		public PostParamsSet setSigned(boolean signed) {
			this.signed = signed;
			return this;
		}

		public int getPublishDate() {
			return publishDate;
		}

		public PostParamsSet setPublishDate(int publishDate) {
			this.publishDate = publishDate;
			return this;
		}

		public double getLat() {
			return lat;
		}

		public PostParamsSet setLat(double lat) {
			this.lat = lat;
			return this;
		}

		public double getLong() {
			return longg;
		}

		public PostParamsSet setLong(double longg) {
			this.longg = longg;
			return this;
		}

		public int getPlaceId() {
			return placeId;
		}

		public PostParamsSet setPlaceId(int placeId) {
			this.placeId = placeId;
			return this;
		}

		public int getPostId() {
			return postId;
		}

		public PostParamsSet setPostId(int postId) {
			this.postId = postId;
			return this;
		}

		public String getGuid() {
			return guid;
		}

		public PostParamsSet setGuid(String guid) {
			this.guid = guid;
			return this;
		}

		public boolean isMarkAsAds() {
			return markAsAds;
		}

		public PostParamsSet setMarkAsAds(boolean markAsAds) {
			this.markAsAds = markAsAds;
			return this;
		}

		public boolean isClosedComments() {
			return closeComments;
		}

		public PostParamsSet setCloseComments(boolean closeComments) {
			this.closeComments = closeComments;
			return this;
		}
	}

}
