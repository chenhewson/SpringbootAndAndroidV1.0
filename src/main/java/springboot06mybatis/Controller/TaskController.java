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
import org.springframework.web.multipart.MultipartFile;
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

    //查看我作为finisherid的所有订单
    @ResponseBody
    @RequestMapping("/Task/ShouldBeDone.do")
    public ServerResponse shouldBeDone(@Param("finisherid")String finisherid){
        return taskService.shouldBeDone(finisherid);
    }

    //查看我发布的所有订单
    @ResponseBody
    @RequestMapping("/Task/MyPublished.do")
    public ServerResponse myPublished(@Param("publishid")String publishid){
        return taskService.myPublished(publishid);
    }

    //查看我的感兴趣清单
    @ResponseBody
    @RequestMapping("/Task/MyStar.do")
    public ServerResponse myStarList(@Param("userid")String userid){
        System.out.println(userid);
        return taskService.myStarList(userid);
    }

    //任务接受方确认完成任务
    @ResponseBody
    @RequestMapping("/Task/isDone.do")
    public ServerResponse isDone(@Param("taskid")String taskid){
        System.out.println("taskid"+taskid);
        return taskService.isDone(taskid);
    }

    //任务发布者确认完成任务并打赏
    @ResponseBody
    @RequestMapping("/Task/ConfirmDone.do")
    public ServerResponse ConfirmDone(@Param("taskid")String taskid){
        System.out.println("ConfirmDone,taskid="+taskid);
        return taskService.ConfirmDone(taskid);
    }

    //上传任务图片
    @ResponseBody
    @RequestMapping("/Task/imageUpload.do")
    public ServerResponse imageUpload(@Param("taskpic") MultipartFile file_pi,@Param("type") String type){
        System.out.println(type);
        System.out.println("file_pi"+file_pi);
        return taskService.imageUpload(file_pi);
    }

    //获取七牛云tocken
    @ResponseBody
    @RequestMapping("/Task/getTocken.do")
    public ServerResponse getTocken(){
        return taskService.getTocken();
    }


}
