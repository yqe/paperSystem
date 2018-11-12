package com.nju.paperSystem.mapper;

import com.nju.paperSystem.entity.student;
import com.nju.paperSystem.mapper.provider.studentProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@Mapper
public interface studentMapper {

    @Select("SELECT * FROM student WHERE studentEmail = #{studentEmail}")
    student getStudentByEmail(String studentEmail);

    @Select("SELECT * FROM student ")
    List<student> getAllStudent();

    @Select("SELECT * FROM student WHERE teacherEmail = #{teacherEmail} and degree = '本科' and state = 0")
    List<student> getBachelorStudentByTeacherEmail(String teacherEmail);

    @Select("SELECT * FROM student WHERE teacherEmail = #{teacherEmail} and degree = '硕士' and state = 0")
    List<student> getMasterStudentByTeacherEmail(String teacherEmail);

    @Select("SELECT * FROM student WHERE teacherEmail = #{teacherEmail} and degree = '博士' and state = 0")
    List<student> getDoctorStudentByTeacherEmail(String teacherEmail);

    @Select("SELECT * FROM student WHERE teacherEmail = #{teacherEmail} and degree = '本科' and state = 1")
    List<student> getGraduatedBachelorStudentByTeacherEmail(String teacherEmail);

    @Select("SELECT * FROM student WHERE teacherEmail = #{teacherEmail} and degree = '硕士' and state = 1")
    List<student> getGraduatedMasterStudentByTeacherEmail(String teacherEmail);

    @Select("SELECT * FROM student WHERE teacherEmail = #{teacherEmail} and degree = '博士' and state = 1")
    List<student> getGraduatedDoctorStudentByTeacherEmail(String teacherEmail);

    @InsertProvider(type = studentProvider.class,method = "insert")
    Boolean insert(@Param("student")student student);

    @UpdateProvider(type = studentProvider.class,method = "update")
    Boolean update(@Param("student")student student);
}