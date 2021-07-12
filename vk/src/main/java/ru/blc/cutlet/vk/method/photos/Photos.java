package ru.blc.cutlet.vk.method.photos;

import ru.blc.cutlet.vk.method.MethodStore;

public class Photos extends MethodStore {

	public GetWallUploadServer getWallUploadServer = new GetWallUploadServer();
	public SaveWallPhoto saveWallPhoto = new SaveWallPhoto();
	public GetMessagesUploadServer getMessagesUploadServer = new GetMessagesUploadServer();
	public SaveMessagesPhoto saveMessagesPhoto = new SaveMessagesPhoto();

}
