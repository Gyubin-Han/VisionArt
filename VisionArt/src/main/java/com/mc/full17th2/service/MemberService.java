package com.mc.full17th2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mc.full17th2.dao.MemberDAO;

// 회원관련 기능 중 공통적인 처리 로직을 가진 것을 담은 서비스 클래스
@Service("memberService")
public class MemberService {
    @Autowired
    MemberDAO dao;

    // 아이디 값이 중복된 값인지 확인하고, 중복 여부를 반환하는 메소드
    // 반환 값이 false인 경우, 해당 입력 값(아이디)은 중복되지 않았다는 의미(사용 가능)
    // (회원가입, 마이페이지-정보 수정 등에서 사용)
    public boolean isUserIdExists(String userId){
        int result=0;

        // DAO에 중복을 확인하는 쿼리를 실행
        // result=dao.checkUserIdExists(userId);
        result=dao.userIdExistsCheck(userId);

        // 결과 반환
        if(result==0){
            return false;
        }else{
            return true;
        }
    }

    // 이메일 값이 중복된 값인지 확인하고, 중복 여부를 반환하는 메소드
    // 반환 값이 false인 경우, 해당 입력 값(이메일)은 중복되지 않았다는 의미(사용 가능)
    // (회원가입, 마이페이지-정보 수정 등에서 사용)
    public boolean isUserEmailExists(String userEmail){
        int result=0;

        // DAO에 중복을 확인하는 쿼리를 실행
        // result=dao.checkUserEmailExists(userId);
        result=dao.userEmailExistsCheck(userEmail);

        // 결과 반환
        if(result==0){
            return false;
        }else{
            return true;
        }
    }

    // 관리자 회원 확인
    public boolean isAdmin(int memberId){
        if(dao.isAdmin(memberId)==1){
            return true;
        }else{
            return false;
        }
    }
}
