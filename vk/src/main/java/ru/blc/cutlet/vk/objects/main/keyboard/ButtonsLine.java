package ru.blc.cutlet.vk.objects.main.keyboard;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
public class ButtonsLine {

    @Getter
    private List<Button> buttons = new ArrayList<>();

    public ButtonsLine(Button...buttons){
        this.buttons = new ArrayList<>();
        this.buttons.addAll(Arrays.asList(buttons));

    }

    public ButtonsLine addButton(Button button){
        buttons.add(button);
        return this;
    }
}
