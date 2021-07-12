package ru.blc.cutlet.vk.method.messages;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import ru.blc.cutlet.vk.AccessToken.AccessTokenType;
import ru.blc.cutlet.vk.method.Method;
import ru.blc.cutlet.vk.method.ParamsSet;
import ru.blc.cutlet.vk.objects.main.keyboard.Keyboard;
import ru.blc.cutlet.vk.objects.media.Attachment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Send extends Method<Send> {

	public Send() {
		super("messages.send", AccessTokenType.USER, AccessTokenType.GROUP);
	}

	@Override
	public SendParamsSet getNewParamsSet() {
		return new SendParamsSet(this);
	}

	public static class SendParamsSet extends ParamsSet<Send> {

		public SendParamsSet(Method<Send> method) {
			super(method);
		}

		private Integer userId;
		private long randomId;
		private Integer peerId;
		private Integer[] peerIds;
		private String domain;
		private Integer chatId;
		private int[] userIds;
		private String message;
		private Double lat;
		private Double longg;
		private List<String> attachments = new ArrayList<>();
		private Integer replyTo;
		private int[] forwardMessages;
		private Integer stickerId;
		private Keyboard keyboard;
		private String payload;
		private Boolean parseLinks;
		private Boolean disableMentions;

		@Override
		public List<NameValuePair> getParams() {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			if (getUserId() != null) params.add(new BasicNameValuePair("user_id", String.valueOf(getUserId())));
			params.add(new BasicNameValuePair("random_id", String.valueOf(getRandomId())));
			if (getPeerId() != null) params.add(new BasicNameValuePair("peer_id", String.valueOf(getPeerId())));
			if (getPeerIds() != null) params.add(new BasicNameValuePair("peer_ids", collectionToString(Arrays.asList(getPeerIds()))));
			if (getDomain() != null) params.add(new BasicNameValuePair("domain", getDomain()));
			if (getChatId() != null) params.add(new BasicNameValuePair("chat_id", String.valueOf(getChatId())));
			if (getUserIds() != null) params.add(new BasicNameValuePair("user_ids", collectionToString(Arrays.asList(getUserIds()))));
			if (getMessage() != null) params.add(new BasicNameValuePair("message", getMessage()));
			if (getLat() != null && getLong() != null) {
				params.add(new BasicNameValuePair("lat", String.valueOf(getLat())));
				params.add(new BasicNameValuePair("long", String.valueOf(getLong())));
			}
			if (getAttachments() != null && !getAttachments().isEmpty()) params.add(new BasicNameValuePair("attachment", collectionToString(getAttachments())));
			if (getReplyTo() != null) params.add(new BasicNameValuePair("reply_to", String.valueOf(getReplyTo())));
			if (getForwardMessages() != null)
				params.add(new BasicNameValuePair("forward_messages", collectionToString(Arrays.asList(getForwardMessages()))));
			if (getStickerId() != null) params.add(new BasicNameValuePair("sticker_id", String.valueOf(getStickerId())));
			if (getKeyboard() != null) params.add(new BasicNameValuePair("keyboard", getKeyboard().toJson()));
			if (getPayload() != null) params.add(new BasicNameValuePair("payload", getPayload()));
			if (getParseLinks() != null) params.add(new BasicNameValuePair("dont_parse_links", booleanToIntString(!getParseLinks())));
			if (isDisableMentions() != null) params.add(new BasicNameValuePair("disable_mentions", booleanToIntString(isDisableMentions())));
			return params;
		}

		@Override
		public CompletableFuture<String> call(Header... headers) {
			if(getMessage()!=null && getMessage().length()>2000){
				return CompletableFuture.supplyAsync(() -> {
					String[] msgs = getMessage().split("(?<=\\G(?s).{1800,2000}\\n)");
					for (int i = 0; i < msgs.length-1; i++) {
						String msg = msgs[i];
						setMessage(msg);
						super.callAwait();
					}
					setMessage(msgs[msgs.length-1]);
					return super.callAwait();
				});
			}
			return super.call(headers);
		}

		public Integer getUserId() {
			return userId;
		}

		public SendParamsSet setUserId(Integer userId) {
			this.userId = userId;
			return this;
		}

		public long getRandomId() {
			return randomId;
		}

		public SendParamsSet setRandomId(long randomId) {
			this.randomId = randomId;
			return this;
		}

		public Integer getPeerId() {
			return peerId;
		}

		public SendParamsSet setPeerId(Integer peerId) {
			this.peerId = peerId;
			return this;
		}

		public Integer[] getPeerIds() {
			return peerIds;
		}

		public SendParamsSet setPeerIds(Integer... peerIds) {
			this.peerIds = peerIds;
			return this;
		}

		public String getDomain() {
			return domain;
		}

		public SendParamsSet setDomain(String domain) {
			this.domain = domain;
			return this;
		}

		public Integer getChatId() {
			return chatId;
		}

		public SendParamsSet setChatId(Integer chatId) {
			this.chatId = chatId;
			return this;
		}

		public int[] getUserIds() {
			return userIds;
		}

		public SendParamsSet setUserIds(int[] userIds) {
			this.userIds = userIds;
			return this;
		}

		public String getMessage() {
			return message;
		}

		public SendParamsSet setMessage(String message) {
			this.message = message;
			return this;
		}

		public Double getLat() {
			return lat;
		}

		public SendParamsSet setLat(Double lat) {
			this.lat = lat;
			return this;
		}

		public Double getLong() {
			return longg;
		}

		public SendParamsSet setLong(Double longg) {
			this.longg = longg;
			return this;
		}

		public List<String> getAttachments() {
			return attachments;
		}

		public SendParamsSet addAttachment(String attachment) {
			if ((attachment==null)||attachment.isEmpty()) return this;
			attachments.add(attachment);
			return this;
		}		

		public SendParamsSet addAttachment(Attachment attachment) {
			return addAttachment(Attachment.parse(attachment));
		}
		
		public SendParamsSet addAllAttachments(Collection<Attachment> attachment) {
			for (Attachment a : attachment) addAttachment(a);
			return this;
		}
		
		public SendParamsSet removeAttachment(String attachment) {
			if ((attachment==null)||attachment.isEmpty()) return this;
			attachments.remove(attachment);		
			return this;
		}		

		public SendParamsSet removeAttachment(Attachment attachment) {
			return removeAttachment(Attachment.parse(attachment));
		}

		public SendParamsSet setAttachments(List<String> attachments) {
			this.attachments = attachments;
			return this;
		}

		public Integer getReplyTo() {
			return replyTo;
		}

		public SendParamsSet setReplyTo(Integer replyTo) {
			this.replyTo = replyTo;
			return this;
		}

		public int[] getForwardMessages() {
			return forwardMessages;
		}

		public SendParamsSet setForwardMessages(int[] forwardMessages) {
			this.forwardMessages = forwardMessages;
			return this;
		}

		public Integer getStickerId() {
			return stickerId;
		}

		public SendParamsSet setStickerId(Integer stickerId) {
			this.stickerId = stickerId;
			return this;
		}

		public Keyboard getKeyboard() {
			return keyboard;
		}

		public SendParamsSet setKeyboard(Keyboard keyboard) {
			this.keyboard = keyboard;
			return this;
		}

		public String getPayload() {
			return payload;
		}

		public SendParamsSet setPayload(String payload) {
			this.payload = payload;
			return this;
		}

		public Boolean getParseLinks() {
			return parseLinks;
		}

		public SendParamsSet setParseLinks(Boolean parseLinks) {
			this.parseLinks = parseLinks;
			return this;
		}

		public Boolean isDisableMentions() {
			return disableMentions;
		}

		public SendParamsSet setDisableMentions(Boolean disable) {
			this.disableMentions = disable;
			return this;
		}
	}
}
