package com.mc.full17th2.service;

import java.util.List;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mc.full17th2.dao.NoticeDAO;
import com.mc.full17th2.dto.DBPostDTO;

@Service("noticeService")
public class NoticeService {
    @Autowired
    NoticeDAO dao;
    
    // 공지사항 목록 조회
    public HashMap<String,Object> getNoticePosts(int page){
        int n=15, p=3;
        HashMap<String,Object> result=new HashMap<>();
        List<DBPostDTO> list=null;
        int start=(page-1)*n;
        boolean status=false;

        if(start>=0){
            status=true;

            // 현재 공지사항 글의 갯수 조회
            int count=dao.getAllNoticePostCount();
    
            // 공지사항 글 목록 조회
            list=dao.getListNoticePosts(start, n);

            if(list!=null){
                result.put("startPage",page>p ? page-p : 1);
                result.put("endPage",(count/n)*1 > page+p ? page+p : (count%n==0 ? count/n : count/n*1+1));
                result.put("page",page);
                result.put("count",count);
            }
        }
        
        // 조회한 결과를 반환
        result.put("status",status);
        result.put("posts",list);

        return result;
    }

    // 공지사항 글 상세 조회
    public HashMap<String,Object> getNoticePost(int postId){
        HashMap<String,Object> result=new HashMap<>();
        DBPostDTO post=dao.getNoticePost(postId);
        result.put("post",post);

        return result;
    }

    // 공지사항 글 등록
    public HashMap<String,Object> insertNoticePost(DBPostDTO data) throws Exception{
        HashMap<String,Object> result=new HashMap<>();

        dao.insertNoticePost(data);

        return result;
    }

    // 공지사항 글 수정
    public void updateNoticePost(DBPostDTO data) throws Exception{
        if(dao.updateNoticePost(data)!=1){
            throw new Exception();
        }
    }

    // 공지사항 글 삭제
    public void deleteNoticePost(int memberId, int postId) throws Exception{
        if(dao.deleteNoticePost(postId,memberId)!=1){
            throw new Exception();
        }
    }
}
