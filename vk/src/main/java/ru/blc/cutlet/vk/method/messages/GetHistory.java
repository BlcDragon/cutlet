package ru.blc.cutlet.vk.method.messages;

import com.google.common.base.Preconditions;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import ru.blc.cutlet.vk.AccessToken;
import ru.blc.cutlet.vk.method.Method;
import ru.blc.cutlet.vk.method.ParamsSet;

import java.util.ArrayList;
import java.util.List;

public class GetHistory extends Method<GetHistory> {

    public GetHistory() {
        super("messages.getHistory", AccessToken.AccessTokenType.USER, AccessToken.AccessTokenType.GROUP);
    }

    @Override
    public GetHistoryParamSet getNewParamsSet() {
        return new GetHistoryParamSet(this);
    }

    public static class GetHistoryParamSet extends ParamsSet<GetHistory>{

        private int offset;
        private int count = 20;
        private Integer userId;
        private Integer peerId;
        private boolean extended = false;
        private int reverse = 0;
        private int startMessageId = 0;
        private final List<String> fields = new ArrayList<>();
        private int groupId = 0;

        public GetHistoryParamSet(Method<GetHistory> method) {
            super(method);
        }

        @Override
        public List<NameValuePair> getParams() {
            List<NameValuePair> params = new ArrayList<>();
            if (getOffset()!=0) params.add(new BasicNameValuePair("offset", String.valueOf(getOffset())));
            if (getCount()!=20) params.add(new BasicNameValuePair("count", String.valueOf(getCount())));
            if (getUserId() != null) params.add(new BasicNameValuePair("user_id", String.valueOf(getUserId())));
            if (getPeerId() != null) params.add(new BasicNameValuePair("peer_id", String.valueOf(getPeerId())));
            if (isExtended()) params.add(new BasicNameValuePair("extended", booleanToIntString(isExtended())));
            if (getReverse()!=0) params.add(new BasicNameValuePair("rev", String.valueOf(getReverse())));
            if (getStartMessageId()!=0) params.add(new BasicNameValuePair("start_message_id", String.valueOf(getStartMessageId())));
            if (!getFields().isEmpty()) params.add(new BasicNameValuePair("fields", collectionToString(getFields())));
            if (getGroupId()!=0) params.add(new BasicNameValuePair("group_id", String.valueOf(getGroupId())));
            return params;
        }

        public int getOffset() {
            return offset;
        }

        public GetHistoryParamSet setOffset(int offset) {
            this.offset = offset;
            return this;
        }

        public int getCount() {
            return count;
        }

        public GetHistoryParamSet setCount(int count) {
            Preconditions.checkArgument(count<=200, "200 is max count");
            this.count = count;
            return this;
        }

        public Integer getUserId() {
            return userId;
        }

        public GetHistoryParamSet setUserId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public Integer getPeerId() {
            return peerId;
        }

        public GetHistoryParamSet setPeerId(Integer peerId) {
            this.peerId = peerId;
            return this;
        }

        public boolean isExtended() {
            return extended;
        }

        public GetHistoryParamSet setExtended(boolean extended) {
            this.extended = extended;
            return this;
        }

        public int getReverse() {
            return reverse;
        }

        /**
         * @param rev 0 (по умолчанию) для обратного хронолгического порядка, 1 для нормального
         * @return этот объект
         */
        public GetHistoryParamSet setReverse(int rev) {
            this.reverse = rev;
            return this;
        }

        public int getStartMessageId() {
            return startMessageId;
        }

        public GetHistoryParamSet setStartMessageId(int startMessageId) {
            this.startMessageId = startMessageId;
            return this;
        }

        public List<String> getFields() {
            return fields;
        }

        public GetHistoryParamSet addField(String field) {
            this.fields.add(field);
            return this;
        }

        public GetHistoryParamSet removeField(String field) {
            this.fields.remove(field);
            return this;
        }

        public int getGroupId() {
            return groupId;
        }

        public GetHistoryParamSet setGroupId(int groupId) {
            this.groupId = groupId;
            return this;
        }
    }
}
