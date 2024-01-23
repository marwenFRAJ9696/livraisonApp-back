package com.fly.delivery.service;


import com.fly.delivery.dao.response.SignUpResponse;

import java.util.Collection;

public interface AdminService {
   Collection<SignUpResponse> getAllClientAccount();

    SignUpResponse getClientAccountByEmail(String email);
}
