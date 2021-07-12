package ru.blc.cutlet.vk.objects.main.keyboard;

import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.hash.TLongObjectHashMap;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import ru.blc.cutlet.vk.command.VkCommandSender;
import ru.blc.objconfig.ConfigurationSection;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

@Data
public class ButtonAction {

    private static final AtomicLong id = new AtomicLong(0L);
    private static final AtomicLong removeId = new AtomicLong(0L);
    private static final int MAX_CACHED_ACTIONS = 1000;
    private static final TLongObjectMap<ButtonAction> actions = new TLongObjectHashMap<>();

    synchronized public static ButtonAction getAction(long id){
        return actions.get(id);
    }

    private String type;
    private Consumer<VkCommandSender> onPress;
    @Getter(value = AccessLevel.NONE)
    @Setter(value = AccessLevel.NONE)
    private long ownId;

    public ButtonAction(String type, @Nullable Consumer<VkCommandSender> onPress){
        this.type = type;
        this.onPress = onPress;
        this.ownId = id.getAndAdd(1L);
    }

    synchronized public void write(ConfigurationSection json){
        json.set("type", getType());
        json.set("payload", "{\"id\": "+ownId+"}");
        synchronized (actions) {
            actions.put(ownId, this);
            while (actions.size() > MAX_CACHED_ACTIONS) {
                actions.remove(removeId.getAndAdd(1L));
            }
        }
    }

    public void onPress(VkCommandSender sender, Logger logger){
        if (onPress!=null){
            try {
                onPress.accept(sender);
            }catch (Exception e){
                logger.error("Error while accepting button action", e);
            }
        }
    }
}
