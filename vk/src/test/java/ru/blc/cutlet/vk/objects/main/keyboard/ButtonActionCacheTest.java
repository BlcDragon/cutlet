package ru.blc.cutlet.vk.objects.main.keyboard;

import org.junit.Assert;
import org.junit.Test;
import ru.blc.objconfig.json.JsonConfiguration;

public class ButtonActionCacheTest {
    @Test
    public void isPermissionAllows() {
        JsonConfiguration c = new JsonConfiguration();
        for (int i = 0; i<5000; i++){
            new ButtonAction("a", null).write(c);
        }
        Assert.assertNotNull("Wrong cache system", ButtonAction.getAction(4500));
    }
}