package com.edu.smartstudentcard.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class JwtTokenProvider {
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

	@Value("j3H5Ld5nYmGWyULy6xwpOgfSH++NgKXnJMq20vpfd+8=t")
	private String jwtSecret;

	@Value("${jwt.security.expirationInMs}")
	private int jwtExpirationInMs;

	public String generateToken(Authentication authentication) {

		UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();

		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
		
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
	    for (GrantedAuthority role :userDetails.getAuthorities()){
	        grantedAuthorities.add(new SimpleGrantedAuthority(role.getAuthority()));
	    }
	    
	   	String token = Jwts .builder() .setId(userDetails.getId()+"")
				 .setSubject(userDetails.getId()+"")
				 .claim("authorities",grantedAuthorities) 
				 .claim("user",userDetails)
				
				 .setIssuedAt(new
				 Date(System.currentTimeMillis())) .setExpiration(expiryDate)
				 .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
		 
		
		return token;
	}

	public Long getUserIdFromJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

		return Long.parseLong(claims.getSubject());
	}

	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException ex) {
			logger.error("Invalid JWT signature", ex);
		} catch (MalformedJwtException ex) {
			logger.error("Invalid JWT token", ex);
		} catch (ExpiredJwtException ex) {
			logger.error("Expired JWT token" + ex);
		} catch (UnsupportedJwtException ex) {
			logger.error("Unsupported JWT token" + ex);
		} catch (IllegalArgumentException ex) {
			logger.error("JWT claims string is empty" + ex);
		}
		return false;
	}
}
