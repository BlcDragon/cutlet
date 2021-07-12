package ru.blc.cutlet.vk.method.messages;

import com.google.common.base.Preconditions;
import lombok.Getter;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import ru.blc.cutlet.vk.AccessToken.AccessTokenType;
import ru.blc.cutlet.vk.method.Method;
import ru.blc.cutlet.vk.method.ParamsSet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SendMessageEventAnswer extends Method<SendMessageEventAnswer> {

	public static final Set<String> notAnsweredData = new HashSet<>();

	public SendMessageEventAnswer() {
		super("messages.sendMessageEventAnswer", AccessTokenType.GROUP);
	}

	@Override
	public SendMessageEventAnswerParamsSet getNewParamsSet() {
		return new SendMessageEventAnswerParamsSet(this);
	}

	public static class SendMessageEventAnswerParamsSet extends ParamsSet<SendMessageEventAnswer> {

		public SendMessageEventAnswerParamsSet(Method<SendMessageEventAnswer> method) {
			super(method);
		}

		@Getter
		private String eventId;
		@Getter
		private int userId, peerId;
		@Getter
		private String eventData;
		
		@Override
		public List<NameValuePair> getParams() {
			List<NameValuePair> params = new ArrayList<>();
			Preconditions.checkNotNull(getEventId(), "event_id");
			Preconditions.checkArgument(getUserId()>0, "user_id");
			Preconditions.checkArgument(getPeerId()>0, "peer_id");
			params.add(new BasicNameValuePair("event_id", getEventId()));
			params.add(new BasicNameValuePair("user_id", String.valueOf(getUserId())));
			params.add(new BasicNameValuePair("peer_id", String.valueOf(getPeerId())));
			if (getEventData()!=null)params.add(new BasicNameValuePair("event_data", getEventData()));
			return params;
		}

		public SendMessageEventAnswerParamsSet setEventId(String eventId) {
			this.eventId = eventId;
			return this;
		}

		public SendMessageEventAnswerParamsSet setUserId(int userId) {
			this.userId = userId;
			return this;
		}

		public SendMessageEventAnswerParamsSet setPeerId(int peerId) {
			this.peerId = peerId;
			return this;
		}

		public SendMessageEventAnswerParamsSet setEventData(String eventData) {
			this.eventData = eventData;
			return this;
		}
	}
}
