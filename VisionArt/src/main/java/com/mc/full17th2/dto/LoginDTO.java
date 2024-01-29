package com.mc.full17th2.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class LoginDTO {
    String userId;   // 입력한 사용자 아이디(또는 이메일)
    String userPassword;   // 입력한 사용자 비밀번호

    public LoginDTO(){}
    public LoginDTO(String id, String pw){
        userId=id;
        userPassword=pw;
    }
}
