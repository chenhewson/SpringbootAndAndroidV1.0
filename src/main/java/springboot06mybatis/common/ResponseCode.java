package springboot06mybatis.common;

/**
 * ClassName:    ResponseCode
 * Package:    springboot06mybatis.common
 * Description:
 * Datetime:    2020/3/9   21:36
 * Author:   hewson.chen@foxmail.com
 */
//枚举，设定状态码
public enum ResponseCode {
    USERNAME_NOT_EMPTY(1,"用户名不能空！"),
    PASSWORD_NOT_EMPTY(2,"密码不能空！"),
    USERNAME_NOT_EXIST(3,"用户名不存在！"),
    PASSWORD_NOT_CORRECT(4,"密码错误！")
    ;
    private int code;
    private String msg;

    ResponseCode(int code,String msg){
        this.code=code;
        this.msg=msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
