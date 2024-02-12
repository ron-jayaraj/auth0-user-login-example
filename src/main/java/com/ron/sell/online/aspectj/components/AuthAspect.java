package com.ron.sell.online.aspectj.components;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseCookie;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import java.net.http.HttpHeaders;
import java.util.Objects;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;

@Aspect
@Component
public class AuthAspect {

    private final JwtDecoder jwtDecoder;
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public AuthAspect(JwtDecoder jwtDecoder, HttpServletRequest request, HttpServletResponse response) {
        this.jwtDecoder = jwtDecoder;
        this.request = request;
        this.response = response;
    }

    @Pointcut("@annotation(com.ron.sell.online.aspectj.Authenticated)")
    public void authenticatedMethods() {
    }

    @Before("authenticatedMethods()")
    public void validateAuthToken(JoinPoint joinPoint) {
        System.out.println("yes insdie the annotation class");

        String idToken = getIdTokenFromCookie();
        if(Objects.isNull(idToken)){
            System.out.println(" id token not in the cookie so trying to retrieve it from request and add to cookie");
           idToken = request.getHeader("Ron-X-User-Identity");
           System.out.println(" got the id token from request header "+idToken);
        }

      
      decodeToken(idToken);
      addTokenToCookie(idToken);

    }

    private String getIdTokenFromCookie() {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            System.out.println("GOT COOKIE***************");

            for (Cookie cookie : cookies) {
                if ("Ron-X-User-Identity-Cookie".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        System.out.println("mmm null");

        return null;
    }

    private void addTokenToCookie(String idToken) {



        Cookie cookie = new Cookie("RonsCookie", "tempRonToken");
        // Set appropriate cookie attributes (secure, HttpOnly, etc.)
        cookie.setMaxAge(120000); // Set the maximum age in seconds

        cookie.setPath("/");
        cookie.setSecure(false);
        cookie.setHttpOnly(false);
        response.addCookie(cookie);
        
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Set-Cookie",cookie.toString());
        

    }


public void decodeToken(String idToken) {


        try {
            // Parse the JWT token
            JWT jwt = JWTParser.parse(idToken);

            // Get the claims set from the JWT
            JWTClaimsSet jwtClaimsSet = jwt.getJWTClaimsSet();

            // Extract information from the claims set
            String email = jwtClaimsSet.getStringClaim("email");

            // Use the extracted information as needed
            System.out.println("Email: " + email);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("No JwtAuthenticationToken found in method arguments.");

        }
    }
}


