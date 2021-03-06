package com.nju.paperSystem.mapper.provider;

import com.nju.paperSystem.entity.teacher;

import java.util.Map;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

public class teacherProvider {
    private static final String TABLE_NAME="teacher";
    public String insert(Map<String,Object> para){
        teacher teacher = (teacher) para.get("teacher");
        if(teacher!=null){
            BEGIN();
            INSERT_INTO(TABLE_NAME);
            VALUES("name","#{teacher.name,javaType=String,jdbcType=VARCHAR}");
            VALUES("password","#{teacher.password,javaType=String,jdbcType=VARCHAR}");
            VALUES("email","#{teacher.email,javaType=String,jdbcType=VARCHAR}");
        }
        return SQL();

    }


    public String update(Map<String,Object> para){
        teacher teacher = (teacher) para.get("teacher");
        BEGIN();
        UPDATE(TABLE_NAME);
        SET("name = #{teacher.name,javaType=String,jdbcType=VARCHAR}");
        SET("password = #{teacher.password,javaType=String,jdbcType=VARCHAR}");
        WHERE("email = #{teacher.email,javaType=String,jdbcType=VARCHAR}");
        return SQL();
    }
}
