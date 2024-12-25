package com.sample.sbb.user;

import lombok.Getter;

@Getter
public enum UserRole {
	ADMIN("ROLE_ADMIN","ADMIN유저"),
	USER("ROLE_USER","일반유저");
	
	private String value;
	private String desc;
	
	UserRole(String value, String desc) {
		this.value = value;
	}
	
	public String desc() {
		return desc;
	}
}
