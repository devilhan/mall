package com.devil.mall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Hanyanjiao
 * @date 2020/5/15
 */
@Data
public class UserRegisterForm {

    //@NotBlank   //用户字符String 判断空格
    //@NotNull
    //@NotEmpty  //用户集合

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String email;
}
