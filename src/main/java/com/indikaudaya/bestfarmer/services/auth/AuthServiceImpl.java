package com.indikaudaya.bestfarmer.services.auth;

import com.indikaudaya.bestfarmer.entities.User;
import com.indikaudaya.bestfarmer.repositories.UserRepository;
import com.indikaudaya.bestfarmer.dto.SignupDTO;
import com.indikaudaya.bestfarmer.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO createUser(SignupDTO signupDTO) {
        User user = new User();
        user.setEmail(signupDTO.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(signupDTO.getPassword()));
        user.setMobile(signupDTO.getMobile());

        User createdUser = userRepository.save(user);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(createdUser.getId());
        userDTO.setEmail(createdUser.getEmail());
        return userDTO;
    }
}
