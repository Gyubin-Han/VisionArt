package com.mc.full17th2.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.mc.full17th2.dto.DBPostDTO;

@Repository("noticeDao")
@Mapper
public interface NoticeDAO {
    int getAllNoticePostCount();
    List<DBPostDTO> getListNoticePosts(int start,int limit);
    DBPostDTO getNoticePost(int postId);
    int insertNoticePost(DBPostDTO data);
    int updateNoticePost(DBPostDTO data);
    int deleteNoticePost(int postId,int memberId);
}
