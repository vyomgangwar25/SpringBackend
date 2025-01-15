package com.ics.zoo.util;

import java.util.HashMap;
import java.util.Random;
import org.springframework.stereotype.Component;

import com.ics.zoo.enums.ResponseEnum;

/**
 * UrlConstant
 * 
 * @author Vyom Gangwar
 */
@Component
public class UrlConstant {

	HashMap<Integer, String> hm = new HashMap<>();

	public Integer urlKey(String token) {
		Integer key = randomNo();
		hm.put(key, token);
		return key;
	}

	public String getUrlValue(Integer key) {
		String value = hm.get(key);
		return value;
	}

	public Integer randomNo() {
		Random random = new Random();
		int no = random.nextInt(99);
		return no;
	}

	public String removeKey(Integer key) {
		return hm.remove(key);
	}

}
