package team.tb.common;

import com.mysql.cj.protocol.MessageSender;

public class Result {
    private int code;   //200正常, 非200异常
    private String msg;
    private Object data;
    private int count; // 数据条数，方便分页

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static Result succ(Object data) {
        return succ(200, "成功", data, 0);
    }

    public static Result succ(String msg) {
        return succ(200, msg, null, 0);
    }

    /**
     * 主要用于返回layui规定格式的数据
     * @param code 状态码为0
     * @param data 返回的数据
     * @param count 数据总数，方便layui表格计算页数
     * @return
     */
    public static Result succ(int code, Object data, int count){
        return succ(code, "请求成功", data, count);
    }

    private static Result succ(int code, String msg, Object data, int count) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        result.setCount(count);
        return result;
    }

    public static Result fail(String msg) {
        return fail(400, msg,null);
    }

    public static Result fail(int code, String msg, Object data) {
        Result m = new Result();
        m.setCode(code);
        m.setMsg(msg);
        m.setData(data);
        return m;
    }
}
