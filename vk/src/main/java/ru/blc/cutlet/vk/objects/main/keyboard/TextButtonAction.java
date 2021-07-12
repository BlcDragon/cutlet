package ru.blc.cutlet.vk.objects.main.keyboard;

import lombok.Getter;
import lombok.Setter;
import ru.blc.cutlet.vk.command.VkCommandSender;
import ru.blc.objconfig.ConfigurationSection;

import java.util.function.Consumer;

public class TextButtonAction extends ButtonAction{

    @Getter
    @Setter
    private String label;

    public TextButtonAction(String label) {
        super("text", null);
        this.label = label;
    }

    public TextButtonAction(String label, Consumer<VkCommandSender> onPress) {
        super("text", onPress);
        this.label = label;
    }

    @Override
    public void write(ConfigurationSection json) {
        super.write(json);
        json.set("label", getLabel());
    }
}
