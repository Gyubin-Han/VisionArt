package com.mc.full17th2.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mc.full17th2.dto.DBPostDTO;
import com.mc.full17th2.dto.FamousGallaryDTO;
import com.mc.full17th2.dto.FamousGallaryInsertDTO;
import com.mc.full17th2.service.MemberService;
import com.mc.full17th2.service.NoticeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    private PlatformTransactionManager transactionManager;
    @Autowired
    NoticeService service;
    @Autowired
    MemberService memberService;

    @GetMapping("")
    public ModelAndView noticeIndex(HttpServletRequest request){
        ModelAndView mv=new ModelAndView();

        int page=1;
        if(request.getParameter("page")!=null){
            page=Integer.parseInt((String)request.getParameter("page"));
        }

        mv.addObject("result",service.getNoticePosts(page));
        mv.setViewName("noticeList");
        return mv;
    }
    @GetMapping("/")
    public String noticeIndex2(){
        return "redirect:/notice";
    }

    @GetMapping("/{num}")
    public ModelAndView getNoticePost(HttpServletRequest request, @PathVariable int num){
        ModelAndView mv=new ModelAndView();
        
        mv.addObject("result",service.getNoticePost(num));
        mv.setViewName("noticeRead");
        return mv;
    }

    @GetMapping("/write")
    public ModelAndView writePost(HttpServletRequest request){
        ModelAndView mv=new ModelAndView();
        HttpSession session=request.getSession();

        if(session.getAttribute("memberId")!=null){
            mv.addObject("isAdmin",memberService.isAdmin(Integer.parseInt((String)session.getAttribute("memberId"))));
        }else{
            mv.addObject("isAdmin",false);
        }
        mv.setViewName("noticeWrite");
        return mv;
    }

    @PostMapping("/write/save")
    public ModelAndView saveWritePost(DBPostDTO data, HttpServletRequest request){
        ModelAndView mv=new ModelAndView();
        HttpSession session=request.getSession();
        TransactionStatus status=transactionManager.getTransaction(new DefaultTransactionDefinition());
        String memberIdString=(String)session.getAttribute("memberId");
        boolean result=false;

        if(memberIdString!=null){
            data.setMemberId(Integer.parseInt(memberIdString));
            try{
                service.insertNoticePost(data);

                // 데이터 처리 완료
                transactionManager.commit(status);
                result=true;
            }
            catch(Exception e){
                transactionManager.rollback(status);
                e.printStackTrace();
            }
        }

        mv.addObject("result",result);
        mv.setViewName("noticeWriteResult");
        return mv;
    }

    @GetMapping("/edit/{num}")
    public ModelAndView editPost(@PathVariable int num, HttpServletRequest request){
        ModelAndView mv=new ModelAndView();
        HttpSession session=request.getSession();

        if(session.getAttribute("memberId")!=null){
            mv.addObject("isAdmin",memberService.isAdmin(Integer.parseInt((String)session.getAttribute("memberId"))));
        }else{
            mv.addObject("isAdmin",false);
        }

        mv.setViewName("noticeWrite");
        mv.addObject("result",service.getNoticePost(num));

        return mv;
    }
    @PostMapping("/edit/save")
    public ModelAndView saveEditPost(DBPostDTO data,HttpServletRequest request){
        ModelAndView mv=new ModelAndView();
        TransactionStatus status=transactionManager.getTransaction(new DefaultTransactionDefinition());
        boolean result=false;

        try{
            data.setMemberId(Integer.parseInt((String)request.getSession().getAttribute("memberId")));
            service.updateNoticePost(data);

            // 데이터 처리 완료
            transactionManager.commit(status);
            result=true;
        }
        catch(Exception e){
            transactionManager.rollback(status);
        }

        mv.addObject("result",result);
        mv.setViewName("noticeWriteResult");
        return mv;
    }

    @PostMapping("/delete")
    @ResponseBody
    public HashMap<String,Object> deletePost(HttpServletRequest request){
        HashMap<String,Object> result=new HashMap<>();
        TransactionStatus status=transactionManager.getTransaction(new DefaultTransactionDefinition());
        
        String memberIdString=(String)request.getSession().getAttribute("memberId");
        String postIdString=(String)request.getParameter("postId");
        try{
            service.deleteNoticePost(Integer.parseInt(memberIdString),Integer.parseInt(postIdString));
            
            // 삭제 완료
            transactionManager.commit(status);
            result.put("result","ok");
        }
        catch(Exception e){
            e.printStackTrace();
            transactionManager.rollback(status);
            result.put("result","error");
        }

        return result;
    }
}
