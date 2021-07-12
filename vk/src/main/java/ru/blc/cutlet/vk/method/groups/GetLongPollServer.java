package ru.blc.cutlet.vk.method.groups;

import com.google.common.base.Preconditions;
import lombok.Getter;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import ru.blc.cutlet.vk.AccessToken;
import ru.blc.cutlet.vk.method.Method;
import ru.blc.cutlet.vk.method.ParamsSet;

import java.util.ArrayList;
import java.util.List;

public class GetLongPollServer extends Method<GetLongPollServer> {

    public GetLongPollServer() {
        super("groups.getLongPollServer", AccessToken.AccessTokenType.USER, AccessToken.AccessTokenType.GROUP);
    }

    @Override
    public GetLongPollServerParamSet getNewParamsSet() {
        return new GetLongPollServerParamSet(this);
    }

    public static class GetLongPollServerParamSet extends ParamsSet<GetLongPollServer>{

        @Getter
        private int groupId;

        public GetLongPollServerParamSet(Method<GetLongPollServer> method) {
            super(method);
        }

        @Override
        public List<NameValuePair> getParams() {
            ArrayList<NameValuePair> result = new ArrayList<>();
            Preconditions.checkArgument(getGroupId()>0);
            result.add(new BasicNameValuePair("group_id", String.valueOf(getGroupId())));
            return result;
        }

        public GetLongPollServerParamSet setGroupId(int groupId) {
            this.groupId = groupId;
            return this;
        }
    }
}
