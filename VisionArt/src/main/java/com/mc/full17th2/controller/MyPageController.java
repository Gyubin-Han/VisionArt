package com.mc.full17th2.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mc.full17th2.dto.CommentDTO;
import com.mc.full17th2.dto.MemberDTO;
import com.mc.full17th2.dto.MyPageCheckCurrentPasswordDTO;
import com.mc.full17th2.dto.PostDTO;
import com.mc.full17th2.service.CommentService;
import com.mc.full17th2.service.MyPageService;
import com.mc.full17th2.service.PostService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/mypage")
public class MyPageController {
    @Autowired
    private MyPageService myPageService;
    // @Autowired
    // private PostService postService;
    // @Autowired
    // private CommentService commentService;

    // 마이페이지를 반환하는 메소드 (반환되는 것 : 사용자의 글, 댓글 정보)
    @GetMapping("")
    public ModelAndView myPageIndex(HttpServletRequest request){
        ModelAndView mv=new ModelAndView();
        HttpSession session=request.getSession();

        Integer memberId;
        // 로그인 상태인지 확인
        if(session.getAttribute("memberId")==null){
            mv.setViewName("loginPlease");
            return mv;
        }
        
        // 로그인 중인 세션 값을 int형으로 변환 후 대입
        memberId=Integer.parseInt((String)session.getAttribute("memberId"));

        // service로부터 필요한 정보(최근 작성된 글/댓글 5개)를 받아옴.
        HashMap<String,Object> result=myPageService.myPageIndex(memberId);

        // service로부터 받아온 객체를 model 객체에 추가
        mv.addObject("result",result);
        mv.setViewName("mypage");
        return mv;
    }

    @GetMapping("/")
    public String myPageIndex2() {
        // '/mypage' url로 접속되도록 redirect 처리
        return "redirect:/mypage";
    }

    // 사용자가 이제까지 작성했던 글들을 모두 목록에 볼 수 있는 페이지를 반환하는 메소드
    @GetMapping("/post")
    public ModelAndView myPagePostsList(HttpServletRequest request){
        ModelAndView mv=new ModelAndView();
        HttpSession session=request.getSession();

        Integer memberId;
        // 로그인 상태인지 확인
        if(session.getAttribute("memberId")==null){
            mv.setViewName("loginPlease");
            return mv;
        }
        
        // 로그인 중인 세션 값을 int형으로 변환 후 대입
        memberId=Integer.parseInt((String)session.getAttribute("memberId"));

        // 조회하려는 페이지 받아옴.
        int page=1;
        // page 값이 null인지 구별 (null인 경우, 첫 번째 페이지를 보여줌.)
        if(request.getParameter("page")!=null){
            page=Integer.parseInt((String)request.getParameter("page"));
        }

        mv.addObject("result",myPageService.getMyAllPost(memberId,page));
        mv.setViewName("mypage_posts");
        return mv;
    }

    // 사용자가 이제까지 작성했던 댓글들을 모두 목록에 볼 수 있는 페이지를 반환하는 메소드
    @GetMapping("/comment")
    public ModelAndView myPageCommentsList(HttpServletRequest request){
        ModelAndView mv=new ModelAndView();
        HttpSession session=request.getSession();

        Integer memberId;
        // 로그인 상태인지 확인
        if(session.getAttribute("memberId")==null){
            mv.setViewName("loginPlease");
            return mv;
        }
        
        // 로그인 중인 세션 값을 int형으로 변환 후 대입
        memberId=Integer.parseInt((String)session.getAttribute("memberId"));

        // 조회하려는 페이지 받아옴.
        int page=1;
        // page 값이 null인지 구별 (null인 경우, 첫 번째 페이지를 보여줌.)
        if(request.getParameter("page")!=null){
            page=Integer.parseInt((String)request.getParameter("page"));
        }

        mv.addObject("result",myPageService.getMyAllComment(memberId,page));
        mv.setViewName("mypage_comments");
        return mv;
    }

    // 내 정보를 수정하는 페이지를 반환하는 메소드
    @GetMapping("/edit")
    public ModelAndView myPageEdit(HttpServletRequest request){
        ModelAndView mv=new ModelAndView();
        HttpSession session=request.getSession();

        String memberIdString=(String)session.getAttribute("memberId");
        if(memberIdString!=null){
            mv.addObject("memberData", myPageService.getMemberData(Integer.parseInt(memberIdString)));
        }
        mv.setViewName("mypage_edit");

        return mv;
    }

    // 정보 수정 시, 현재 비밀번호와 일치하는지 확인하는 메소드
    // JSON 형식으로 데이터를 주고 받음. -> 비동기 통신 필요
    @PostMapping("/checkCurrentPassword")
    @ResponseBody
    public HashMap<String,String> editCheckCurrentPassword(@RequestParam String password, HttpServletRequest request){
        HttpSession session=request.getSession();
        String memberIdString=(String)session.getAttribute("memberId");

        if(memberIdString==null){
            HashMap<String,String> errorResult=new HashMap<>();
            errorResult.put("exists","error");

            return errorResult;
        }

        MyPageCheckCurrentPasswordDTO passwordData=new MyPageCheckCurrentPasswordDTO(Integer.parseInt(memberIdString),password);
        return myPageService.checkCurrentPassword(passwordData);
    }

    // 수정한 정보를 반영하는 페이지를 반환하는 메소드
    @PostMapping("/editApply")
    @ResponseBody
    public HashMap<String,Object> myPageEditApply(HttpServletRequest request,MemberDTO editData) {
        // ModelAndView mv=new ModelAndView();
        HashMap<String,Object> result=new HashMap<>();
        HttpSession session=request.getSession();

        String memberIdString=(String)session.getAttribute("memberId");
        if(memberIdString==null){
            result.put("status","error");
            result.put("message","잘못된 요청");
        }else{            
            // Service로 입력 데이터를 넘긴 후, 결과 값을 받아옴.
            editData.setMemberId(Integer.parseInt(memberIdString));
            result.put("status",myPageService.userInfoEdit(editData));
        }

        return result;
    }
    
}
