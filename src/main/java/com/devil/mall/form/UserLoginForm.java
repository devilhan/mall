package com.devil.mall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Hanyanjiao
 * @date 2020/5/21
 */

@Data
public class UserLoginForm {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
