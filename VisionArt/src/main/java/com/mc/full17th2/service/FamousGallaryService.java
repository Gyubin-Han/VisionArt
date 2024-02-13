package com.mc.full17th2.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mc.full17th2.dao.FamousGallaryDAO;
import com.mc.full17th2.dto.CommentDTO;
import com.mc.full17th2.dto.FamousGallaryDTO;
import com.mc.full17th2.dto.FamousGallaryInsertDTO;

@Service("famousGallaryService")
public class FamousGallaryService {
    @Autowired
    FamousGallaryDAO dao;
    private final String uploadPath="./attachment/fg";
    
    // 글 목록 조회
    public HashMap<String,Object> getPostList(int page){
        int n=15, p=3;
        HashMap<String,Object> result=new HashMap<>();
        List<FamousGallaryDTO> list=null;
        int start=(page-1)*n;
        boolean status=false;

        if(start>=0){
            status=true;

            // 현재 명화갤러리에 작성된 글의 갯수를 조회
            int count=dao.getAllFamousGallaryPostCount();
    
            // 명화갤러리에 작성된 글 중 n개 씩 페이지 조회
            list=dao.getListFamousGallaryPosts(start, n);

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

    // 글 조회
    public HashMap<String,Object> getPost(int postId){
        HashMap<String,Object> result=new HashMap<>();

        // 해당 글의 내용과 댓글을 반환
        FamousGallaryDTO post=dao.getFamousGallaryPost(postId);
        dao.updatePostView(post.getPostId(),post.getViews()+1);
        post.setLikes(dao.getFamousGallaryPostLikes(postId));
        List<CommentDTO> comments=dao.getFamousGallaryComments(postId);

        result.put("post",post);
        result.put("comments",comments);

        return result;
    }

    // 파일을 디스크에 저장하는 메소드
    private void saveAttachmentFile(int postId, MultipartFile file, String filePath) throws IOException {
        File newFile=new File(filePath);
        System.out.println(file.getOriginalFilename());
        file.transferTo(newFile);
    }
    
    // 글 등록
    public void insertPost(FamousGallaryDTO data, MultipartFile file) throws Exception{
        // HashMap<String,Object> result=new HashMap<>();
        
        // 작성한 게시글을 DB에 저장
        // 저장한 데이터의 postId(PK)를 참고하여 작업 진행
        dao.insertFamousGallaryPost(data);
        // 명화갤러리 특화 데이터를 DB에 저장
        dao.insertFamousGallaryInformation(data);

        String fileName=data.getPostId()+"-"+file.getOriginalFilename();
        String filePath=uploadPath+"/"+fileName;

        // 파일 저장 메소드를 호출 - 해당 메소드에서 파일을 디스크에 저장하고, DB에 첨부한 게시글 정보와 파일명을 저장
        saveAttachmentFile(data.getPostId(),file,filePath);
        dao.insertFamousGallaryFile(data.getPostId(),fileName);

        // 최종적으로 게시글 저장에 성공하였음을 표시 - controller가 처리
    }

    // 글 수정
    public void updatePost(FamousGallaryDTO data, MultipartFile file) throws Exception{
        dao.updateFamousGallaryPost(data);
        dao.updateFamousGallaryInformation(data);

        if(!file.isEmpty()){
            String fileName=data.getPostId()+"-"+file.getOriginalFilename();
            String filePath=uploadPath+"/"+fileName;
            dao.deleteFamousGallaryFile(data.getPostId());
            saveAttachmentFile(data.getPostId(),file,filePath);
            dao.insertFamousGallaryFile(data.getPostId(),fileName);
        }
    }

    // 글 삭제
    public void deletePost(int memberId,int postId) throws Exception{
        dao.deleteFamousGallaryAllComments(postId);
        dao.deleteFamousGallaryLikes(postId);
        dao.deleteFamousGallaryFile(postId);
        dao.deleteFamousGallaryInformation(postId);
        dao.deleteFamousGallaryPost(postId,memberId);
    }
    
    // 댓글 등록
    public HashMap<String,Object> addComment(CommentDTO data){
        HashMap<String,Object> result=new HashMap<>();

        if(dao.insertFamousGallaryComment(data)==1){
            result.put("status",true);
            result.put("data",data);
        }else{
            result.put("status",false);
        }

        return result;
    }
    // 댓글 삭제
    public HashMap<String,Object> deleteComment(int commentId,String memberIdString){
        HashMap<String,Object> result=new HashMap<>();

        if(memberIdString==null){
            result.put("result","error0");
        }else if(dao.deleteFamousGallaryComment(commentId,Integer.parseInt(memberIdString))==1){
            result.put("result","ok");
        }else{
            result.put("result","error1");
        }

        return result;
    }

    // 전체 좋아요 수 조회
    public HashMap<String,Object> getPostLikes(int postId){
        HashMap<String,Object> result=new HashMap<>();

        result.put("likes",dao.getFamousGallaryPostLikes(postId));
        return result;
    }
    // 좋아요 여부 조회
    public HashMap<String,Object> getPostMemberLike(String memberIdString,String postIdString){
        HashMap<String,Object> result=new HashMap<>();

        System.out.println(memberIdString+" "+postIdString);
        if(memberIdString==null || postIdString==null){
            result.put("isLike",false);
        }
        else if(dao.getFamousGallaryMemberLike(Integer.parseInt(memberIdString), Integer.parseInt(postIdString))==0){
            result.put("isLike",false);
        }else{
            result.put("isLike",true);
        }

        return result;
    }
    // 좋아요 클릭 처리
    public HashMap<String,Object> clickPostLike(Integer memberId,Integer postId){
        HashMap<String,Object> result=new HashMap<>();

        if(dao.getFamousGallaryMemberLike(memberId,postId)==0){
            dao.insertFamousGallaryLike(memberId, postId);
            result.put("status",true);
            result.put("isLike",true);
        }else{
            dao.deleteFamousGallaryLike(memberId,postId);
            result.put("status",true);
            result.put("isLike",false);
        }

        return result;
    }
}
