package com.ics.zoo.util;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.ics.zoo.entities.TokenCheck;
import com.ics.zoo.entities.User;
import com.ics.zoo.repository.RolePrivilegesRepository;
import com.ics.zoo.repository.TokenRepository;
import com.ics.zoo.service.RolePrivilageService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultJwtParserBuilder;

/**
 * JwtUtil
 * 
 * @author Vyom Gangwar
 **/

@Component
public class JwtUtil {
	@Autowired
	private RolePrivilegesRepository rolePrivilegeList;

	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private RolePrivilageService rolePrivilageService;

	/**
	 * validity of token in hours
	 */
	private Integer ExpirationTime = 2;

	private String SECRET_KEY = "2D4A614E645267556B58703273357638792F423F4428472B4B6250655368566D";

	/**
	 * this method is used to create a new token. token is valid for 2 hrs
	 * 
	 * @param claims,userName
	 * @author Vyom Gangwar
	 */
	public String createToken(Map<String, Object> claims, String userName, Integer expirationTime, String secretKey) {
		return Jwts.builder().setClaims(claims).setSubject(userName).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + Duration.ofHours(expirationTime).toMillis()))
				.signWith(generateKey(secretKey)).compact();
	}

	public SecretKey generateKey(String secret) {

		byte[] decodedKey = Base64.getDecoder().decode(secret);

		SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length,
				SignatureAlgorithm.HS256.getJcaName());

		return secretKey;
	}

	public String generateToken(User userDetails) {
		Map<String, Object> claims = new HashMap<>();

		List<Integer> privilegeList = rolePrivilegeList.findByRoleId(userDetails.getRoleId()).stream()
				.map(t -> t.getPrivileges().getId()).collect(Collectors.toList());
		claims.put("authority", privilegeList);

		return createToken(claims, userDetails.getEmail(), ExpirationTime, SECRET_KEY);
	}

	/**
	 * this is used to validate the token
	 * 
	 * @param token ,email
	 * 
	 **/
	public User validateToken(String token, String email) {
		TokenCheck tokenObject = tokenRepository.findByToken(token);
		if (tokenObject == null) {
			return null;
		}
		if (tokenObject.getIsvalid() != false)
			if (email.equals(tokenObject.getUser().getEmail()) && !isTokenExpired(token)) {
				loadUserByUsername(tokenObject);
				return tokenObject.getUser();
			}
		return null;
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

	/**
	 * this method is used to check whether the token is expired or not
	 */
	public Boolean isTokenExpired(String token) {
		Boolean isExpired = extractExpiration(token).before(new Date());
		return isExpired;
	}

	public Date extractExpiration(String token) {

		return extractClaim(token, Claims::getExpiration);
	}

	// token claim extraction:
	public UserDetails loadUserByUsername(TokenCheck tokenObject) {
		Claims claims = extractAllClaims(tokenObject.getToken());
		ArrayList<Integer> newList = (ArrayList<Integer>) claims.get("authority");
		for (Integer j : newList) {
			tokenObject.getUser().setAuthority(newList.stream().map(a -> new SimpleGrantedAuthority("AUTHORITY_" + j))
					.collect(Collectors.toList()));
		}
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				tokenObject.getUser(), null, tokenObject.getUser().getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);

		return tokenObject.getUser();
	}
}
