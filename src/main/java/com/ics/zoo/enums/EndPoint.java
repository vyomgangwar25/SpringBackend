package com.ics.zoo.enums;

import java.util.ArrayList;
import java.util.List;

public enum EndPoint {

	TEST("/user/setnewpassword"),
	LOGIN("/user/login"),
	REGISTRATION("/user/registration"),
	FORGET_PASSWORD("/user/forgetpassword"),
	FETCH_ROLES("/user/fetchroles"),
	TOKEN_VALIDATION("/user/refreshtoken");

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