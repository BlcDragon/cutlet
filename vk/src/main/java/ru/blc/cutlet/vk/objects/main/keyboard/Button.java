package ru.blc.cutlet.vk.objects.main.keyboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.blc.objconfig.json.JsonConfiguration;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Button {

    private ButtonColor color;
    private ButtonAction action;

    public void write(JsonConfiguration json){
        json.set("color", getColor().getName());
        getAction().write(json.createSection("action"));
    }


    public enum ButtonColor {
        BLUE("primary"),
        WHITE("secondary"),
        RED("negative"),
        GREEN("positive"),
        ;

        private final String name;

        ButtonColor(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return getName();
        }
    }
}
