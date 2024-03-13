package jmaster.io.demo.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;

@Service
public class JwtTokenService {

	/*
	 * @Value("${jwt.secret}") private String secretKey;
	 */
	private SecretKey secretKey = Jwts.SIG.HS256.key().build();
	private long validity = 5; // 5 minutes

	public String createToken(String username) {

		// SecretKey key = Jwts.SIG.HS256.key().build();

		Date now = new Date();
		Date exp = new Date(now.getTime() + validity * 60 * 1000);// convert minutes to miliseconds

		return Jwts.builder().subject(username).issuedAt(now).expiration(exp).signWith(secretKey).compact();
	}

	public boolean isValidToken(String token) {

		try {
			Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
			return true;
		} catch (Exception e) {
			// Token not valid
		}
		return false;
	}

	public String getUsername(String token) {
		try {
			return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getSubject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
