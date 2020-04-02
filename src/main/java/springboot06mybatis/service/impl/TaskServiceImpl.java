package springboot06mybatis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot06mybatis.common.ResponseCode;
import springboot06mybatis.dao.TaskMapper;
import springboot06mybatis.pojo.Task;
import springboot06mybatis.service.TaskService;
import springboot06mybatis.utils.ServerResponse;

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
    public ServerResponse addTask(Task task) {
        //发布者id为空
        if(task.getPublishuserid()==null||task.getPublishuserid().equals("")){
            return ServerResponse.createServerResponseByFail(ResponseCode.ADDTASK_PUBLISHUSERID_EMPTY.getCode(),ResponseCode.ADDTASK_PUBLISHUSERID_EMPTY.getMsg());
        }

        //标题为空
        if(task.gettTitle()==null||task.gettTitle().equals("")){
            return ServerResponse.createServerResponseByFail(ResponseCode.ADDTASK_TITLE_EMPTY.getCode(),ResponseCode.ADDTASK_TITLE_EMPTY.getMsg());
        }

        //数据库插入任务
        Integer result= taskMapper.insert(task);

        //插入失败
        if(result==0){
            return ServerResponse.createServerResponseByFail(ResponseCode.ADDTASK_FAILED.getCode(),ResponseCode.ADDTASK_FAILED.getMsg());
        }
        return ServerResponse.createServerResponseBySuccess(ResponseCode.ADDTASK_SUCCESS.getCode(),ResponseCode.ADDTASK_SUCCESS.getMsg());
    }
}
