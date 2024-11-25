package com.example.demo.enums;

import java.util.ArrayList;
import java.util.List;

public enum EndPoint {

	LOGIN("/user/login"),
	REGISTRATION("/user/registration"),
	FORGET_PASSWORD("/user/forgetpassword"),
	newpath("/user/newpath"),
	FETCH_ROLES("/user/fetchroles");

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