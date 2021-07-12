package ru.blc.cutlet.vk.method;

import com.google.common.base.Preconditions;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import ru.blc.cutlet.vk.AccessToken;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.function.BiConsumer;

public abstract class Method<T extends Method<T>> {

	private final String callAdress;
	private final List<AccessToken.AccessTokenType> types;

	public Method(String callAdress, AccessToken.AccessTokenType... types) {
		this.callAdress = callAdress;
		this.types = Arrays.asList(types);
	}

	public String getCallAdress() {
		return callAdress;
	}

	public boolean isTokenAllowed(AccessToken.AccessTokenType type) {
		return types.contains(type);
	}

	public boolean isTokenAllowed(AccessToken token) {
		return isTokenAllowed(token.getType());
	}

	/**
	 * Вызывает метод с указанными параметрами и ожидает ответ от вк апи
	 * @param params параметры
	 * @param headers http headers
	 * @return ответ от вк апи
	 */
	public String callAwait(ParamsSet<T> params, Header...headers) {
		Preconditions.checkNotNull(params, "params");
		Preconditions.checkNotNull(params.getToken(), "token");
		Preconditions.checkArgument(isTokenAllowed(params.getToken()),
				"Token type " + params.getToken().getType().name() + " is not allowed for method " + callAdress);
		String adr = "https://api.vk.com/method/" + getCallAdress();
		try {
			RequestConfig globalConfig = RequestConfig.custom()
	                .setCookieSpec(CookieSpecs.DEFAULT)
	                .build();
			HttpClient httpclient = HttpClients.custom()
	                .setDefaultRequestConfig(globalConfig)
	                .build();
	        RequestConfig localConfig = RequestConfig.copy(globalConfig)
	                .setCookieSpec(CookieSpecs.STANDARD)
	                .build();
			HttpPost httppost = new HttpPost(adr);
			for (Header h : headers) {
				httppost.setHeader(h.name, h.value);
			}
			httppost.setConfig(localConfig);
			

			// Request parameters and other properties.
			List<NameValuePair> pars = params.getParams();
			pars.add(new BasicNameValuePair("access_token", params.getToken().getValue()));
			pars.add(new BasicNameValuePair("v", params.getVersion().getText()));
			httppost.setEntity(new UrlEncodedFormEntity(pars, "UTF-8"));
			

			// Execute and get the response.
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();

			byte[] data;
			if (entity == null) {
				data = new byte[0];
			} else {
				data = EntityUtils.toByteArray(entity);
			}
			int bytesl = data.length;
	        if (bytesl>0) {
				return new String(data, StandardCharsets.UTF_8);
	        }else {
	        	return null;
	        }
		} catch (IOException ex) {
			ex.printStackTrace();
			System.out.println(adr);
		}
		return null;
	}

	/**
	 * Запускает выполнение этого метода и не дожидается ответа от вк апи<br>
	 * Задача создается в {@link ForkJoinPool#commonPool()}<br> 
	 * @param params параметры
	 * @param headers http headers
	 * @return запущенная задача
	 */
	public CompletableFuture<String> call(ParamsSet<T> params, Header...headers){
		return CompletableFuture.supplyAsync(() -> callAwait(params, headers));
	}

	/**
	 * Запускает выполнение этого метода и не дожидается ответа от вк апи<br>
	 * Задача создается в {@link ForkJoinPool#commonPool()}<br>
	 * После получения ответа от вк выполняет указанную задачу при помощи {@link CompletableFuture#whenComplete(BiConsumer)}<br>
	 * @param params параметры
	 * @param headers http headers
	 * @return запущенная задача
	 */
	public CompletableFuture<String> call(ParamsSet<T> params, BiConsumer<? super String, ? super Throwable> consumer, Header...headers){
		return CompletableFuture.supplyAsync(() -> callAwait(params, headers)).whenComplete(consumer);
	}
	
	public static class Header{
		private final String name;
		private final String value;
		
		public Header(String name, String value) {
			this.name = name;
			this.value = value;
		}
	}

	public abstract ParamsSet<T> getNewParamsSet();
}
