package com.xtw.api.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class SellerInfo implements Serializable {
    private static final long serialVersionUID = -5632765715541613877L;
    private String id;

    private String username;

    private String password;

    private String openid;

    private Date createTime;

    private Date updateTime;


}