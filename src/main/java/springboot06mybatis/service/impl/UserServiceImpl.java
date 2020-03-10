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
import springboot06mybatis.utils.ServerResponse;

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
    public ServerResponse loginLogic(String username, String password) {
        if(username==null||username==""){
            logger.info("username==null||username=="+username);
            return ServerResponse.createServerResponseByFail(ResponseCode.USERNAME_NOT_EMPTY.getCode(),ResponseCode.USERNAME_NOT_EMPTY.getMsg());
        }
        if(password==null||password==""){
            logger.info("password==null||password=="+username);
            return ServerResponse.createServerResponseByFail(ResponseCode.PASSWORD_NOT_EMPTY.getCode(),ResponseCode.PASSWORD_NOT_EMPTY.getMsg());
        }
        if(userMapper.findByUserName(username)==0){
            logger.info("userMapper.findByUserName(username)==null"+username);
            return ServerResponse.createServerResponseByFail(ResponseCode.USERNAME_NOT_EXIST.getCode(),ResponseCode.USERNAME_NOT_EXIST.getMsg());
        }
        User user=userMapper.findByUsernameAndPassword(username,password);
        if(user==null){
            logger.info("user==null"+username);
            return ServerResponse.createServerResponseByFail(ResponseCode.PASSWORD_NOT_CORRECT.getCode(),ResponseCode.PASSWORD_NOT_CORRECT.getMsg());
        }
        logger.info("end+"+username);
        return ServerResponse.createServerResponseBySuccess(user);
    }
}
