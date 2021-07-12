package ru.blc.cutlet.vk.method.board;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import ru.blc.cutlet.vk.AccessToken.AccessTokenType;
import ru.blc.cutlet.vk.method.Method;
import ru.blc.cutlet.vk.method.ParamsSet;
import ru.blc.cutlet.vk.objects.main.TopicComment;
import ru.blc.cutlet.vk.objects.media.Poll;
import ru.blc.objconfig.ConfigurationSection;
import ru.blc.objconfig.json.JsonConfiguration;

import java.util.ArrayList;
import java.util.List;

public class GetComments extends Method<GetComments> {

	public GetComments() {
		super("board.getComments", AccessTokenType.USER);
	}

	@Override
	public GetCommentsParamsSet getNewParamsSet() {
		return new GetCommentsParamsSet(this);
	}

	public static class GetCommentsParamsSet extends ParamsSet<GetComments> {

		private int groupId, topicId;
		private boolean needLikes;
		private Integer startCommentId, offset, count;
		private boolean extended;
		private Sort sort = Sort.DEFAULT;

		public GetCommentsParamsSet(Method<GetComments> method) {
			super(method);
		}

		@Override
		public List<NameValuePair> getParams() {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("group_id", String.valueOf(getGroupId())));
			params.add(new BasicNameValuePair("topic_id", String.valueOf(getTopicId())));	
			if (isNeedLikes()) params.add(new BasicNameValuePair("need_likes", String.valueOf(isNeedLikes())));
			if (this.startCommentId != null)  params.add(new BasicNameValuePair("start_comment_id", String.valueOf(getStartCommentId())));
			if (this.offset != null)  params.add(new BasicNameValuePair("offset", String.valueOf(getOffset())));
			if (this.count != null)  params.add(new BasicNameValuePair("count", String.valueOf(getCount())));
			if (isExtended()) params.add(new BasicNameValuePair("extended",  String.valueOf(isExtended())));
			if (getSort() != Sort.DEFAULT) params.add(new BasicNameValuePair("sort", getSort().name().toLowerCase()));	
			return params;
		}

		public int getGroupId() {
			return groupId;
		}

		public GetCommentsParamsSet setGroupId(int groupId) {
			this.groupId = groupId;
			return this;
		}

		public int getTopicId() {
			return topicId;
		}

		public GetCommentsParamsSet setTopicId(int topicId) {
			this.topicId = topicId;
			return this;
		}

		public boolean isNeedLikes() {
			return needLikes;
		}

		public GetCommentsParamsSet setNeedLikes(boolean needLikes) {
			this.needLikes = needLikes;
			return this;
		}

		public int getStartCommentId() {
			if (startCommentId == null) return 0;
			return startCommentId;
		}

		public GetCommentsParamsSet setStartCommentId(int startCommentId) {
			this.startCommentId = startCommentId;
			return this;
		}

		public int getOffset() {
			if (offset == null) return 0;
			return offset;
		}

		public GetCommentsParamsSet setOffset(int offset) {
			this.offset = offset;
			return this;
		}

		public int getCount() {
			if (count == null) return 20;
			return count;
		}

		public GetCommentsParamsSet setCount(int count) {
			this.count = count;
			return this;
		}

		public boolean isExtended() {
			return extended;
		}

		public GetCommentsParamsSet setExtended(boolean extended) {
			this.extended = extended;
			return this;
		}

		public Sort getSort() {
			return sort;
		}

		public GetCommentsParamsSet setSort(Sort sort) {
			this.sort = sort;
			return this;
		}

		public enum Sort {
			ASC, DESC, DEFAULT;
		}
	}

	public static class GetCommentsResponse {

		public static GetCommentsResponse load(String json) {
			return load(JsonConfiguration.loadConfiguration(json));
		}

		public static GetCommentsResponse load(JsonConfiguration json) {
			if (!json.hasValue("response")) return null;
			return new GetCommentsResponse(json.getConfigurationSection("response"));
		}
		
		private int count;
		private List<TopicComment> items;
		private Poll poll;
		//private List<User> profiles; TODO
		
		public GetCommentsResponse(ConfigurationSection config) {
			this.count = config.getInt("count");
			this.items = new ArrayList<>();
			for (ConfigurationSection section : config.getConfigurationSectionList("items")) {
				items.add(TopicComment.load(section));
			}
			if (config.hasValue("poll")) this.poll = new Poll(config.getConfigurationSection("poll"));
		}
		
		public int getCount() {
			return count;
		}
		
		public List<TopicComment> getItems() {
			return items;
		}
		
		public Poll getPoll() {
			return poll;
		}
	}
}
