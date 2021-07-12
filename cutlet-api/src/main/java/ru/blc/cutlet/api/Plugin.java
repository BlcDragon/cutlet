package ru.blc.cutlet.api;

import org.slf4j.Logger;

/**
 * Описывает добавленные расширения, такие как модули и боты
 */
public interface Plugin {

    String getName();

    Logger getLogger();

    Cutlet getCutlet();

    boolean isEnabled();

    void setEnabled(boolean enabled);

    boolean isLoaded();
}
