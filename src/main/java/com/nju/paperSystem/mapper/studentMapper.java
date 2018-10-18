package com.nju.paperSystem.mapper;

import com.nju.paperSystem.entity.student;
import com.nju.paperSystem.mapper.provider.studentProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@Mapper
public interface studentMapper {
    @Select("SELECT * FROM student WHERE id = #{id}")
    student getStudentById(String id);

    @Select("SELECT * FROM student WHERE studentEmail = #{studentEmail}")
    student getStudentByEmail(String studentEmail);

    @Select("SELECT * FROM student ")
    List<student> getAllStudent();

    @Select("SELECT * FROM student WHERE teacherEmail = #{teacherEmail}")
    List<student> getAllStudentByTeacherEmail(String teacherEmail);

    @InsertProvider(type = studentProvider.class,method = "insert")
    Boolean insert(@Param("student")student student);

    @UpdateProvider(type = studentProvider.class,method = "update")
    Boolean update(@Param("student")student student);
}