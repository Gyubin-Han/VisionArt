package com.mc.full17th2.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mc.full17th2.dao.MemberDAO;
import com.mc.full17th2.dto.LoginDTO;
import com.mc.full17th2.dto.LoginResultDTO;

@Service
public class LoginService {
    @Autowired
    MemberDAO dao;
    
    // 사용자가 입력한 로그인 정보를 사용하여 로그인을 시도하는 메소드
    public HashMap<String,String> loginCheck(LoginDTO loginData){
        HashMap<String,String> result=new HashMap<>();

        // 만약, 들어온 값이 정상적이지 않은 값(빈 값)이라면 오류를 반환
        if(loginData.getUserId().equals("") || loginData.getUserPassword().equals("")){
            result.put("result","error");
            result.put("error_code","01");
            result.put("error_msg","잘못된 값이 입력되었습니다.");
        }

        LoginResultDTO loginResult=null;
        // 아이디 값에 '@' 문자가 들어가 있는지 확인하고 로그인 종류를 구분하여 처리
        if(loginData.getUserId().indexOf("@")==-1){
            // queryResult=dao.tryLogin(0, loginData);
            loginResult=dao.loginIdCheck(loginData);
        }else{
            // queryResult=dao.tryLogin(1,loginData);
            loginResult=dao.loginEmailCheck(loginData);
        }

        // 로그인 실행 질의 결과가 true인 경우, 결과를 success로 분류
        if(loginResult!=null){
            result.put("result","success");
            result.put("memberId",loginResult.getMemberId().toString());
            result.put("nickname",loginResult.getNickname());
        // 로그인에 실패한 경우, 결과를 error로 분류하고, 상세 오류 정보를 함께 삽입
        }else{
            result.put("result","error");
            result.put("error_code","02");
            result.put("error_msg","계정이 없거나, 아이디(이메일) 또는 비밀번호가 잘못되었습니다.");
        }

        return result;
    }
}
