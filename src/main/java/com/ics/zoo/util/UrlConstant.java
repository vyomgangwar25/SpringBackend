package com.ics.zoo.util;

import java.util.HashMap;
import java.util.Random;
import org.springframework.stereotype.Component;

import com.ics.zoo.enums.ResponseEnum;

/**
 * UrlConstant
 * @author Vyom Gangwar
 * */
@Component
public class UrlConstant {

	HashMap<String, String> hm = new HashMap<>();
	public String urlKey(String token) {
		String key = randomNo().toString();
		hm.put(key, token);
		return key;
	}

	public String getUrlValue(String key) {
		if (!hm.containsKey(key)) {
			return  ResponseEnum.NO_VALUE_FOUND.getMessage();
		}
		String value = hm.get(key);
		return value;
	}

	public Integer randomNo() {
		Random random = new Random();
		int no = random.nextInt(99);
		return no;
	}
	
	public String removeKey(String key)
	{
		return hm.remove(key);
	}

}
