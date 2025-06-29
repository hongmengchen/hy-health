package cn.rj.hyhealthbackend.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 陈亮
 * <p>
 * Msg类
 * 封装返回结果，其中包含了响应码，响应消息，响应成功标志，响应数据，
 * 并实现了创建成功响应、失败响应以及添加响应数据等方法
 */
public class Msg {
    /*状态码*/
    private Integer code;
    /*响应消息*/
    private String message;
    /*响应是否成功标志*/
    private boolean success;
    /*响应的数据*/
    private Map<String, Object> data = new HashMap<>();

    /**
     * 创建成功响应
     *
     * @return Msg
     */
    public static Msg success() {
        Msg msg = new Msg();
        msg.setCode(20000);
        msg.setMessage("响应成功");
        msg.setSuccess(true);
        return msg;
    }

    /**
     * 创建失败响应
     *
     * @return Msg
     */
    public static Msg fail() {
        Msg msg = new Msg();
        msg.setCode(10000);
        msg.setMessage("响应失败");
        msg.setSuccess(false);
        return msg;
    }

    /**
     * 添加响应数据
     *
     * @return Msg
     */
    public Msg data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    /**
     * 添加响应码
     *
     * @return Msg
     */
    public Msg code(Integer code) {
        this.setCode(code);
        return this;
    }

    /**
     * 添加响应消息
     *
     * @return Msg
     */
    public Msg mess(String str) {
        this.setMessage(str);
        return this;
    }

    // getter setter
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}