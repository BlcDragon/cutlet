package ru.blc.cutlet.vk.method.messages;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import ru.blc.cutlet.vk.AccessToken.AccessTokenType;
import ru.blc.cutlet.vk.method.Method;
import ru.blc.cutlet.vk.method.ParamsSet;
import ru.blc.cutlet.vk.objects.main.keyboard.Keyboard;
import ru.blc.cutlet.vk.objects.media.Attachment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Edit extends Method<Edit> {

	public Edit() {
		super("messages.edit", AccessTokenType.USER, AccessTokenType.GROUP);
	}

	@Override
	public EditParamsSet getNewParamsSet() {
		return new EditParamsSet(this);
	}

	public static class EditParamsSet extends ParamsSet<Edit> {

		public EditParamsSet(Method<Edit> method) {
			super(method);
		}

		private Integer peerId, messageId, conversationMessageId;
		private String message;
		private Double lat;
		private Double longg;
		private List<String> attachments = new ArrayList<>();
		private Keyboard keyboard;
		private Boolean dontParseLinks, keepForwardMessages, keepSnippets;

		@Override
		public List<NameValuePair> getParams() {
			List<NameValuePair> params = new ArrayList<>();
			if (getPeerId() != null) params.add(new BasicNameValuePair("peer_id", String.valueOf(getPeerId())));
			if (getMessageId() != null) params.add(new BasicNameValuePair("message_id", String.valueOf(getMessageId())));
			if (getConversationMessageId() != null) params.add(new BasicNameValuePair("conversation_message_id", String.valueOf(getConversationMessageId())));
			if (getMessage() != null) params.add(new BasicNameValuePair("message", getMessage()));
			if (getLat() != null && getLong() != null) {
				params.add(new BasicNameValuePair("lat", String.valueOf(getLat())));
				params.add(new BasicNameValuePair("long", String.valueOf(getLong())));
			}
			if (getAttachments() != null) params.add(new BasicNameValuePair("attachment", collectionToString(getAttachments())));
			if (getDontParseLinks() != null) params.add(new BasicNameValuePair("dont_parse_links", String.valueOf(getDontParseLinks())));
			if (getKeepForwardMessages() != null) params.add(new BasicNameValuePair("keep_forward_messages", String.valueOf(getKeepForwardMessages())));
			if (getKeepSnippets() != null) params.add(new BasicNameValuePair("keep_snippets", String.valueOf(getKeepSnippets())));
			if (getKeyboard() != null) params.add(new BasicNameValuePair("keyboard", getKeyboard().toJson()));
			return params;
		}

		public Integer getPeerId() {
			return peerId;
		}

		public EditParamsSet setPeerId(Integer peerId) {
			this.peerId = peerId;
			return this;
		}

		public Integer getMessageId() {
			return messageId;
		}

		public EditParamsSet setMessageId(Integer messageId) {
			this.messageId = messageId;
			return this;
		}

		public Integer getConversationMessageId() {
			return conversationMessageId;
		}

		public EditParamsSet setConversationMessageId(Integer conversationMessageId) {
			this.conversationMessageId = conversationMessageId;
			return this;
		}

		public String getMessage() {
			return message;
		}

		public EditParamsSet setMessage(String message) {
			this.message = message;
			return this;
		}

		public Double getLat() {
			return lat;
		}

		public EditParamsSet setLat(Double lat) {
			this.lat = lat;
			return this;
		}

		public Double getLong() {
			return longg;
		}

		public EditParamsSet setLong(Double longg) {
			this.longg = longg;
			return this;
		}

		public List<String> getAttachments() {
			return attachments;
		}

		public EditParamsSet addAttachment(String attachment) {
			if ((attachment==null)||attachment.isEmpty()) return this;
			attachments.add(attachment);
			return this;
		}		

		public EditParamsSet addAttachment(Attachment attachment) {
			return addAttachment(Attachment.parse(attachment));
		}
		
		public EditParamsSet addAllAttachments(Collection<Attachment> attachment) {
			for (Attachment a : attachment) addAttachment(a);
			return this;
		}
		
		public EditParamsSet removeAttachment(String attachment) {
			if ((attachment==null)||attachment.isEmpty()) return this;
			attachments.remove(attachment);		
			return this;
		}		

		public EditParamsSet removeAttachment(Attachment attachment) {
			return removeAttachment(Attachment.parse(attachment));
		}

		public EditParamsSet setAttachments(List<String> attachments) {
			this.attachments = attachments;
			return this;
		}

		public Keyboard getKeyboard() {
			return keyboard;
		}

		public EditParamsSet setKeyboard(Keyboard keyboard) {
			this.keyboard = keyboard;
			return this;
		}

		public Boolean getDontParseLinks() {
			return dontParseLinks;
		}

		public EditParamsSet setDontParseLinks(Boolean dontParseLinks) {
			this.dontParseLinks = dontParseLinks;
			return this;
		}

		public Boolean getKeepForwardMessages() {
			return keepForwardMessages;
		}

		public EditParamsSet setKeepForwardMessages(Boolean keepForwardMessages) {
			this.keepForwardMessages = keepForwardMessages;
			return this;
		}

		public Boolean getKeepSnippets() {
			return keepSnippets;
		}

		public EditParamsSet setKeepSnippets(Boolean keepSnippets) {
			this.keepSnippets = keepSnippets;
			return this;
		}
	}
}
