package ru.blc.cutlet.vk.objects.main.keyboard;

import lombok.Getter;
import lombok.Setter;
import ru.blc.cutlet.vk.command.VkCommandSender;
import ru.blc.objconfig.ConfigurationSection;

import java.util.function.Consumer;

public class OpenLinkButtonAction extends ButtonAction{

    @Getter
    @Setter
    private String link;
    @Getter
    @Setter
    private String label;

    public OpenLinkButtonAction(String link, String label) {
        super("open_link", null);
        this.link = link;
        this.label = label;
    }

    public OpenLinkButtonAction(String link, String label, Consumer<VkCommandSender> onPress) {
        super("open_link", onPress);
        this.link = link;
        this.label = label;
    }

    @Override
    public void write(ConfigurationSection json) {
        super.write(json);
        json.set("label", getLabel());
        json.set("link", getLink());
    }
}
