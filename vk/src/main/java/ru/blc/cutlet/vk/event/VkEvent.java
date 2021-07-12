package ru.blc.cutlet.vk.event;

import ru.blc.cutlet.api.event.Event;
import ru.blc.cutlet.vk.objects.VkApiObject;

public abstract class VkEvent extends Event {

    private final VkApiObject object;

    public VkEvent(VkApiObject object){
        this.object = object;
    }

    public VkApiObject getVkObject() {
        return object;
    }
}
