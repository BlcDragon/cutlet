package ru.blc.cutlet.vk.method.board;

import ru.blc.cutlet.vk.method.MethodStore;

public class Board extends MethodStore {

	public AddTopic addTopic = new AddTopic();
	public CloseTopic closeTopic = new CloseTopic();
	public CreateComment createComment = new CreateComment();
	public DeleteComment deleteComment = new DeleteComment();
	public DeleteTopic deleteTopic = new DeleteTopic();
	public EditComment editComment = new EditComment();
	public EditTopic editTopic = new EditTopic();
	public FixTopic fixTopic = new FixTopic();
	public GetComments getComments = new GetComments();
	public GetTopics getTopics = new GetTopics();
	public OpenTopic openTopic = new OpenTopic();
	public RestoreComment restoreComment = new RestoreComment();
	public UnfixTopic unfixTopic = new UnfixTopic();
}
