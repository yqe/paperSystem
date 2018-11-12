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
            VALUES("studentName","#{student.studentName,javaType=String,jdbcType=VARCHAR}");
            VALUES("studentEmail","#{student.studentEmail,javaType=String,jdbcType=VARCHAR}");
            VALUES("password","#{student.password,javaType=String,jdbcType=VARCHAR}");
            VALUES("phone","#{student.phone,javaType=String,jdbcType=VARCHAR}");
            VALUES("teacherEmail","#{student.teacherEmail,javaType=String,jdbcType=VARCHAR}");
            VALUES("degree","#{student.degree,javaType=String,jdbcType=VARCHAR}");
            VALUES("state","#{student.state,javaType=int,jdbcType=INTEGER}");
        }
        return SQL();

    }


    public String update(Map<String,Object> para){
        student student = (student) para.get("student");
        BEGIN();
        UPDATE(TABLE_NAME);
        SET("studentName = #{student.studentName,javaType=String,jdbcType=VARCHAR}");
        SET("password = #{student.password,javaType=String,jdbcType=VARCHAR}");
        SET("phone = #{student.phone,javaType=String,jdbcType=VARCHAR}");
        SET("teacherEmail = #{student.teacherEmail,javaType=String,jdbcType=VARCHAR}");
        SET("degree = #{student.degree,javaType=String,jdbcType=VARCHAR}");
        SET("state = #{student.state,javaType=int,jdbcType=INTEGER}");
        SET("lastCommit = #{student.lastCommit,javaType=String,jdbcType=VARCHAR}");
        WHERE("studentEmail = #{student.studentEmail,javaType=String,jdbcType=VARCHAR}");
        return SQL();
    }
}
