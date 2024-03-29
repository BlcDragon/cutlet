package ru.blc.cutlet.api.event;

public enum EventPriority {

	/**
	 * Самый низкий приоритет<br>
	 * Слушатель получит событие самый первый, наименьшее влияние на результат события.
	 */
	LOWEST,
	/**
	 * Низкий приоритет<br>
	 * Слушатель получит событие одним из первых, низкое влияние на результат события
	 */
	LOW,
	/**
	 * Обычный приоритет<br>
	 * Стандартное влияние на результат события
	 */
	NORMAL,
	/**
	 * Высокий приоритет<br>
	 * Высокое влияние на результат события
	 */
	HIGH,
	/**
	 * Самый высокий приоритет<br>
	 * Наивысшее влияние на результат события
	 */
	HIGHEST,
	/**
	 * Последний приоритет<br>
	 * Предназначен только для ПРОСМОТРА результа, изменять результат в этом приоритете КРАЙНЕ НЕ РЕКОМЕНДУЕТСЯ
	 */
	MONITOR
}
