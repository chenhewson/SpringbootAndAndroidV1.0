package springboot06mybatis.service;

import org.springframework.stereotype.Service;
import springboot06mybatis.utils.ServerResponse;

/**
 * ClassName:    UserService
 * Package:    springboot06mybatis.service
 * Description:
 * Datetime:    2020/3/9   21:27
 * Author:   hewson.chen@foxmail.com
 */
@Service
public interface UserService {
    ServerResponse loginLogic(String username, String password);
}
