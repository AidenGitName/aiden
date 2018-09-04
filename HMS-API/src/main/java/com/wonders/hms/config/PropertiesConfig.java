package com.wonders.hms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("kms")
public class PropertiesConfig {
	private String endPoint;
	private String loginSecret;

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public String getLoginSecret() {
		return loginSecret;
	}

	public void setLoginSecret(String loginSecret) {
		this.loginSecret = loginSecret;
	}
}
