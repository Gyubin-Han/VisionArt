package com.mc.full17th2.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class MemberDTO {
    int memberId;   // 사용자 고유번호
    String id;   // 사용자 아이디
    String email;   // 사용자 이메일
    String password;   // 사용자 비밀번호
    LocalDateTime signupDate;   // 가입일
    LocalDate birthdate;   // 생일
    String nickname;   // 닉네임
    boolean isAdmin;   // 관리자 여부
    int artistId;   // 좋아하는 예술가의 고유번호
    int artFieldId;   // 좋아하는(관심있는) 미술 분야의 고유번호
    String address;   // 주소 x
    String phoneNumber;   // 전화번호 x
}
