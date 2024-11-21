package com.example.jwtrefresh.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpDto {
    private String email;
    private String password;
    private String phoneNumber;
}
