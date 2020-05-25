package springboot06mybatis.service;

import org.springframework.stereotype.Service;
import springboot06mybatis.pojo.User;
import springboot06mybatis.utils.ServerResponse;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * ClassName:    UserService
 * Package:    springboot06mybatis.service
 * Description:
 * Datetime:    2020/3/9   21:27
 * Author:   hewson.chen@foxmail.com
 */
@Service
public interface UserService {
    ServerResponse loginLogic(String username, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    ServerResponse registerLogic(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    ServerResponse getUserInfo(Integer userid);

    ServerResponse uploadAvatar( String userid,String filename);

    ServerResponse updateMyInfo(String userid,String tell, String email);

    ServerResponse sendEmail(String emailAddress, String emailCode);
}
