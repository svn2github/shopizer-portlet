package com.salesmanager.core.modules.email;

import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

public class EmailConfig implements JSONAware {
	
	private String hostName;
	private String smtpHost;
	private String smtpPort;
	private String smtpUserName;
	private String smtpPassword;
	private boolean smtpAuth = false;
	private boolean starttls = false;
	
	private String emailTemplatesPath = null;
	
	@SuppressWarnings("unchecked")
	@Override
	public String toJSONString() {
		//TODO populate
		JSONObject data = new JSONObject();
		data.put("host", this.getSmtpHost());
		return data.toJSONString();
	}
	
	
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getSmtpHost() {
		return smtpHost;
	}
	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}
	public String getSmtpPort() {
		return smtpPort;
	}
	public void setSmtpPort(String smtpPort) {
		this.smtpPort = smtpPort;
	}
	public String getSmtpUserName() {
		return smtpUserName;
	}
	public void setSmtpUserName(String smtpUserName) {
		this.smtpUserName = smtpUserName;
	}
	public String getSmtpPassword() {
		return smtpPassword;
	}
	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}
	public boolean isSmtpAuth() {
		return smtpAuth;
	}
	public void setSmtpAuth(boolean smtpAuth) {
		this.smtpAuth = smtpAuth;
	}
	public boolean isStarttls() {
		return starttls;
	}
	public void setStarttls(boolean starttls) {
		this.starttls = starttls;
	}
	public void setEmailTemplatesPath(String emailTemplatesPath) {
		this.emailTemplatesPath = emailTemplatesPath;
	}
	public String getEmailTemplatesPath() {
		return emailTemplatesPath;
	}

}
