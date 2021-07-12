package ru.blc.cutlet.vk.objects.main.keyboard;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Buttons {

    @Getter
    private final List<ButtonsLine> buttonsLines = new ArrayList<>();

    public Buttons addButtonsLine(@NotNull ButtonsLine line){
        buttonsLines.add(line);
        return this;
    }
}
