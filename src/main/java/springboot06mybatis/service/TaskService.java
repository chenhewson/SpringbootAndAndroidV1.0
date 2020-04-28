package springboot06mybatis.service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import springboot06mybatis.pojo.Star;
import springboot06mybatis.pojo.Task;
import springboot06mybatis.pojo.User;
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
    ServerResponse addTask(Task task,String address);

    ServerResponse receiveTask(Task task);

    ServerResponse allTask(User user);

    ServerResponse homeTask(String jingdu, String weidu);

    ServerResponse getTaskUsername(Integer taskId);

    ServerResponse addStar(Star star);

    ServerResponse starExistTask(Star star);

    ServerResponse StarExist(Star star);

    ServerResponse shouldBeDone(String finisherid);

    ServerResponse myPublished(String publishid);

    ServerResponse myStarList(String userid);

    ServerResponse isDone(String taskid);

    ServerResponse ConfirmDone(String taskid);

    ServerResponse imageUpload(MultipartFile file_pi);

    ServerResponse getTocken();
}
