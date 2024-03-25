package com.indikaudaya.bestfarmer.dto;

import lombok.*;

import java.io.Serializable;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SignupDTO implements Serializable {
    private String email;
    private String mobile;
    private String password;
    private String userType;
    private boolean status;
}
