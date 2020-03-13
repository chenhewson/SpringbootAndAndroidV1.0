package springboot06mybatis.service;


import org.springframework.stereotype.Service;
import springboot06mybatis.pojo.Task;
import springboot06mybatis.utils.ServerResponse;

/**
 * ClassName:    TaskService
 * Package:    springboot06mybatis.service
 * Description:
 * Datetime:    2020/3/10   19:27
 * Author:   hewson.chen@foxmail.com
 */
@Service
public interface TaskService {
    ServerResponse addTask(Task task);
}
