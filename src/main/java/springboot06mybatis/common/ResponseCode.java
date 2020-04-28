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
    PASSWORD_NOT_CORRECT(4,"密码错误！"),
    EMAIL_NOT_EMPTY(5,"邮箱不能为空！"),
    USERNAME_IS_EXIST(6,"用户名已存在！"),
    EMAILIS_EXIST(7,"邮箱已存在！"),
    USERINFO_IS_EMPTY(8,"用户信息为空！"),
    REGISTER_FAILED(9,"注册失败！"),
    REGISTER_SUCCESS(10,"注册成功！"),

    ADDTASK_PUBLISHUSERID_EMPTY(11,"发布者id为空！"),
    ADDTASK_TITLE_EMPTY(12,"标题为空！"),
    ADDTASK_FAILED(13,"添加任务失败！"),
    ADDTASK_SUCCESS(14,"添加任务成功！"),

    RECEIVETASK_SUCCESS(15,"成功接受任务，请按时完成！"),
    RECEIVETASK_FAILED(16,"接受任务失败！"),
    RECEIVETASK_TASKID_EMPTY(17,"TaskID为空！"),
    RECEIVETASK_FINISHERID_EMPTY(18,"FinisherID为空！"),
    RECEIVETASK_TASK_EMPTY(19,"找不到任务！"),
    RECEIVETASK_TASK_ISNOTNEW(20,"该任务已被抢走！"),

    ALLTASK_USERID_EMPTY(21,"UserID为空！"),
    ALLTASK_FAILED(22,"查询失败！"),
    ALLTASK_SUCCESS(23,"查询成功！"),

    ADDTASK_AddRESS_EMPTY(24,"目的地为空！"),

    MYLOCATION_EMPTY(25,"经纬度信息为空！"),

    TASKID_EMPTY(26,"任务id为空！"),
    TASKID_NOT_EXIST(27,"任务不存在！"),
    NO_SHOULD_DONE(28,"没有存在未完成的任务！"),
    NO_PUBLISHED_DONE(29,"从未发布过任务！"),
    STAR_LIST_EMPTY(30,"愿望清单为空！"),
    TASK_IS_DONE(31,"任务已完成，请勿重复点击！"),
    TASK_DONE(32,"已完成！待发布者确认后即可得到赏金！"),
    TASK_DESTROY(33,"已经打赏过了！"),

    AVATAT_UPLOAD_OK(34,"头像上传成功！"),

    IMAGE_EMPTY(35,"图片资源为空！"),
    IMAGE_UPLOAD_OK(36,"图片上传成功！"),
    IMAGE_UPLOAD_FAIL(37,"图片上传失败！"),
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
