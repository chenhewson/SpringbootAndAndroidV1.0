package springboot06mybatis.Controller;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springboot06mybatis.common.Const;
import springboot06mybatis.pojo.Task;
import springboot06mybatis.pojo.User;
import springboot06mybatis.service.TaskService;
import springboot06mybatis.utils.ServerResponse;
import springboot06mybatis.utils.String2utf_8;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ClassName:    OrderController
 * Package:    springboot06mybatis.Controller
 * Description:
 * Datetime:    2020/4/2   13:46
 * Author:   hewson.chen@foxmail.com
 */
@Controller
@RequestMapping("/protal")
public class TaskController {
    protected static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    TaskService taskService;

    @ResponseBody
    @RequestMapping("/Task/addTask.do")
    //新增任务
    public ServerResponse addtask( Task task) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        logger.info(String.valueOf(task.getPublishuserid()));
        task.settMoney(0.0);
//        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        task.settCreatetime(new java.sql.Date(new java.util.Date().getTime()));
        return taskService.addTask(task);
    }

    @ResponseBody
    @RequestMapping("/Task/receiveTask.do")
    //接受任务
    public ServerResponse receivetask(Task task) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        logger.info(String.valueOf(task.getTaskid()));
        logger.info(String.valueOf(task.getFinisherid()));
        return taskService.receiveTask(task);
    }

    @ResponseBody
    @RequestMapping("/Task/allTask.do")
    //查看全部任务
    public ServerResponse alltask(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        logger.info(String.valueOf(user.getUserid()));
        return taskService.allTask(user);
    }
}
