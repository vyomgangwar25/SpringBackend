package com.ics.zoo.service;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ics.zoo.entities.TokenCheck;
import com.ics.zoo.entities.User;
import com.ics.zoo.repository.TokenRepository;
import com.ics.zoo.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultJwtParserBuilder;

@Service
public class RefreshTokenService {

	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private JwtUtil jwtUtil;

	private String SECRET_KEY = "2D4A614E645267556B58703273357638792F423F4428472B4B6250655368588D";
	private Integer ExpirationTime = 8;

	public String generateToken(User userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return jwtUtil.createToken(claims, userDetails.getEmail(), ExpirationTime, SECRET_KEY);
	}

	public boolean validateToken(String token, String email) {
		TokenCheck tokenCheck = tokenRepository.findByRtoken(token);
		if (email.equals(tokenCheck.getUser().getEmail()) && !isTokenExpired(token)) {
			return true;
		}
		return false;
	}

	public Boolean isTokenExpired(String token) {
		Boolean isExpired = extractExpiration(token).before(new Date());
		return isExpired;
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return new DefaultJwtParserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
	}

}
