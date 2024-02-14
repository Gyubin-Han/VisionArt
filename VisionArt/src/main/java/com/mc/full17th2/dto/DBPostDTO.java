package com.mc.full17th2.dto;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class DBPostDTO {
    int postId;
    LocalDateTime postDatetime;
    String title;
    String content;
    int views;
    int memberId;
    int postFieldId;
    int artFieldId;
    String nickname;
}
