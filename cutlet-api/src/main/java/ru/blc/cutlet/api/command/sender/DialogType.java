package ru.blc.cutlet.api.command.sender;

/**
 * Тип диалога
 */
public enum DialogType {
    /**
     * ЛС
     */
    PRIVATE_MESSAGE,
    /**
     * Общие чаты
     */
    CONVERSATION,
    /**
     * Используется для регистрации команд в люыбх чатах,
     * но в исключительных ситуациях может быть и у отправителя (например консоли)
     */
    ALL,
    ;

    /**
     * Проверяет разрешен тип диалога этим типом
     * @param other тип для проверки
     * @return true, если диалог разрешен
     */
    public boolean allows(DialogType other){
        if (this==ALL||other==ALL) return true;
        return other==this;
    }
}
