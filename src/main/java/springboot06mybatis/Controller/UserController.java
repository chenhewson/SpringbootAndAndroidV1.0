package springboot06mybatis.Controller;


import org.apache.ibatis.annotations.Param;
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
        user.settBalance(0.0);
        return userServices.registerLogic(user);
    }

    @RequestMapping("/user/getUserinfo.do")
    public ServerResponse getUserInfo(@Param("userid")Integer userid){
        return userServices.getUserInfo(userid);
    }

    //上传头像
    @RequestMapping("/user/uploadAvatar.do")
    public ServerResponse uploadAvatar(@Param("userid") String userid,@Param("filename") String filename){
        System.out.println("上传头像"+userid+filename);
        return userServices.uploadAvatar(userid,filename);
    }

    //修改个人信息
    @RequestMapping("/user/updateMyInfo.do")
    public ServerResponse updateMyInfo(@Param("userid") String userid,@Param("tell") String tell,@Param("email") String email){
        System.out.println("修改个人信息"+tell+email);
        return userServices.updateMyInfo(userid,tell,email);
    }

    //发送验证码
    @RequestMapping("/user/sendEmail.do")
    public ServerResponse sendEmail(@Param("emailAddress") String emailAddress,@Param("emailCode") String emailCode){
        System.out.println("验证码"+emailCode);
        System.out.println("邮箱"+emailAddress);
        return userServices.sendEmail(emailAddress,emailCode);
    }
}
