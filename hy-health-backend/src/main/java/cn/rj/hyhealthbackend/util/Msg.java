package cn.rj.hyhealthbackend.util;

import lombok.Data;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 陈亮
 * <p>
 * Msg类
 * 封装返回结果，其中包含了响应码，响应消息，响应成功标志，响应数据，
 * 并实现了创建成功响应、失败响应以及添加响应数据等方法
 */
@Data
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
     * 响应成功函数     * @return
     */
    public static Msg success() {
        Msg msg = new Msg();
        msg.setCode(20000);
        msg.setMessage("响应成功");
        msg.setSuccess(true);
        return msg;
    }

    /**
     * 响应失败函数     * @return
     */
    public static Msg fail() {
        Msg msg = new Msg();
        msg.setCode(10000);
        msg.setMessage("响应失败");
        msg.setSuccess(false);
        return msg;
    }

    /**
     * 添加响应数据     * @return
     */
    public Msg data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    /**
     * 修改状态码     * @return
     */
    public Msg code(Integer code) {
        this.setCode(code);
        return this;
    }

    /**
     * 修改消息     * @return
     */
    public Msg mess(String str) {
        this.setMessage(str);
        return this;
    }
}