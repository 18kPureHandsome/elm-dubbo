package com.xtw.api.vo;

import lombok.Data;

/**
 * Created by xiaotianwen on 2019/3/3.
 * 回调信息vo
 */
@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultVo<T> {

    private Integer code = 0;

    private String msg = "";

    private T data;

    public ResultVo(){

    }

    public ResultVo(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
