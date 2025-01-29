package com.ics.zoo.controller;

import org.springframework.beans.factory.annotation.Autowired;
 
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.ics.zoo.service.GoogleSignInService;

@RestController
@RequestMapping("/signin")
public class GoogleSignInController {

	@Autowired
	private GoogleSignInService googleSignInService;

	@GetMapping("/setInfo")
	RedirectView setInfo(@AuthenticationPrincipal OAuth2User principal) {
		return googleSignInService.signIn(principal.getAttribute("email"));
	}

 }
