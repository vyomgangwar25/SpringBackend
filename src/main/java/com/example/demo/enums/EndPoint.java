package com.example.demo.enums;

import java.util.ArrayList;
import java.util.List;

public enum EndPoint {

	LOGIN("/login"),
	REGISTRATION("/registration"),
	FORGET_PASSWORD("/forgetpassword"),
	newpath("/newpath"),
	FETCH_ROLES("/fetchroles");

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