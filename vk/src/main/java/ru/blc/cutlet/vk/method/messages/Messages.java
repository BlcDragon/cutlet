package ru.blc.cutlet.vk.method.messages;

import ru.blc.cutlet.vk.method.MethodStore;

public class Messages extends MethodStore {

	public Delete delete = new Delete();
	public Edit edit = new Edit();
	public EditChat editChat = new EditChat();
	public GetByConversationMessageId getByConversationMessageId = new GetByConversationMessageId();
	public GetConversations getConversations = new GetConversations();
	public GetConversationMembers getConversationMembers = new GetConversationMembers();
	public GetChat getChat = new GetChat();
	public GetHistory getHistory = new GetHistory();
	public IsMessagesFromGroupAllowed isMessagesFromGroupAllowed = new IsMessagesFromGroupAllowed();
	public Send send = new Send();
	public SendMessageEventAnswer sendMessageEventAnswer = new SendMessageEventAnswer();
	public RemoveChatUser removeChatUser = new RemoveChatUser();

}
