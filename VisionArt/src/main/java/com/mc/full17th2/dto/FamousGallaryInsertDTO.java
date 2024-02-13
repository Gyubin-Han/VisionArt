package com.mc.full17th2.dto;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Component
@Data
public class FamousGallaryInsertDTO {
    int postId;
    int memberId;
    int category;
    int author;
    String title;
    String content;
}
