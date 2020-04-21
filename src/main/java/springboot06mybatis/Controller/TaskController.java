package springboot06mybatis.Controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springboot06mybatis.common.Const;
import springboot06mybatis.pojo.Star;
import springboot06mybatis.pojo.Task;
import springboot06mybatis.pojo.User;
import springboot06mybatis.service.TaskService;
import springboot06mybatis.utils.HttpClient;
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
import java.util.HashMap;

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
        logger.info(String.valueOf(task.getPublishuserid())+task.gettAddress());
//        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        task.settAddress(String2utf_8.setString2utf_8(task.gettAddress()));
        String address=task.gettAddress();

        //设定默认值
        task.settCreatetime(new java.sql.Date(new java.util.Date().getTime()));
        task.settIsdone(false);
        task.settIsnew(true);
        task.settIsdestroy(false);
        //赏金若为空
        if(task.gettMoney()==null){
            task.settMoney(0.0);
        }

        return taskService.addTask(task,address);
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

    /*首页所有任务的展示
    * 输入：当前用户所在经纬度
    * 输出：附近任务列表，按距离排序*/
    @ResponseBody
    @RequestMapping("/Task/homeTask.do")
    //查看全部任务
    public ServerResponse hometask(@Param("jingdu")String jingdu,@Param("weidu") String weidu) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        logger.info("经纬度："+jingdu+"+"+weidu);
        return taskService.homeTask(jingdu,weidu);
    }

    @ResponseBody
    @RequestMapping("/Task/getUsername.do")
    public ServerResponse getusername(@Param("taskId")Integer taskId){
        return taskService.getTaskUsername(taskId);
    }

    //
    @ResponseBody
    @RequestMapping("/Task/StarExist.do")
    public ServerResponse StarExist(Star star){
        logger.info(star+"");
        return taskService.StarExist(star);
    }

    //实现点击红心的相关逻辑
    @ResponseBody
    @RequestMapping("/Task/starExistTask.do")
    public ServerResponse starExist(Star star){
        logger.info(star+"");
        return taskService.starExistTask(star);
    }
}
