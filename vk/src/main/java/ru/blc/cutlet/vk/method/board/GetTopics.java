package ru.blc.cutlet.vk.method.board;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import ru.blc.cutlet.vk.AccessToken.AccessTokenType;
import ru.blc.cutlet.vk.method.Method;
import ru.blc.cutlet.vk.method.ParamsSet;
import ru.blc.cutlet.vk.objects.main.Topic;
import ru.blc.objconfig.ConfigurationSection;
import ru.blc.objconfig.json.JsonConfiguration;

import java.util.ArrayList;
import java.util.List;

public class GetTopics extends Method<GetTopics> {

	public GetTopics() {
		super("board.getTopics", AccessTokenType.USER);
	}

	@Override
	public GetTopicsParamsSet getNewParamsSet() {
		return new GetTopicsParamsSet(this);
	}

	public static class GetTopicsParamsSet extends ParamsSet<GetTopics>{
		
		private int groupId;
		private List<Integer> topicIds;
		private Integer order, offset, count;
		private boolean extended;
		private Integer preview, previewLength;

		public GetTopicsParamsSet(Method<GetTopics> method) {
			super(method);
		}
		
		@Override
		public List<NameValuePair> getParams() {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("group_id", String.valueOf(getGroupId())));
			if ((getTopicIds()!=null)&&(!getTopicIds().isEmpty())) params.add(new BasicNameValuePair("topic_ids", collectionToString(getTopicIds())));
			if (this.order!=null) params.add(new BasicNameValuePair("order", String.valueOf(getOrder())));
			if (this.offset != null) params.add(new BasicNameValuePair("offset", String.valueOf(getOffset())));
			if (this.count != null) params.add(new BasicNameValuePair("count", String.valueOf(getCount())));
			if (isExtended()) params.add(new BasicNameValuePair("extended", String.valueOf(isExtended())));
			if (this.preview != null) params.add(new BasicNameValuePair("preview", String.valueOf(getPreview())));
			if (this.previewLength != null) params.add(new BasicNameValuePair("preview_length", String.valueOf(getPreviewLength())));			
			return params;
		}

		public int getGroupId() {
			return groupId;
		}

		public GetTopicsParamsSet setGroupId(int groupId) {
			this.groupId = groupId;
			return this;
		}

		public List<Integer> getTopicIds() {
			return topicIds;
		}

		public GetTopicsParamsSet setTopicIds(List<Integer> topicIds) {
			this.topicIds = topicIds;
			return this;
		}
		
		public GetTopicsParamsSet addTopicId(int id) {
			this.topicIds.add(id);
			return this;
		}
		
		public GetTopicsParamsSet removeTopicId(int id) {
			this.topicIds.remove(id);
			return this;
		}

		public int getOrder() {
			return order;
		}

		public GetTopicsParamsSet setOrder(int order) {
			this.order = order;
			return this;
		}

		public int getOffset() {
			return offset;
		}

		public GetTopicsParamsSet setOffset(int offset) {
			this.offset = offset;
			return this;
		}

		public int getCount() {
			return count;
		}

		public GetTopicsParamsSet setCount(int count) {
			this.count = count;
			return this;
		}

		public boolean isExtended() {
			return extended;
		}

		public GetTopicsParamsSet setExtended(boolean extended) {
			this.extended = extended;
			return this;
		}

		public int getPreview() {
			return preview;
		}

		public GetTopicsParamsSet setPreview(int preview) {
			this.preview = preview;
			return this;
		}

		public int getPreviewLength() {
			return previewLength;
		}

		public GetTopicsParamsSet setPreviewLength(int previewLength) {
			this.previewLength = previewLength;
			return this;
		}
		
	}
	
	public static class GetTopicsResponse {
		
		public static GetTopicsResponse load(String json) {
			return load(JsonConfiguration.loadConfiguration(json));
		}

		public static GetTopicsResponse load(JsonConfiguration json) {
			if (!json.hasValue("response")) return null;
			return new GetTopicsResponse(json.getConfigurationSection("response"));
		}
		
		private final int count;
		private final List<Topic> items;
		private final int defaultOrder;
		private final boolean canAddTopics;
		//private List<User> profiles; TODO
		
		public GetTopicsResponse(ConfigurationSection config) {
			this.count = config.getInt("count");
			this.items = new ArrayList<>();
			for (ConfigurationSection section : config.getConfigurationSectionList("items")) {
				items.add(Topic.load(section));
			}
			this.defaultOrder = config.getInt("default_order");
			this.canAddTopics = config.getInt("can_add_topics")==1;
		}
		
		public int getCount() {
			return count;
		}
		
		public List<Topic> getItems() {
			return items;
		}
		
		public int getDefaultOrder() {
			return defaultOrder;
		}
		
		public boolean canAddTopics() {
			return canAddTopics;
		}
	}
}
