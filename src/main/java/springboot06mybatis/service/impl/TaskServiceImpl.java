package springboot06mybatis.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot06mybatis.common.Const;
import springboot06mybatis.common.ResponseCode;
import springboot06mybatis.dao.TaskMapper;
import springboot06mybatis.pojo.Task;
import springboot06mybatis.pojo.User;
import springboot06mybatis.service.TaskService;
import springboot06mybatis.utils.HttpClient;
import springboot06mybatis.utils.MathUtils;
import springboot06mybatis.utils.ServerResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
}
