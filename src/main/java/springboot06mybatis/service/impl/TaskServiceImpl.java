package springboot06mybatis.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot06mybatis.common.Const;
import springboot06mybatis.common.ResponseCode;
import springboot06mybatis.dao.StarMapper;
import springboot06mybatis.dao.TaskMapper;
import springboot06mybatis.dao.UserMapper;
import springboot06mybatis.pojo.Star;
import springboot06mybatis.pojo.Task;
import springboot06mybatis.pojo.User;
import springboot06mybatis.service.TaskService;
import springboot06mybatis.utils.DistanceUtil;
import springboot06mybatis.utils.HttpClient;
import springboot06mybatis.utils.MathUtils;
import springboot06mybatis.utils.ServerResponse;

import java.math.BigDecimal;
import java.util.*;

/**
 * ClassName:    TaskServiceImpl
 * Package:    springboot06mybatis.service.impl
 * Description:
 * Datetime:    2020/3/10   19:28
 * Author:   hewson.chen@foxmail.com
 */
@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskMapper taskMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    StarMapper starMapper;
    @Override
    public ServerResponse addTask(Task task,String address) {
        //发布者id为空
        if(task.getPublishuserid()==null||task.getPublishuserid().equals("")){
            return ServerResponse.createServerResponseByFail(ResponseCode.ADDTASK_PUBLISHUSERID_EMPTY.getCode(),ResponseCode.ADDTASK_PUBLISHUSERID_EMPTY.getMsg());
        }

        //标题为空
        if(task.gettTitle()==null||task.gettTitle().equals("")){
            return ServerResponse.createServerResponseByFail(ResponseCode.ADDTASK_TITLE_EMPTY.getCode(),ResponseCode.ADDTASK_TITLE_EMPTY.getMsg());
        }


        //地址为空
        if(address==null||address.equals("")){
            return ServerResponse.createServerResponseByFail(ResponseCode.ADDTASK_AddRESS_EMPTY.getCode(),ResponseCode.ADDTASK_AddRESS_EMPTY.getMsg());
        }else{
            //        //中文地址转经纬度
            System.out.println(address);
            HashMap<String, String> hashMap = new HashMap<String,String>();
            hashMap.put("address",address);
            HashMap<String,Object> hashMap1=getGeo(HttpClient.sendGetRequest(Const.GaoDeMapAPI,hashMap));
            task.settJingdu(MathUtils.objectConvertBigDecimal(hashMap1.get("jingdu")));
            task.settWeidu(MathUtils.objectConvertBigDecimal(hashMap1.get("weidu")));
        }



        //数据库插入任务
        Integer result= taskMapper.insert(task);
        //插入失败
        if(result==0){
            return ServerResponse.createServerResponseByFail(ResponseCode.ADDTASK_FAILED.getCode(),ResponseCode.ADDTASK_FAILED.getMsg());
        }
        return ServerResponse.createServerResponseBySuccess(ResponseCode.ADDTASK_SUCCESS.getCode(),ResponseCode.ADDTASK_SUCCESS.getMsg());
    }


    @Override
    public ServerResponse receiveTask(Task task) {
        //1.前端传来的task里有taskid、finisherid是否为空
        if(task.getTaskid()==null||task.getTaskid().equals("")){
            return ServerResponse.createServerResponseByFail(ResponseCode.RECEIVETASK_TASKID_EMPTY.getCode(),ResponseCode.RECEIVETASK_TASKID_EMPTY.getMsg());
        }
        if(task.getFinisherid()==null||task.getFinisherid().equals("")){
            return ServerResponse.createServerResponseByFail(ResponseCode.RECEIVETASK_FINISHERID_EMPTY.getCode(),ResponseCode.RECEIVETASK_FINISHERID_EMPTY.getMsg());
        }

        //2.获取数据库中的task信息
        Task DBtask=taskMapper.selectByPrimaryKey(task.getTaskid());

        //3.数据库找不到该任务
        if(DBtask==null){
            return ServerResponse.createServerResponseByFail(ResponseCode.RECEIVETASK_TASK_EMPTY.getCode(),ResponseCode.RECEIVETASK_TASK_EMPTY.getMsg());
        }

        //4.任务被人抢先接受
        if(!DBtask.gettIsnew()){
            return ServerResponse.createServerResponseByFail(ResponseCode.RECEIVETASK_TASK_ISNOTNEW.getCode(),ResponseCode.RECEIVETASK_TASK_ISNOTNEW.getMsg());
        }

        //5.该任务未完成、未销毁、是最新发布（未被其他用户接受)
        if(!DBtask.gettIsdone()&&DBtask.gettIsnew()&&!DBtask.gettIsdestroy()){
            DBtask.setFinisherid(task.getFinisherid());
            DBtask.settIsnew(false);
            taskMapper.updateByPrimaryKey(DBtask);
            return ServerResponse.createServerResponseBySuccess(ResponseCode.RECEIVETASK_SUCCESS.getCode(),ResponseCode.RECEIVETASK_SUCCESS.getMsg());
        }
        return ServerResponse.createServerResponseByFail(ResponseCode.RECEIVETASK_FAILED.getCode(),ResponseCode.RECEIVETASK_FAILED.getMsg());
    }

    //查看与用户有关的全部任务
    @Override
    public ServerResponse allTask(User user) {

        if(user.getUserid()==null||user.getUserid().equals("")){
            return ServerResponse.createServerResponseByFail(ResponseCode.ALLTASK_USERID_EMPTY.getCode(),ResponseCode.ALLTASK_USERID_EMPTY.getMsg());
        }

        List<Task> list=taskMapper.AllList(user.getUserid());
        if(list.isEmpty()){
            return ServerResponse.createServerResponseByFail(ResponseCode.ALLTASK_FAILED.getCode(),ResponseCode.ALLTASK_FAILED.getMsg());
        }
        return ServerResponse.createServerResponseBySuccess(list);
    }

    @Override
    public ServerResponse homeTask(String jingduFrom, String weiduFrom) {
        Map<Double, Task> treemap = new TreeMap<Double, Task>();
        if(jingduFrom==null||jingduFrom.equals("")||weiduFrom==null||weiduFrom.equals("")){
            return ServerResponse.createServerResponseByFail(ResponseCode.MYLOCATION_EMPTY.getCode(),ResponseCode.MYLOCATION_EMPTY.getMsg());
        }
        List<Task> list=taskMapper.homeList();
        if(list.isEmpty()){
            return ServerResponse.createServerResponseByFail(ResponseCode.ALLTASK_FAILED.getCode(),ResponseCode.ALLTASK_FAILED.getMsg());
        }else{
            for (Task item:list){
                Double longitudeFrom=Double.valueOf(jingduFrom);
                Double latitudeFrom=Double.valueOf(weiduFrom);
                Double longitudeTo=item.gettJingdu().doubleValue();
                Double latitudeTo=item.gettWeidu().doubleValue();
                Double distance=DistanceUtil.getDistance(longitudeFrom,latitudeFrom,longitudeTo,latitudeTo);
                System.out.println(distance);

                //存到treemap中，便于排序
                treemap.put(distance,item);
            }
        }
        return ServerResponse.createServerResponseBySuccess(treemap);
    }

    public HashMap<String,Object> getGeo(String addressJSON){
        HashMap<String,Object> hashMap=new HashMap<String,Object>();

        //高德API返回的字符串转json对象
        JSONObject data0= JSONObject.parseObject(addressJSON);
        //json对象找出geocodes数组
        JSONArray data1=JSONObject.parseArray(data0.getString("geocodes"));
        //从geocodes数组抽取location
        JSONObject data3=JSONObject.parseObject(data1.get(0).toString());

        String [] GeoArray=data3.getString("location").split(",");
        hashMap.put("jingdu",GeoArray[0]);
        hashMap.put("weidu",GeoArray[1]);
        return hashMap;
    }

    @Override
    public ServerResponse getTaskUsername(Integer taskId) {
        if(taskId==null||taskId<0){
            return ServerResponse.createServerResponseByFail(ResponseCode.TASKID_EMPTY.getCode(),ResponseCode.TASKID_EMPTY.getMsg());
        }
        String taskUserName=taskMapper.getTaskUsername(taskId);
        return ServerResponse.createServerResponseBySuccess(taskUserName);
    }

    @Override
    public ServerResponse addStar(Star star) {
        if (star.gettUserid().length()==0||star.gettUserid()==null){
            return ServerResponse.createServerResponseByFail(ResponseCode.ALLTASK_USERID_EMPTY.getCode(),ResponseCode.ALLTASK_USERID_EMPTY.getMsg());
        }else {
            //查询用户名是否存在
            User user=userMapper.selectByPrimaryKey(Integer.valueOf(star.gettUserid()));
            if(user.getUserid()==null||user.getUserid()==0){
                return ServerResponse.createServerResponseByFail(ResponseCode.USERNAME_NOT_EXIST.getCode(),ResponseCode.USERNAME_NOT_EXIST.getMsg());
            }
        }
        if (star.gettTaskid().length()==0||star.gettTaskid()==null){
            return ServerResponse.createServerResponseByFail(ResponseCode.TASKID_EMPTY.getCode(),ResponseCode.TASKID_EMPTY.getMsg());
        }else {
            Task task=taskMapper.selectByPrimaryKey(Integer.valueOf(star.gettTaskid()));
            if(task.getTaskid()==null||task.getTaskid()==0){
                return ServerResponse.createServerResponseByFail(ResponseCode.TASKID_NOT_EXIST.getCode(),ResponseCode.TASKID_NOT_EXIST.getMsg());
            }
        }
        starMapper.insert(star);
        return ServerResponse.createServerResponseBySuccess("已加入心愿单！");
    }

    @Override
    public ServerResponse starExistTask(Star star) {
        Integer starid=starMapper.selectByUseridAndTaskid(star);
        if (starid==null||starid==0){
            //心愿清单不存在该项，允许加入清单。code=1
            starMapper.insert(star);
            return ServerResponse.createServerResponseBySuccess(0,"心愿清单不存在该项，现已加入该项目");
        }
        starMapper.deleteByPrimaryKey(starid);
        return ServerResponse.createServerResponseBySuccess(1,"心愿清单已经存在该项，现已移除该项目");
    }

    @Override
    public ServerResponse StarExist(Star star) {
        Integer starid=starMapper.selectByUseridAndTaskid(star);
        if (starid==null||starid==0){
            //心愿清单不存在该项
            return ServerResponse.createServerResponseBySuccess(0,"心愿清单不存在该项，设置为空红心");
        }
        //心愿清单已经存在该项
        return ServerResponse.createServerResponseBySuccess(1,"心愿清单已经存在该项，设置为实红心");
    }

    @Override
    public ServerResponse shouldBeDone(String finisherid) {
        List<Task> list=taskMapper.selectByfinisherid(finisherid);
        if (list.size()!=0){
            return ServerResponse.createServerResponseBySuccess(list);
        }else {
            return ServerResponse.createServerResponseByFail(ResponseCode.NO_SHOULD_DONE.getCode(),ResponseCode.NO_SHOULD_DONE.getMsg());
        }
    }

    @Override
    public ServerResponse myPublished(String publishid) {
        List<Task> list=taskMapper.selectByPublished(publishid);
        if (list.size()!=0){
            return ServerResponse.createServerResponseBySuccess(list);
        }else {
            return ServerResponse.createServerResponseByFail(ResponseCode.NO_PUBLISHED_DONE.getCode(),ResponseCode.NO_PUBLISHED_DONE.getMsg());
        }
    }

    @Override
    public ServerResponse myStarList(String userid) {
        List<Task> list=taskMapper.myStarList(userid);
        if (list.size()!=0){
            return ServerResponse.createServerResponseBySuccess(list);
        }else {
            return ServerResponse.createServerResponseByFail(ResponseCode.STAR_LIST_EMPTY.getCode(),ResponseCode.STAR_LIST_EMPTY.getMsg());
        }
    }

    //接受者完成任务
    @Override
    public ServerResponse isDone(String taskid) {
        Task task=taskMapper.selectByPrimaryKey(Integer.valueOf(taskid));
        if(task==null){
            return ServerResponse.createServerResponseByFail(ResponseCode.TASKID_NOT_EXIST.getCode(),ResponseCode.TASKID_NOT_EXIST.getMsg());
        }
        if(task.gettIsdone()==true){
            return ServerResponse.createServerResponseByFail(ResponseCode.TASK_IS_DONE.getCode(),ResponseCode.TASK_IS_DONE.getMsg());
        }
        task.settIsdone(true);
        //Begintime表示任务接受者完成任务的时间
        task.settBegintime(new java.sql.Date(new java.util.Date().getTime()));
        taskMapper.updateByPrimaryKey(task);
        return ServerResponse.createServerResponseBySuccess(ResponseCode.TASK_DONE.getCode(),ResponseCode.TASK_DONE.getMsg());
    }

    //发布者确认完成，并打赏
    @Override
    public ServerResponse ConfirmDone(String taskid) {
        Task task=taskMapper.selectByPrimaryKey(Integer.valueOf(taskid));
        if(task==null){
            return ServerResponse.createServerResponseByFail(ResponseCode.TASKID_NOT_EXIST.getCode(),ResponseCode.TASKID_NOT_EXIST.getMsg());
        }
        if(task.gettIsdestroy()){
            return ServerResponse.createServerResponseByFail(ResponseCode.TASK_DESTROY.getCode(),ResponseCode.TASK_DESTROY.getMsg());
        }
        //Finishtime表示任务发布者完成确认对方完成，并打赏的时间
        task.settFinishtime(new java.sql.Date(new java.util.Date().getTime()));

        //标记为双方确认完成
        task.settIsdestroy(true);

        //更新task
        taskMapper.updateByPrimaryKey(task);

        //更新完成者的账户余额
        User user=userMapper.selectByPrimaryKey(Integer.valueOf(task.getFinisherid()));
        user.settBalance(user.gettBalance()+task.gettMoney());

        //数据库更新完成者信息
        userMapper.updateByPrimaryKey(user);
        return ServerResponse.createServerResponseBySuccess(user,"确认完成！赏金已打入对方账户！");
    }
}
