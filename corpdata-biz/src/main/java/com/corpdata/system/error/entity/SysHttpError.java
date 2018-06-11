package com.corpdata.system.error.entity;

import com.corpdata.core.base.BaseEntity;

/**
 * 系统HTTP请求异常
 * 
 * @author zealon
 * @date 2018-06-11 09:32:48
 * 
 */
public class SysHttpError extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private String errorServiceUrl;
	private String errorServiceMethod;
	private String errorMessage;
	private String ipAddress;

	public void setErrorServiceUrl(String errorServiceUrl) {
		this.errorServiceUrl = errorServiceUrl;
	}

	public String getErrorServiceUrl() {
		return errorServiceUrl;
	}
	public void setErrorServiceMethod(String errorServiceMethod) {
		this.errorServiceMethod = errorServiceMethod;
	}

	public String getErrorServiceMethod() {
		return errorServiceMethod;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getIpAddress() {
		return ipAddress;
	}
}
