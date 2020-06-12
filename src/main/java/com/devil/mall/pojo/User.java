package com.devil.mall.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
* Created by ESC Han on 2020/05/15
*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    public User(String username, String password, String email,Integer role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    private Integer id;

    private String username;

    private String password;

    private String email;

    private String phone;

    private String question;

    private String answer;

    private Integer role;

    private Date createTime;

    private Date updateTime;
}