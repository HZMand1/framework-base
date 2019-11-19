package com.base.user.model;

import com.base.vo.IfQuery;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TODO
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2019/11/19 10:28
 * @copyright XXX Copyright (c) 2019
 */
@Data
@Table(name = "user_info")
public class UserEntity extends IfQuery {

    @Id
    @Column(name = "id")
    private String id;

    /**
     * 账号
     */
    @Column(name = "userAccount")
    private String userAccount;

    /**
     * 姓名
     */
    @Column(name = "userName")
    private String userName;

    /**
     * 密码
     */
    @Column(name = "password")
    private String password;
}
