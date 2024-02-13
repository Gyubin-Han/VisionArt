package com.mc.full17th2.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mc.full17th2.dao.MemberDAO;
import com.mc.full17th2.dao.TempCommentDAO;
import com.mc.full17th2.dao.TempPostDAO;
import com.mc.full17th2.dto.MemberDTO;
import com.mc.full17th2.dto.MyPageCheckCurrentPasswordDTO;
import com.mc.full17th2.dto.TempMyPageCommentDTO;
import com.mc.full17th2.dto.TempMyPagePostDTO;
import com.mc.full17th2.dto.TempMyPageQueryDTO;

@Service("myPageService")
public class MyPageService {
    @Autowired
    MemberDAO memberDao;
    @Autowired
    TempPostDAO tempPostDao;
    @Autowired
    TempCommentDAO tempCommentDao;
    @Autowired
    PostService postService;
    @Autowired
    CommentService commentService;

    // 마이페이지의 메인 화면에 접속하였을 때 처리되는 서비스 로직을 담은 메소드
    public HashMap<String,Object> myPageIndex(int memberId){
        HashMap<String,Object> result=new HashMap<String,Object>();

        // 최근 작성했던 글 5개를 받아옴.
        TempMyPageQueryDTO query=new TempMyPageQueryDTO(memberId,0,5);
        List<TempMyPagePostDTO> myPost=tempPostDao.getMyAllPosts(query);
        List<TempMyPageCommentDTO> myComment=tempCommentDao.getMyAllComments(query);

        // 받아온 글, 댓글 목록을 반환 결과에 포함시킴.
        result.put("posts",myPost);
        result.put("comments",myComment);

        return result;
    }
    
    public HashMap<String,Object> getMyAllPost(int memberId, int page){
        int n=10, p=3;
        List<TempMyPagePostDTO> myPost=null;
        HashMap<String,Object> result=new HashMap<>();
        boolean status=false;
        int start=(page-1)*n;

        if(start>=0){
            status=true;
            Integer count=tempPostDao.getMyAllPostCount(memberId);
            TempMyPageQueryDTO query=new TempMyPageQueryDTO(memberId,start,n);
            myPost=tempPostDao.getMyAllPosts(query);

            System.out.println(count);
            if(count>0){
                result.put("startPage",page > p ? page-p : 1);
                result.put("endPage",(count/n)*1 > page+p ? page+p : (count%n==0 ? count/n : count/n*1+1));
                result.put("page",page);
                result.put("count",count);
            }else{
                myPost=null;
            }
        }

        result.put("status",status);
        result.put("posts",myPost);

        return result;
    }

    // 사용자의 댓글 목록을 받아옴.
    public HashMap<String,Object> getMyAllComment(int memberId, int page){
        int n=10, p=5;
        List<TempMyPageCommentDTO> myComment=null;
        HashMap<String,Object> result=new HashMap<>();
        boolean status=false;
        int start=(page-1)*n;

        if(start>=0){
            status=true;
            Integer count=tempCommentDao.getMyAllCommentCount(memberId);
            TempMyPageQueryDTO query=new TempMyPageQueryDTO(memberId,start,n);
            myComment=tempCommentDao.getMyAllComments(query);

            System.out.println(count);
            if(count>0){
                result.put("startPage",page > p ? page-p : 1);
                result.put("endPage",(count/n)*1 > page+p ? page+p : (count%n==0 ? count/n : count/n*1+1));
                result.put("page",page);
                result.put("count",count);
            }else{
                myComment=null;
            }
        }

        result.put("status",status);
        result.put("comments",myComment);

        return result;
    }

    // 마이페이지 - 정보 수정 페이지의 내용을 채움.
    public MemberDTO getMemberData(int memberId){
        return memberDao.getMemberData(memberId);
    }

    // 마이페이지 - 사용자가 입력한 비밀번호 값이 현재 비밀번호와 일치하는지 확인하는 메소드
    public HashMap<String,String> checkCurrentPassword(MyPageCheckCurrentPasswordDTO passwordData){
        HashMap<String,String> result=new HashMap<>();

        // MemberDAO를 통해 사용자 고유번호와 비밀번호가 일치하는지 검증
        if(memberDao.checkCurrentPassword(passwordData)==0){
            result.put("exists","false");
        }else{
            result.put("exists","true");
        }

        return result;
    }

    // 마이페이지의 수정된 정보를 저장하는 서비스 로직을 담은 메소드
    public String userInfoEdit(MemberDTO editInfo){
        System.out.println(editInfo.getMemberId()+" "+editInfo.getId()+" "+editInfo.getEmail()+" "+editInfo.getNickname()+" "+editInfo.getPassword()+" "+editInfo.getArtistId()+" "+editInfo.getArtFieldId());
        System.out.println(memberDao.editMember(editInfo));
        
        return "ok";
    }
}
