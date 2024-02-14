package com.mc.full17th2.dto;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class DBCommentDTO {
    int commentId;
    LocalDateTime commentDatetime;
    String commentContent;
    int memberId;
    int postId;
    String nickname;
}
