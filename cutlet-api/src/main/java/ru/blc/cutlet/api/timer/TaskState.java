package ru.blc.cutlet.api.timer;

/**
 * Описывает состояние задачи.<br>
 * Для задач-циклов (таймеров), состояник {@link #FINISHED} не наступает никогда,
 * состояния {@link #WAITING} и {@link #RUNNING} чередуются между собой.
 */
public enum TaskState {

    /**
     * Задача ожидает момента своего выполнения
     */
    WAITING,
    /**
     * Задача выполняется
     */
    RUNNING,
    /**
     * Задача завершена
     */
    FINISHED,
    /**
     * Задача отменена
     */
    CANCELLED;
}
