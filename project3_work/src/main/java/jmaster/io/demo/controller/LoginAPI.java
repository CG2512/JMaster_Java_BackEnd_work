package jmaster.io.demo.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jmaster.io.demo.dto.UserDTO;
import jmaster.io.demo.service.JwtTokenService;
import jmaster.io.demo.service.UserService;


@RestController
@RequestMapping("/")
public class LoginAPI {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtTokenService jwtTokenService;

	@Autowired
	UserService userService;
	
	@GetMapping("/me")
	@PreAuthorize("isAuthenticated()")
	public UserDTO me(Principal p) {
			String username= p.getName();
			
			UserDTO user=userService.findByUsername(username);
			return user;
	}
	@PostMapping("/login")
	public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		// refreshToken = UUID.randomUUID().toString();
		// save refresh token - table(refresh_token)(username, refreshtoken,expired)
		// class TokenDTO(refreshToken,accesstoken)
		return jwtTokenService.createToken(username);
	}
}
