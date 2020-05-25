package springboot06mybatis.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot06mybatis.Controller.UserController;
import springboot06mybatis.common.ResponseCode;
import springboot06mybatis.dao.UserMapper;
import springboot06mybatis.pojo.User;
import springboot06mybatis.service.UserService;
import springboot06mybatis.utils.MD5Utils;
import springboot06mybatis.utils.SendEmail;
import springboot06mybatis.utils.ServerResponse;
import springboot06mybatis.utils.String2utf_8;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * ClassName:    UserServiceImpl
 * Package:    springboot06mybatis.service.impl
 * Description:
 * Datetime:    2020/3/9   21:30
 * Author:   hewson.chen@foxmail.com
 */
@Service
public class UserServiceImpl implements UserService {

    protected static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserMapper userMapper;
    @Override
    public ServerResponse loginLogic(String username, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        //前台来的密码先统一加密
        password=String2utf_8.setString2utf_8(password);
        String MD5Password=MD5Utils.getMD5Code(password);
        logger.info(MD5Password);
        if(username==null||username.equals("")){
            return ServerResponse.createServerResponseByFail(ResponseCode.USERNAME_NOT_EMPTY.getCode(),ResponseCode.USERNAME_NOT_EMPTY.getMsg());
        }
        if(MD5Password==null||MD5Password.equals("")){
            return ServerResponse.createServerResponseByFail(ResponseCode.PASSWORD_NOT_EMPTY.getCode(),ResponseCode.PASSWORD_NOT_EMPTY.getMsg());
        }
        if(userMapper.findByUserName(username)==0){
            return ServerResponse.createServerResponseByFail(ResponseCode.USERNAME_NOT_EXIST.getCode(),ResponseCode.USERNAME_NOT_EXIST.getMsg());
        }
        User user=userMapper.findByUsernameAndPassword(username,MD5Password);
        if(user==null){
            return ServerResponse.createServerResponseByFail(ResponseCode.PASSWORD_NOT_CORRECT.getCode(),ResponseCode.PASSWORD_NOT_CORRECT.getMsg());
        }
        return ServerResponse.createServerResponseBySuccess(user);
    }

    @Override
    public ServerResponse registerLogic(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        //用户对象为空
        if(user==null){
            return ServerResponse.createServerResponseByFail(ResponseCode.USERINFO_IS_EMPTY.getCode(),ResponseCode.USERINFO_IS_EMPTY.getMsg());
        }

        String username=user.getUsername();
        String password=String2utf_8.setString2utf_8(user.getPassword());
        String email=user.gettEmail();

        //用户名为空？
        if(username==null||username==""){
            return ServerResponse.createServerResponseByFail(ResponseCode.USERNAME_NOT_EMPTY.getCode(),ResponseCode.USERNAME_NOT_EMPTY.getMsg());
        }
        //用户密码为空?
        if(password==null||password==""){
            return ServerResponse.createServerResponseByFail(ResponseCode.PASSWORD_NOT_EMPTY.getCode(),ResponseCode.PASSWORD_NOT_EMPTY.getMsg());
        }
        //用户邮箱为空？
        if(email==null||email==""){
            return ServerResponse.createServerResponseByFail(ResponseCode.EMAIL_NOT_EMPTY.getCode(),ResponseCode.EMAIL_NOT_EMPTY.getMsg());
        }
        //用户名存在？
        if(userMapper.findByUserName(username)>0){
            return ServerResponse.createServerResponseByFail(ResponseCode.USERNAME_IS_EXIST.getCode(),ResponseCode.USERNAME_IS_EXIST.getMsg());
        }
        //用户邮箱存在？
        if(userMapper.findByEmail(email)>0){
            return ServerResponse.createServerResponseByFail(ResponseCode.EMAILIS_EXIST.getCode(),ResponseCode.EMAILIS_EXIST.getMsg());
        }

        //加密
        user.setPassword(MD5Utils.getMD5Code(user.getPassword()));

        //注册
        Integer result=userMapper.insert(user);
        if(result==0){
            //注册失败
            return ServerResponse.createServerResponseByFail(ResponseCode.REGISTER_FAILED.getCode(),ResponseCode.REGISTER_FAILED.getMsg());
        }
        return ServerResponse.createServerResponseBySuccess(ResponseCode.REGISTER_SUCCESS.getCode(),ResponseCode.REGISTER_SUCCESS.getMsg());
    }

    @Override
    public ServerResponse getUserInfo(Integer userid) {
        User user=userMapper.selectByPrimaryKey(userid);
        return ServerResponse.createServerResponseBySuccess(user);
    }

    //上传用户头像
    @Override
    public ServerResponse uploadAvatar(String userid,String filename) {
        if((userid==null&&userid.length()==0)||(filename==null&&filename.length()==0)){
            return ServerResponse.createServerResponseByFail(ResponseCode.USERNAME_NOT_EXIST.getCode(),ResponseCode.USERNAME_NOT_EXIST.getMsg());
        }
        User user=userMapper.selectByPrimaryKey(Integer.valueOf(userid));
        if(user==null){
            return ServerResponse.createServerResponseByFail(ResponseCode.USERNAME_NOT_EXIST.getCode(),ResponseCode.USERNAME_NOT_EXIST.getMsg());
        }
        user.settAvator(filename);
        userMapper.updateByPrimaryKey(user);
        return ServerResponse.createServerResponseBySuccess(ResponseCode.AVATAT_UPLOAD_OK.getCode(),ResponseCode.AVATAT_UPLOAD_OK.getMsg());
    }

    //修改个人信息
    @Override
    public ServerResponse updateMyInfo(String userid,String tell, String email) {
        if(userid==null&&userid.length()==0){
            return ServerResponse.createServerResponseByFail(ResponseCode.USERNAME_NOT_EXIST.getCode(),ResponseCode.USERNAME_NOT_EXIST.getMsg());
        }
        User user=userMapper.selectByPrimaryKey(Integer.valueOf(userid));
        if(user==null){
            return ServerResponse.createServerResponseByFail(ResponseCode.USERNAME_NOT_EXIST.getCode(),ResponseCode.USERNAME_NOT_EXIST.getMsg());
        }
        user.settTell(tell);
        user.settEmail(email);
        userMapper.updateByPrimaryKey(user);
        return ServerResponse.createServerResponseBySuccess(ResponseCode.INFO_UPDATE_OK.getCode(),ResponseCode.INFO_UPDATE_OK.getMsg());
    }

    @Override
    public ServerResponse sendEmail(String emailAddress, String emailCode) {
        if(emailAddress==null&&emailAddress.length()==0){
            return ServerResponse.createServerResponseByFail(ResponseCode.EMAIL_NOT_EMPTY.getCode(),ResponseCode.EMAIL_NOT_EMPTY.getMsg());
        }
        if(emailCode==null&&emailCode.length()==0){
            return ServerResponse.createServerResponseByFail(ResponseCode.EMAIL_CODE_IS_EMPTY.getCode(),ResponseCode.EMAIL_CODE_IS_EMPTY.getMsg());
        }
        try {
            SendEmail sm=new SendEmail();
            sm.email(emailAddress, Integer.valueOf(emailCode));
            return ServerResponse.createServerResponseBySuccess(ResponseCode.EMAIL_SEND_OK.getCode(),ResponseCode.EMAIL_SEND_OK.getMsg());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ServerResponse.createServerResponseByFail(ResponseCode.EMAIL_SEND_FAILED.getCode(),ResponseCode.EMAIL_SEND_FAILED.getMsg());
        }
    }
}
