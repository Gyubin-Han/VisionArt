package com.mc.full17th2.dto;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class FamousGallaryDTO {
    int postId;
    int memberId;
    String nickname;
    int artFieldId;
    String artFieldName;
    int artistId;
    String artistName;
    LocalDateTime postDatetime;
    String title;
    String content;
    int views;
    String postAttPath;
    int likes;
}
