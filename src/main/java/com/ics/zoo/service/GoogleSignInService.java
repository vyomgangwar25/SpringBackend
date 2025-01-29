package com.ics.zoo.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
 
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import com.ics.zoo.dto.LoginResponseDTO;
import com.ics.zoo.entities.TokenCheck;
import com.ics.zoo.entities.User;
import com.ics.zoo.repository.TokenRepository;
import com.ics.zoo.repository.UserRepository;
import com.ics.zoo.util.JwtUtil;

@Service
public class GoogleSignInService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private JwtUtil jwtutil;

	@Autowired
	private RefreshTokenService refreshTokenService;

	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private ModelMapper modelMapper;

	public RedirectView signIn(String email) {
		User user = repository.findByEmail(email);
		if (user != null) {
			String generated_token = jwtutil.generateToken(user);
			LoginResponseDTO response = modelMapper.map(user, LoginResponseDTO.class);
			response.setToken(generated_token);
			String refreshToken = refreshTokenService.generateToken(user);
			response.setRefreshToken(refreshToken);
			TokenCheck tokencheck = TokenCheck.builder().token(generated_token).isvalid(true).user(user)
					.rtoken(refreshToken).build();
			tokenRepository.save(tokencheck);

			String redirectUrl = "http://zoo.com:3000/api/signin?email=" + email + "&token=" + generated_token
					+ "&refreshToken=" + refreshToken + "&name=" + user.getUsername() + "&role=" + user.getRoleId()+""
					+ "&id=" + user.getId();

			return new RedirectView(redirectUrl);

		}
		return new RedirectView("http://zoo.com:3000/login");
	}
}
