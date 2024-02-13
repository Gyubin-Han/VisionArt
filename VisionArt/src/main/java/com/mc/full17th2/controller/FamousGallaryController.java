package com.mc.full17th2.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.mc.full17th2.dto.CommentDTO;
import com.mc.full17th2.dto.FamousGallaryDTO;
import com.mc.full17th2.dto.FamousGallaryInsertDTO;
import com.mc.full17th2.service.FamousGallaryService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/fg")
public class FamousGallaryController {
    @Autowired
    private PlatformTransactionManager transactionManager;
    @Autowired
    FamousGallaryService service;

    @GetMapping("")
    public ModelAndView listPost(HttpServletRequest request){
        ModelAndView mv=new ModelAndView();

        int page=1;
        if(request.getParameter("page")!=null){
            page=Integer.parseInt((String)request.getParameter("page"));
        }

        mv.addObject("result", service.getPostList(page));
        mv.setViewName("fgList");
        return mv;
    }
    @GetMapping("/")
    public String listPostRedirect(){
        return "redirect:/fg";
    }

    @GetMapping("/{num}")
    public ModelAndView getPost(HttpServletRequest request, @PathVariable int num){
        ModelAndView mv=new ModelAndView();

        mv.setViewName("fgpostId");
        mv.addObject("result",service.getPost(num));
        return mv;
    }

    @GetMapping("/write")
    public ModelAndView writePost(){
        ModelAndView mv=new ModelAndView();

        mv.setViewName("fgWrite");
        return mv;
    }

    @PostMapping("/write/save")
    public ModelAndView saveWritePost(@RequestParam("image") MultipartFile file, FamousGallaryInsertDTO formData){
        ModelAndView mv=new ModelAndView();
        TransactionStatus status=transactionManager.getTransaction(new DefaultTransactionDefinition());
        boolean result=false;

        System.out.println("명화갤러리 - 글 등록 요청 받음!");
        try{
            FamousGallaryDTO data=new FamousGallaryDTO();

            data.setArtFieldId(formData.getCategory());
            data.setArtistId(formData.getAuthor());
            data.setMemberId(formData.getMemberId());
            data.setTitle(formData.getTitle());
            data.setContent(formData.getContent());

            service.insertPost(data,file);

            // 데이터 처리 완료
            transactionManager.commit(status);
            result=true;
        }
        catch(Exception e){
            transactionManager.rollback(status);
            e.printStackTrace();
        }

        mv.addObject("result",result);
        mv.setViewName("fgWriteResult");
        return mv;
    }
    
    @GetMapping("/edit/{num}")
    public ModelAndView editPost(@PathVariable int num){
        ModelAndView mv=new ModelAndView();

        mv.setViewName("fgEditPostId");
        mv.addObject("post",service.getPost(num));

        return mv;
    }
    @PostMapping("/edit/save")
    public ModelAndView saveEditPost(@RequestParam("image") MultipartFile file, FamousGallaryInsertDTO formData){
        ModelAndView mv=new ModelAndView();
        TransactionStatus status=transactionManager.getTransaction(new DefaultTransactionDefinition());
        boolean result=false;

        System.out.println(formData.getPostId());
        System.out.println(formData.getMemberId());
        System.out.println(formData.getAuthor());
        try{
            FamousGallaryDTO data=new FamousGallaryDTO();

            data.setPostId(formData.getPostId());
            data.setMemberId(formData.getMemberId());
            data.setArtFieldId(formData.getCategory());
            data.setArtistId(formData.getAuthor());
            data.setTitle(formData.getTitle());
            data.setContent(formData.getContent());

            service.updatePost(data,file);

            // 데이터 처리 완료
            transactionManager.commit(status);
            result=true;
        }
        catch(Exception e){
            transactionManager.rollback(status);
            e.printStackTrace();
        }

        mv.addObject("result",result);
        mv.setViewName("fgWriteResult");
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
            service.deletePost(Integer.parseInt(memberIdString),Integer.parseInt(postIdString));

            // 삭제 완료
            transactionManager.commit(status);
            result.put("result","ok");
        }
        catch(Exception e){
            transactionManager.rollback(status);
            result.put("result","error");
        }

        return result;
    }

    @PostMapping("/comment/save")
    @ResponseBody
    public HashMap<String,Object> saveComment(CommentDTO data){
        return service.addComment(data);
    }

    @PostMapping("/comment/delete")
    @ResponseBody
    public HashMap<String,Object> deleteComment(int commentId, HttpServletRequest request){
        return service.deleteComment(commentId,(String)request.getSession().getAttribute("memberId"));
    }

    @PostMapping("/like")
    @ResponseBody
    public HashMap<String,Object> getPostLikes(int postId){
        return service.getPostLikes(postId);
    }
    @PostMapping("/isLike")
    @ResponseBody
    public HashMap<String,Object> getPostMemberLike(HttpServletRequest request){
        return service.getPostMemberLike((String)request.getSession().getAttribute("memberId"),(String)request.getParameter("postId"));
    }
    @PostMapping("/clickLike")
    @ResponseBody
    public HashMap<String,Object> clickMemberPostLike(@RequestParam int postId, HttpServletRequest request){
        String memberId=(String)request.getSession().getAttribute("memberId");
        return service.clickPostLike(Integer.parseInt(memberId),postId);
    }
}
