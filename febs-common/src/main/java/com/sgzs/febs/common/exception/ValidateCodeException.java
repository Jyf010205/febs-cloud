package com.sgzs.febs.common.exception;

/**
 * @author: jianyufeng
 * @description:
 * @date: 2020/6/10 14:16
 */
public class ValidateCodeException extends Exception{

    private static final long serialVersionUID = 7514854456967620043L;

    public ValidateCodeException(String message){
        super(message);
    }
}
