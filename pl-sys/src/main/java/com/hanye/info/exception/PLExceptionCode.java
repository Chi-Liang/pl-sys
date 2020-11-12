package com.hanye.info.exception;

public enum PLExceptionCode {
	
	DATA_NOT_FOUND(404, "查無資料"),
    CATEGORY_IS_USED(600, "影片群組已使用，無法刪除"),
    CATEGORY_NOT_FOUND(601, "無影片群組，請先建立影片群組，再執行新增作業"),
    LOGIN_AGAIN(888, "請重新登入"),
    SYSTEM_ERROR(999, "系統錯誤"),
    DUPLICATE_ACCOUNT(666, "帳號重複"),
    DUPLICATE_EMAIL(777, "email重複");

    private int code;
    private String msg;

    PLExceptionCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
