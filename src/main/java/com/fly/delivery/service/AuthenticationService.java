package com.fly.delivery.service;

import com.fly.delivery.dao.request.SignUpRequest;
import com.fly.delivery.dao.request.SigninRequest;
import com.fly.delivery.dao.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);
    JwtAuthenticationResponse signupForUser(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);
}
