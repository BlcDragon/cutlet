package ru.blc.cutlet.vk.method;

import ru.blc.cutlet.vk.method.board.Board;
import ru.blc.cutlet.vk.method.groups.Groups;
import ru.blc.cutlet.vk.method.messages.Messages;
import ru.blc.cutlet.vk.method.photos.Photos;
import ru.blc.cutlet.vk.method.users.Users;
import ru.blc.cutlet.vk.method.wall.Wall;

public class Methods extends MethodStore {

	public Board board = new Board();
	public Groups groups = new Groups();
	public Messages messages = new Messages();
	public Photos photos = new Photos();
	public Users users = new Users();
	public Wall wall = new Wall();
}
