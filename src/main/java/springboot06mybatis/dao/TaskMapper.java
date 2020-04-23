package springboot06mybatis.dao;

import springboot06mybatis.pojo.Task;

import java.util.List;

public interface TaskMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_task
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer taskid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_task
     *
     * @mbg.generated
     */
    int insert(Task record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_task
     *
     * @mbg.generated
     */
    int insertSelective(Task record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_task
     *
     * @mbg.generated
     */
    Task selectByPrimaryKey(Integer taskid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_task
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Task record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_task
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Task record);

    //自定义部分
    List<Task> AllList(Integer userid);

    List<Task> homeList();

    String getTaskUsername(Integer taskId);

    List<Task> selectByfinisherid(String finisherid);

    List<Task> selectByPublished(String publishid);

    List<Task> myStarList(String userid);
}