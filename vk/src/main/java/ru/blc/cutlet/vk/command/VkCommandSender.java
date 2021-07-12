package ru.blc.cutlet.vk.command;

import ru.blc.cutlet.api.bot.Bot;
import ru.blc.cutlet.api.command.sender.CommandSender;
import ru.blc.cutlet.vk.method.messages.Send;
import ru.blc.cutlet.vk.objects.main.Message;

public interface VkCommandSender extends CommandSender {

    int getPeerId();

    int getFromId();

    Message getMessage();

    Send.SendParamsSet getAnswerSet();

    Send.SendParamsSet getAnswerSet(String text);

    Bot getBot();
}
