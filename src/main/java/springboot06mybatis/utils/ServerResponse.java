package springboot06mybatis.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * ClassName:    ServerResponse
 * Package:    springboot06mybatis.utils
 * Description:接口都用ServerResponse对象往前端返回数据
 * Datetime:    2020/3/9   21:41
 * Author:   hewson.chen@foxmail.com
 */

//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)//设定json默认不返回空字段
public class ServerResponse<T> {
    private int status;//状态 0：接口调用成功
    private T data;//泛型，当status=0，将返回的数据封装到data中
    private String msg;//给前端的提示信息

    //判断接口是否调用成功
//    @JsonIgnore
    public  boolean isSuccess(){
        return this.status==0;
    }

    //构造方法
    private ServerResponse(){}

    private ServerResponse(int status){
        this.status=status;
    }
    private ServerResponse(int status,T data) {
        this.status = status;
        this.data=data;
    }
    private ServerResponse(int status,T data,String msg) {
        this.status = status;
        this.data=data;
        this.msg=msg;
    }

    //定义接口成功返回静态方法,默认0为成功
    public static ServerResponse createServerResponseBySuccess(){
        return new ServerResponse(0);
    }
    public static <T> ServerResponse createServerResponseBySuccess(T data){
        return new ServerResponse(0,data);
    }
    public static <T> ServerResponse createServerResponseBySuccess(T data,String msg){
        return new ServerResponse(0,data,msg);
    }

    //定义接口失败返回静态方法
    public static ServerResponse createServerResponseByFail(int status){
        return new ServerResponse(status);
    }
    public static ServerResponse createServerResponseByFail(int status,String msg){
        return new ServerResponse(status,null,msg);
    }
    //getter和setter
    public void setData(T data) {
        this.data = data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}
