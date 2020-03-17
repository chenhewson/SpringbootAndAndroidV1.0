package springboot06mybatis.dao;

import springboot06mybatis.pojo.TaskIMG;

public interface TaskIMGMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_taskimg
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer tImgid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_taskimg
     *
     * @mbg.generated
     */
    int insert(TaskIMG record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_taskimg
     *
     * @mbg.generated
     */
    int insertSelective(TaskIMG record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_taskimg
     *
     * @mbg.generated
     */
    TaskIMG selectByPrimaryKey(Integer tImgid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_taskimg
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TaskIMG record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_taskimg
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyWithBLOBs(TaskIMG record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_taskimg
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TaskIMG record);
}