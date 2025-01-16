package com.ics.zoo.util;

import java.util.HashMap;
import java.util.Random;
import org.springframework.stereotype.Component;

/**
 * TokenStore
 * 
 * @author Vyom Gangwar
 */
@Component
public class TokenStore {

	HashMap<Integer, String> tokenMap = new HashMap<>();

	public Integer Key(String token) {
		Integer key = randomNo();
		tokenMap.put(key, token);
		return key;
	}

	public String Value(Integer key) {
		String value = tokenMap.get(key);
		return value;
	}

	public Integer randomNo() {
		Random random = new Random();
		int no = random.nextInt(99);
		return no;
	}

	public String removeKey(Integer key) {
		return tokenMap.remove(key);
	}

}
