package cn.edu.scut.sse.supply.pojo;

/**
 * @author Yukino Yukinoshita
 */

public class ResponseResult {

    private Integer code;
    private String msg;
    private Object data;

    public ResponseResult() {
    }

    public Integer getCode() {
        return code;
    }

    public ResponseResult setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResponseResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
        return data;
    }

    public ResponseResult setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
