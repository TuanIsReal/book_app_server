package com.anhtuan.bookapp.response;

public class Response {
    private int code = 1;

    private Object data;

    public Response() {

    }

    public Response(int code, Object dataResponse) {
        this.code = code;
        this.data = dataResponse;
    }

    public Response(int code) {
        super();
        this.code = code;
    }

    public Response(Object data) {
        super();
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Response{");
        sb.append("code=").append(code);
        sb.append(", data=").append(data);
        sb.append('}');
        return sb.toString();
    }
}
