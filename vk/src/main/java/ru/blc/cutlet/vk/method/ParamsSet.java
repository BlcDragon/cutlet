package ru.blc.cutlet.vk.method;

import com.google.common.base.Preconditions;
import org.apache.http.NameValuePair;
import ru.blc.cutlet.vk.AccessToken;
import ru.blc.cutlet.vk.ApiVersion;
import ru.blc.validate.Validate;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.function.BiConsumer;

public abstract class ParamsSet<T extends Method<T>> {

	private ApiVersion version;
	private AccessToken token;
	private final Method<T> method;
	
	public ParamsSet(Method<T> method) {
		this(method, null, null);
	}
	
	public ParamsSet(Method<T> method, AccessToken token) {
		this(method, token, null);
	}
	
	public ParamsSet(Method<T> method, AccessToken token, ApiVersion version) {
		Validate.notNull(method, "Method can not be null");
		this.method = method;
		this.setToken(token);
		this.setVersion(version);
	}

	public ApiVersion getVersion() {
		if (version==null) {
			return ApiVersion.LAST;
		}
		return version;
	}

	public ParamsSet<T> setVersion(ApiVersion version) {
		this.version = version;
		return this;
	}

	public AccessToken getToken() {
		return token;
	}

	/**
	 * Устанавливает токен для выполнения этого метода.<br>
	 * Без установки токена вызов метода не произойдет и будет выброшена ошибка {@link NullPointerException}
	 * @param token токен
	 * @return этот набор параметров
	 */
	public ParamsSet<T> setToken(AccessToken token) {
		this.token = token;
		return this;
	}

	@Deprecated
	public String toPostString(){return "";};

	public abstract List<NameValuePair> getParams();

	/**
	 * Вызывает метод и ожидает результат от вк<br>
	 * @return Ответ от вк api
	 * @throws NullPointerException если не установлен токен ({@link ParamsSet#setToken(AccessToken)})
	 */
	public String callAwait() {
		Preconditions.checkNotNull(getToken(), "token");
		return method.callAwait(this);
	}

	/**
	 * Вызывает метод и не дожидается ответа от вк апи<br>
	 * Задача создается в {@link ForkJoinPool#commonPool()}<br>
	 * @param headers http headers
	 * @return запущенная задача
	 * @throws NullPointerException если не установлен токен ({@link ParamsSet#setToken(AccessToken)})
	 */
	public CompletableFuture<String> call(Method.Header...headers){
		Preconditions.checkNotNull(getToken(), "token");
		return method.call(this, headers);
	}

	/**
	 * Вызывает метод и не дожидается ответа от вк апи<br>
	 * Задача создается в {@link ForkJoinPool#commonPool()}<br>
	 * После получения ответа от вк выполняет указанную задачу при помощи {@link CompletableFuture#whenComplete(BiConsumer)}<br>
	 * @param headers http headers
	 * @return запущенная задача
	 * @throws NullPointerException если не установлен токен ({@link ParamsSet#setToken(AccessToken)})
	 */
	public CompletableFuture<String> call(BiConsumer<? super String, ? super Throwable> consumer, Method.Header...headers){
		Preconditions.checkNotNull(getToken(), "token");
		return method.call(this, consumer, headers);
	}

	public static String collectionToString(Collection<?> c) {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (Object o : c) {
			i++;
			sb.append(o.toString());
			if (i<c.size()) sb.append(",");
		}
		return sb.toString();
	}
	public static String arrayToString(Object[] c) {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (Object o : c) {
			i++;
			sb.append(o.toString());
			if (i<c.length) sb.append(",");
		}
		return sb.toString();
	}

	public static String booleanToIntString(boolean b){
		if (b) return "1";
		return "0";
	}
}
