package com.foxinmy.weixin4j.wxa;

import com.foxinmy.weixin4j.cache.CacheStorager;
import com.foxinmy.weixin4j.cache.FileCacheStorager;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.model.WeixinAccount;
import com.foxinmy.weixin4j.mp.token.WeixinTokenCreator;
import com.foxinmy.weixin4j.token.TokenCreator;
import com.foxinmy.weixin4j.token.TokenManager;
import com.foxinmy.weixin4j.wxa.api.LoginApi;
import com.foxinmy.weixin4j.wxa.api.QrCodeApi;

/**
 * @since 1.8
 */
public class WeixinProxy {

	private final LoginApi loginApi;
	private final QrCodeApi qrCodeApi;

	public WeixinProxy(
		WeixinAccount weixinAccount
	) {
		this(
			weixinAccount,
			new FileCacheStorager<Token>()
		);
	}

	public WeixinProxy(
		WeixinAccount weixinAccount,
		CacheStorager<Token> cacheStorager
	) {
		this(
			weixinAccount,
			new WeixinTokenCreator(weixinAccount.getId(), weixinAccount.getSecret()),
			cacheStorager
		);
	}

	private WeixinProxy(
		WeixinAccount weixinAccount,
		TokenCreator tokenCreator,
		CacheStorager<Token> cacheStorager
	) {
		if (weixinAccount == null) {
			throw new IllegalArgumentException(
				"weixinAccount must not be empty");
		}

		if (tokenCreator == null) {
			throw new IllegalArgumentException(
				"tokenCreator must not be empty");
		}

		if (cacheStorager == null) {
			throw new IllegalArgumentException(
				"cacheStorager must not be empty");
		}

		final TokenManager tokenManager = new TokenManager(tokenCreator, cacheStorager);

		this.loginApi = new LoginApi(weixinAccount);
		this.qrCodeApi = new QrCodeApi(tokenManager);
	}

	public LoginApi getLoginApi() {
		return loginApi;
	}

	public QrCodeApi getQrCodeApi() {
		return qrCodeApi;
	}

}
