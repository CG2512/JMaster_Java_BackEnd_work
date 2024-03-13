package jmaster.io.demo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jmaster.io.demo.dto.ResponseDTO;
import jmaster.io.demo.service.JwtTokenService;

@RestController
public class LoginController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtTokenService jwtTokenService;
	@PostMapping("/login")
	public ResponseDTO<String> login(@RequestParam("username") String username,
			@RequestParam("password") String password) throws IOException {
		// authen,throw exception if fail
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		//login success then generate jwt token,
		
		return ResponseDTO.<String>builder().status(200).data(jwtTokenService.createToken(username)).build();
	}

}
