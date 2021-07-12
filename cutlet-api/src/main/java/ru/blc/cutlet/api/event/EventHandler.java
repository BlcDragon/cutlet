package ru.blc.cutlet.api.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventHandler {

	/**
	 * По умолчанию - {@link EventPriority#NORMAL}
	 * @return Приоритет получения события
	 */
	EventPriority eventPriority() default EventPriority.NORMAL;
	/**
	 * Игнорировать событие, если оно отменено.<br>
	 * Если ивент дошел до потребителя в отмененном виде и это значение = true - потребитель не получит событие</br>
	 * По умолчанию true
	 * @return Модель игнорирования отмененных событий
	 */
	boolean ignoreCancelled() default true;
	/**
	 * Различные модули могут запускать событие только для определнных потребителей.<br>
	 * Если модель поведения игнорирует фильтрацию (это значение = true),
	 * то потребитель получит событие даже если он не удовлетворяет условиям фильтра<br>
	 * По умолчанию false (не игнорировать фильтрацию)
	 * @return Модель игнорирования фильтров
	 */
	boolean ignoreFilter() default false;
}
