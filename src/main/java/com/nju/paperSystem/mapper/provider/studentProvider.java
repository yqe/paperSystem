package com.nju.paperSystem.mapper.provider;

import com.nju.paperSystem.entity.student;

import java.util.Map;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

public class studentProvider {
    private static final String TABLE_NAME="student";
    public String insert(Map<String,Object> para){
        student student = (student) para.get("student");
        if(student!=null){
            BEGIN();
            INSERT_INTO(TABLE_NAME);
            VALUES("studentId","#{student.studentId,javaType=String,jdbcType=VARCHAR}");
            VALUES("studentName","#{student.studentName,javaType=String,jdbcType=VARCHAR}");
            VALUES("studentEmail","#{student.studentEmail,javaType=String,jdbcType=VARCHAR}");
            VALUES("password","#{student.password,javaType=String,jdbcType=VARCHAR}");
            VALUES("phone","#{student.phone,javaType=String,jdbcType=VARCHAR}");
            VALUES("teacherName","#{student.teacherName,javaType=String,jdbcType=VARCHAR}");
            VALUES("teacherEmail","#{student.teacherEmail,javaType=String,jdbcType=VARCHAR}");
            VALUES("fileName","#{student.fileName,javaType=String,jdbcType=VARCHAR}");
        }
        return SQL();

    }


    public String update(Map<String,Object> para){
        student student = (student) para.get("student");
        BEGIN();
        UPDATE(TABLE_NAME);
        SET("studentId = #{student.studentId,javaType=String,jdbcType=VARCHAR}");
        SET("studentName = #{student.studentName,javaType=String,jdbcType=VARCHAR}");
        SET("studentEmail = #{student.studentEmail,javaType=String,jdbcType=VARCHAR}");
        SET("password = #{student.password,javaType=String,jdbcType=VARCHAR}");
        SET("phone = #{student.phone,javaType=String,jdbcType=VARCHAR}");
        SET("teacherName = #{student.teacherName,javaType=String,jdbcType=VARCHAR}");
        SET("teacherEmail = #{student.teacherEmail,javaType=String,jdbcType=VARCHAR}");
        SET("fileName = #{student.fileName,javaType=String,jdbcType=VARCHAR}");
        WHERE("studentId = #{student.studentId,javaType=String,jdbcType=VARCHAR}");
        return SQL();
    }
}
