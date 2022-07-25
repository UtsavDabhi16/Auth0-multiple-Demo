package com.inexture.authdemo.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.inexture.authdemo.config.AuthConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

//    @Autowired
//    private AuthConfig config;

    @GetMapping(value = "/")
    @ResponseBody
    public String home(HttpServletRequest request, HttpServletResponse response, final Authentication authentication) throws IOException {
        System.out.println("Authentication is :"+authentication);
        System.out.println("Response is :"+response);
        if (authentication!= null && authentication instanceof TestingAuthenticationToken) {
            TestingAuthenticationToken token = (TestingAuthenticationToken) authentication;
            System.out.println("In multiple token is :"+token);
            DecodedJWT jwt = JWT.decode(token.getCredentials().toString());
            String email = jwt.getClaims().get("email").asString();
//            String redirectUri = config.getContextPath(request) + "/register";
            response.sendRedirect("http://localhost:8080/demo");
            return "Welcome, " + email + "!";
        } else {
            System.out.println("Plz register yourself");
//            System.out.println("URI :"+ config.getContextPath(request));
            response.sendRedirect("http://localhost:8080/register");
//            String redirectUri = config.getContextPath(request) + "/register";
//            response.sendRedirect(redirectUri);
            return null;
        }

    }

}