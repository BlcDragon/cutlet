package ru.blc.cutlet.vk.objects.main.keyboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.blc.objconfig.ConfigurationSection;
import ru.blc.objconfig.json.JsonConfiguration;

import java.nio.charset.Charset;
import java.util.ArrayList;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class Keyboard {

    private boolean oneTime = false;
    private boolean inline = true;
    private Buttons buttons = new Buttons();

    public String toJson(){
        JsonConfiguration json  = new JsonConfiguration();
        json.set("one_time", isOneTime());
        json.set("inline", isInline());
        ArrayList<ArrayList<ConfigurationSection>> buttons = new ArrayList<>();
        for (ButtonsLine line : getButtons().getButtonsLines()){
            ArrayList<ConfigurationSection> cLine = new ArrayList<>();
            for (Button button:line.getButtons()){
                JsonConfiguration jButton = new JsonConfiguration();
                button.write(jButton);
                cLine.add(jButton);
            }
            buttons.add(cLine);
        }
        json.set("buttons", buttons);
        return json.saveToString(Charset.defaultCharset());
    }

    public Keyboard withLine(Button...buttonsLine){
        buttons.addButtonsLine(new ButtonsLine(buttonsLine));
        return this;
    }

    public Keyboard inline(boolean inline){
        this.inline = inline;
        return this;
    }

    public Keyboard oneTime(boolean oneTime){
        this.oneTime = oneTime;
        return this;
    }

}
