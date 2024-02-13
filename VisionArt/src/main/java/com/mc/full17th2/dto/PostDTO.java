package com.mc.full17th2.dto;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class PostDTO {
    int post_id;
    LocalDateTime post_datetime;
    String title;
    String content;
    int views;
    int member_id;
    int post_field_id;
    int art_field_id;
}
