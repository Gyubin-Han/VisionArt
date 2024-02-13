package com.mc.full17th2.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mc.full17th2.dto.MemberDTO;
import com.mc.full17th2.service.JoinService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/join")
public class JoinController {
    @Autowired
    JoinService service;

    // 회원가입 페이지 뷰를 반환하는 메소드
    @GetMapping("")
    public String joinPage(){
        return "join";
    }

    // 아이디 중복을 체크하는 메소드
    // 공통 기능으로 MemberService에 요청
    @PostMapping("/checkIdExsist")
    @ResponseBody
    public HashMap<String,Object> checkIdExsist(@RequestParam String id){
        return service.checkIdExsist(id);
    }

    // 이메일 중복을 체크하는 메소드
    // 공통 기능으로 MemberService에 요청
    @PostMapping("/checkEmailExsist")
    @ResponseBody
    public HashMap<String,Object> checkEmailExsist(@RequestParam String email){
        return service.checkEmailExsist(email);
    }

    // 요청으로 보낸 회원가입 입력 데이터를 DB에 반영하고, 결과를 반환하는 메소드
    @PostMapping("/apply")
    @ResponseBody
    public HashMap<String,Object> joinApply(MemberDTO data, HttpServletRequest request){
        data.setAdmin(false);
        data.setPhoneNumber("010-");
        data.setAddress("-");
        data.setArtistId(1);
        data.setArtFieldId(1);

        return service.joinApply(data);
    }

}
