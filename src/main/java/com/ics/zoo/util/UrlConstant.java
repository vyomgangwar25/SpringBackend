package com.ics.zoo.util;

import java.util.HashMap;
import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class UrlConstant {

	HashMap<String, String> hm = new HashMap<>();
	public String UrlKey(String token) {
		String key = RandomNo().toString();
		hm.put(key, token);
		return key;
	}

	public String getUrlValue(String key) {
		if (!hm.containsKey(key)) {
			return "no value found!!";
		}
		String value = hm.get(key);
		return value;
	}

	public Integer RandomNo() {
		Random random = new Random();
		int no = random.nextInt(99);
		return no;
	}
	
	public String removeKey(String key)
	{
		return hm.remove(key);
	}

}
