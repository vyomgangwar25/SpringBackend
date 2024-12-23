package com.ics.zoo.util;

import java.util.HashMap;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class UrlConstant {
	HashMap<String, String> hm = new HashMap<>();
	public Set<String> generateUrl(String token) {
		String url = token;
		hm.put("tokenurl", url);
		Set<String> key = hm.keySet();
		return key;
	}

	public String getUrlValue(String key) {
		String value = hm.get(key);
		return value;
	}

}
