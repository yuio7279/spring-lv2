package com.sparta.post.dto;

import lombok.Getter;

@Getter
public class PostRequestDto {

    private Long id;
    private String title;
    private String userName;
    private String password;
    private String content;

    public PostRequestDto(String title, String userName, String password, String content) {

        this.title = title;
        this.userName = userName;
        this.password = password;
        this.content = content;
    }
}
