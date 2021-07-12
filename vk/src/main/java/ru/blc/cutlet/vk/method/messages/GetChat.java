package ru.blc.cutlet.vk.method.messages;

import lombok.Getter;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import ru.blc.cutlet.vk.AccessToken;
import ru.blc.cutlet.vk.method.Method;
import ru.blc.cutlet.vk.method.ParamsSet;
import ru.blc.cutlet.vk.method.users.Get;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetChat extends Method<GetChat> {
    public GetChat() {
        super("messages.getChat", AccessToken.AccessTokenType.USER);
    }

    @Override
    public GetChatParamsSet getNewParamsSet() {
        return new GetChatParamsSet(this);
    }

    @Getter
    public static class GetChatParamsSet extends ParamsSet<GetChat>{

        private int chatId;
        private final List<Integer> chatIds = new ArrayList<>();
        private final List<String> fields = new ArrayList<>();
        private Get.NameCase nameCase;

        public GetChatParamsSet(Method<GetChat> method) {
            super(method);
        }

        @Override
        public List<NameValuePair> getParams() {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            if (getChatId()>0)params.add(new BasicNameValuePair("chat_id", String.valueOf(getChatId())));
            if (!getChatIds().isEmpty())params.add(new BasicNameValuePair("chat_ids", collectionToString(getChatIds())));
            if (!getFields().isEmpty()) params.add(new BasicNameValuePair("fields", collectionToString(getFields())));
            if (getNameCase() != null) params.add(new BasicNameValuePair("name_case", getNameCase().name().toLowerCase()));
            return params;
        }

        public GetChatParamsSet setChatId(int chatId) {
            this.chatId = chatId;
            return this;
        }

        public GetChatParamsSet addChatIds(int... chats) {
            for (int i : chats) {
                chatIds.add(i);
            }
            return this;
        }

        public GetChatParamsSet removeChatIds(int... chats) {
            for (int i : chats) {
                chatIds.remove((Object)i);
            }
            return this;
        }

        public GetChatParamsSet addFields(String... fields) {
            this.fields.addAll(Arrays.asList(fields));
            return this;
        }

        public GetChatParamsSet removeFields(String... fields) {
            this.fields.removeAll(Arrays.asList(fields));
            return this;
        }
    }
}
