package com.mc.full17th2.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mc.full17th2.service.MemberService;
import com.mc.full17th2.dto.LoginDTO;
import com.mc.full17th2.service.LoginService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    LoginService service;
    @Autowired
    MemberService memberService;

    @GetMapping("/login")
    public String login(HttpServletRequest request){
        HttpSession session=request.getSession();
        System.out.println(session.getAttribute("memberId"));
        
        return "login";
    }
    @PostMapping("/login/loginCheck")
    @ResponseBody
    public HashMap<String,String> loginCheck(LoginDTO loginData,HttpServletRequest request){
        // ModelAndView mv=new ModelAndView();
        System.out.println(loginData.getUserId()+" "+loginData.getUserPassword());
        HashMap<String,String> result=service.loginCheck(loginData);

        String result_code=result.get("result");
        if(result_code.equals("success")){
            HttpSession session=request.getSession();
            session.setAttribute("memberId",result.get("memberId"));
        }

        return result;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session=request.getSession();

        if(session.getAttribute("memberId")!=null){
            session.setAttribute("memberId",null);
        }
        return "logout";
    }

}
