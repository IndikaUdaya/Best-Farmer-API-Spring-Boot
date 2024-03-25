package com.indikaudaya.bestfarmer.services.auth;

import com.indikaudaya.bestfarmer.dto.SignupDTO;
import com.indikaudaya.bestfarmer.dto.UserDTO;

public interface AuthService {
    UserDTO createUser(SignupDTO signupDTO);
}
