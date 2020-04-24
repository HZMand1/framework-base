package com.base.utils.excetion;

/**
 * TODO
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2020/4/3 9:00
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2020
 */
public class FrameworkBaseException extends RuntimeException{
    private static final long serialVersionUID = 6517393358388543635L;

    public FrameworkBaseException(){
        super();
    }

    public FrameworkBaseException(String message) {
        super(message);
    }

    public FrameworkBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public FrameworkBaseException(Throwable cause) {
        super(cause);
    }
}
