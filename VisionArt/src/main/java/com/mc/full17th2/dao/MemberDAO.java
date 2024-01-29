package com.mc.full17th2.dao;

import java.io.IOException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Repository;

import com.mc.full17th2.dto.MemberDTO;
import com.mc.full17th2.dto.LoginDTO;
import com.mc.full17th2.dto.MyPageCheckCurrentPasswordDTO;

@Repository("member")
public class MemberDAO {
    // 로그인 시도 - 입력한 아이디(이메일)과 비밀번호가 일치하는지 확인하여 일치하면 true를 반환
    public int tryLogin(int idMode, LoginDTO loginData){
        // boolean result=false;
        Integer result=0;
        SqlSessionFactoryBuilder builder=new SqlSessionFactoryBuilder();
        SqlSessionFactory factory=null;
        SqlSession session=null;

        try{
            factory=builder.build(Resources.getResourceAsReader("mybatis-config.xml"));
        }catch(IOException e){
            e.printStackTrace();
            return 0;
        }
        session=factory.openSession();
        switch (idMode) {
            // idMode가 0인 경우, 해당 로그인을 이메일 로그인으로 처리
            case 0:
                result=(Integer)session.selectOne("member.loginIdCheck",loginData);
                if(result==null){
                    result=0;
                }
                break;
        
            // idMode가 1인 경우, 해당 로그인을 아이디 로그인으로 처리
            default:
                result=(Integer)session.selectOne("member.loginEmailCheck",loginData);
                if(result==null){
                    result=0;
                }
                break;
        }

        return result;
    }

    // 아이디 중복 여부 확인 - 입력받은 아이디가 이미 존재하는지(다른 데이터에 같은 값이 있는지) 확인하여,
    // 중복되면 true / 중복되지 않으면 false를 반환
    public boolean checkUserIdExists(String userId){
        SqlSessionFactoryBuilder builder=new SqlSessionFactoryBuilder();
        SqlSessionFactory factory=null;
        SqlSession session=null;

        try{
            factory=builder.build(Resources.getResourceAsReader("mybatis-config.xml"));
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }
        session=factory.openSession();

        if((int)session.selectOne("member.userIdExists",userId)!=0){
            return true;
        }else{
            return false;
        }
    }

    // 이메일 중복 여부 확인 - 입력받은 이메일이 이미 존재하는지(다른 데이터에 같은 값이 있는지) 확인하여,
    // 중복되면 true / 중복되지 않으면 false를 반환
    public boolean checkUserEmailExists(String userEmail){
        SqlSessionFactoryBuilder builder=new SqlSessionFactoryBuilder();
        SqlSessionFactory factory=null;
        SqlSession session=null;

        try{
            factory=builder.build(Resources.getResourceAsReader("mybatis-config.xml"));
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }
        session=factory.openSession();

        if((int)session.selectOne("member.userIdExists",userEmail)!=0){
            return true;
        }else{
            return false;
        }
    }

    // 회원가입 - 입력한 정보로 회원가입(데이터 삽입)을 수행
    // 불필요한 정보(이름, 주소, 전화번호 등)은 Service 단에서 수정하여 보내주는 것이 좋을 듯?
    //  - 현재 사용하지 않는(불필요) 정보들은 모두 Service 단에서 임의 삽입하여 보냄.
    public void registerMember(MemberDTO data){
        SqlSessionFactoryBuilder builder=new SqlSessionFactoryBuilder();
        SqlSessionFactory factory=null;
        SqlSession session=null;

        try{
            factory=builder.build(Resources.getResourceAsReader("mybatis-config.xml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        session=factory.openSession();

        // 삽입 시도
        session.insert("memeber.registerMember",data);

        return;
    }

    // 회원 정보 수정 - 사용자가 입력(수정)한 데이터를 저장
    public void memberEdit(MemberDTO data){
        SqlSessionFactoryBuilder builder=new SqlSessionFactoryBuilder();
        SqlSessionFactory factory=null;
        SqlSession session=null;

        try{
            factory=builder.build(Resources.getResourceAsReader("mybatis-config.xml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        session=factory.openSession();

        // 정보 수정 시도
        session.update("memeber.editMember",data);

        return;
    }

    // 회원 정보 수정 시, 사용자 정보를 받아옴.
    public MemberDTO getMemberData(int memberId){
        SqlSessionFactoryBuilder builder=new SqlSessionFactoryBuilder();
        SqlSessionFactory factory=null;
        SqlSession session=null;

        try{
            factory=builder.build(Resources.getResourceAsReader("mybatis-config.xml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        session=factory.openSession();

        return session.selectOne("member.getMemberData",memberId);
    }

    // 회원 정보 수정 시, 현재 비밀번호와 일치하는지 검증
    public Integer checkCurrentPassword(MyPageCheckCurrentPasswordDTO passwordData){
        SqlSessionFactoryBuilder builder=new SqlSessionFactoryBuilder();
        SqlSessionFactory factory=null;
        SqlSession session=null;

        try{
            factory=builder.build(Resources.getResourceAsReader("mybatis-config.xml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        session=factory.openSession();

        return session.selectOne("member.checkCurrentPassword",passwordData);
    }
}
