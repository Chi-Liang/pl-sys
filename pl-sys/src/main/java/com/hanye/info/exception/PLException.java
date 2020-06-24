package com.hanye.info.exception;

public class PLException extends RuntimeException{
	
	private static final long serialVersionUID = -5193973915220337753L;
	
	private PLExceptionCode code;

    public PLException(PLExceptionCode code) {
        super(code.getMsg());
        this.code = code;
    }

    public PLExceptionCode getCode() {
        return code;
    }

    public void setCode(PLExceptionCode code) {
        this.code = code;
    }
}
