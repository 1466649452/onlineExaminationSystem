package com.scu.exam.service;

import com.scu.exam.pojo.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeacherService {
    /*
     * 增加
     * */
    int insertOneTeacher(Teacher teacher);
    int insertManyTeacher(List<Teacher> teacherList);

    /*
     * 查询
     * */
    Teacher findTeacherById(String t_id);
    List<Teacher> findTeacherByname(String t_name);


    /*
     * 删除
     * */
    int deleteTeacherByid(String t_id);

    /*
     * 修改
     * */
    int updateTeacherName(@Param("t_id") String t_id, @Param("t_name") String t_name);
    int updateTeacherPassword(@Param("t_id") String t_id,@Param("t_password") String t_password);
    int updateTeacherHeadimage(@Param("t_id") String t_id,@Param("t_image") String t_image);
    int updateTeacher(@Param("t_id") String t_id,@Param("t_name") String t_name,@Param("t_password") String t_password,@Param("t_image") String t_image);
    int updateTeacher(@Param("t_id") String t_id,@Param("t_name") String t_name,@Param("t_password") String t_password);

}
