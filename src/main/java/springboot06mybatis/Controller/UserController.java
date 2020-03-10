package springboot06mybatis.Controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springboot06mybatis.common.Const;
import springboot06mybatis.pojo.User;
import springboot06mybatis.service.UserService;
import springboot06mybatis.utils.ServerResponse;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * ClassName:    usercontroller
 * Package:    Controller
 * Description:
 * Datetime:    2020/3/5   14:17
 * Author:   hewson.chen@foxmail.com
 */
@RestController//包要放在与springboot的main函数的相同目录下
@RequestMapping("/protal")
public class UserController {
    protected static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userServices;

    @RequestMapping("/user/login.do")
    public ServerResponse login(String username, String password, HttpSession session) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        logger.info(username);
        ServerResponse serverResponse=userServices.loginLogic(username,password);
        if(serverResponse.isSuccess()){
            //用户信息放session
            session.setAttribute(Const.CURRENT_USER,serverResponse.getData());
        }
        return serverResponse;
    }

    @RequestMapping("/user/register.do")
    public ServerResponse register(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        return userServices.registerLogic(user);
    }

}
