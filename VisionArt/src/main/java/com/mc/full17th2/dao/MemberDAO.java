package com.mc.full17th2.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.mc.full17th2.dto.LoginDTO;
import com.mc.full17th2.dto.LoginResultDTO;
import com.mc.full17th2.dto.MemberDTO;
import com.mc.full17th2.dto.MyPageCheckCurrentPasswordDTO;

@Repository("member")
@Mapper
public interface MemberDAO {
    LoginResultDTO loginIdCheck(LoginDTO data);
    LoginResultDTO loginEmailCheck(LoginDTO data);
    int userIdExistsCheck(String id);
    int userEmailExistsCheck(String email);
    int joinMember(MemberDTO data);
    int editMember(MemberDTO data);
    MemberDTO getMemberData(int memberId);
    int checkCurrentPassword(MyPageCheckCurrentPasswordDTO data);
    int isAdmin(int memberId);
}
