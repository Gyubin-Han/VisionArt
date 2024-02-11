package com.mc.full17th2.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class LoginResultDTO {
    Integer memberId;
    String nickname;
}
