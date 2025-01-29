package com.ics.zoo.enums;

import java.util.ArrayList;
import java.util.List;

public enum EndPoint {

	TEST("/user/setnewpassword"),
	LOGIN("/user/login"),
	REGISTRATION("/user/registration"),
	FORGET_PASSWORD("/user/forgetpassword"),
	FETCH_ROLES("/user/fetchroles"),
	TOKEN_VALIDATION("/user/refreshtoken"),
	DASH("/signin/userInfo"),
    OAUTH("/oauth2/**"),
    DEPTCREATE("/department/create"),
    DEPTLIST("/department/list/{id}");
	private String endPoint;

	private EndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public String getEndPoint() {
		return endPoint;
	}

	public static String[] getEndPointsArray() {

		List<String> endpointList = new ArrayList<>();
		for (EndPoint endpoint : EndPoint.values()) {
			endpointList.add(endpoint.getEndPoint());
		}
		return endpointList.toArray(new String[0]);
	}
}