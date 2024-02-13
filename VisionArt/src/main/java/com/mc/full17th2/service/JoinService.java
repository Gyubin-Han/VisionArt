package com.mc.full17th2.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mc.full17th2.dao.MemberDAO;
import com.mc.full17th2.dto.MemberDTO;

@Service("joinService")
public class JoinService {
    @Autowired
    MemberDAO dao;
    @Autowired
    MemberService memberService;
    
    // 중복 아이디를 확인하는 메소드
    // 공통 기능으로 MemberService의 메소드를 사용하여 처리
    public HashMap<String,Object> checkIdExsist(String id){
        HashMap<String,Object> result=new HashMap<String,Object>();
        boolean isExists=memberService.isUserIdExists(id);
        result.put("result",isExists);

        return result; 
    }

    // 중복 이메일을 확인하는 메소드
    // 공통 기능으로 MemberService의 메소드를 사용하여 처리
    public HashMap<String,Object> checkEmailExsist(String email){
        HashMap<String,Object> result=new HashMap<String,Object>();
        boolean isExsist=memberService.isUserEmailExists(email);
        result.put("result",isExsist);

        return result; 
    }

    // 회원가입을 처리하는 메소드
    // (이미 중복 검사 등 데이터 무결성을 위한 조치는 다 했다는 가정)
    public HashMap<String,Object> joinApply(MemberDTO data){
        HashMap<String,Object> result=new HashMap<>();
        int status=dao.joinMember(data);

        if(status==1){
            result.put("result",true);
        }else{
            result.put("result",false);
        }
        
        return result;
    }
}
