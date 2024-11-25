package com.ics.zoo.util;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ics.zoo.entities.RolePrivileges;
import com.ics.zoo.entities.Roles;
import com.ics.zoo.entities.User;
import com.ics.zoo.repository.RolePrivilegesRepository;
import com.ics.zoo.repository.RoleRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultJwtParserBuilder;

@Component
public class JwtUtil {
	@Autowired
	private RolePrivilegesRepository rolePrivilegeList;

	@Autowired
	private RoleRepository roleRepository;

	private String SECRET_KEY = "2D4A614E645267556B58703273357638792F423F4428472B4B6250655368566D";

	private String createToken(Map<String, Object> claims, String userName) {
		return Jwts.builder().setClaims(claims).setSubject(userName).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + Duration.ofHours(2).toMillis()))
				.signWith(generateKey(SECRET_KEY)).compact();
	}

	private SecretKey generateKey(String secret) {

		byte[] decodedKey = Base64.getDecoder().decode(secret);

		SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length,
				SignatureAlgorithm.HS256.getJcaName());

		return secretKey;
	}

	public String generateToken(User userDetails) {
		Map<String, Object> claims = new HashMap<>();

		List<Integer> privilegeList = new ArrayList<>();
		Roles role = roleRepository.findByRole(userDetails.getRole());

		List<RolePrivileges> ll = rolePrivilegeList.findByRoleId(role.getId());
		for (RolePrivileges roleprivileges : ll) {
			privilegeList.add(roleprivileges.getPrivileges().getId());

		}

		claims.put("authority", privilegeList);

		return createToken(claims, userDetails.getEmail());
	}

	public Boolean validateToken(String token, User userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getEmail()) && !isTokenExpired(token));
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return new DefaultJwtParserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	private Boolean isTokenExpired(String token) {
		Boolean isExpired = extractExpiration(token).before(new Date());
		return isExpired;
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	// token claim extraction:
	public String extractRole(String token) {
		Claims claims = extractAllClaims(token);
		return claims.get("role", String.class);
	}
}