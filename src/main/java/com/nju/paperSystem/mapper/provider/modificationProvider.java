package com.nju.paperSystem.mapper.provider;

import com.nju.paperSystem.entity.modification;

import java.util.Map;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

public class modificationProvider {
    private static final String TABLE_NAME="modification";
    public String insert(Map<String,Object> para){
        modification modification = (modification) para.get("modification");
        if(modification!=null){
            BEGIN();
            INSERT_INTO(TABLE_NAME);
            VALUES("version","#{modification.version,javaType=int,jdbcType=INTEGER}");
            VALUES("studentEmail","#{modification.studentEmail,javaType=String,jdbcType=VARCHAR}");
            VALUES("summary","#{modification.summary,javaType=String,jdbcType=VARCHAR}");
            VALUES("description","#{modification.description,javaType=String,jdbcType=VARCHAR}");
            VALUES("date","#{modification.date,javaType=String,jdbcType=VARCHAR}");
            VALUES("fileName","#{modification.fileName,javaType=String,jdbcType=VARCHAR}");
            VALUES("teacherAdvice","#{modification.teacherAdvice,javaType=String,jdbcType=VARCHAR}");
            VALUES("teacherFileName","#{modification.teacherFileName,javaType=String,jdbcType=VARCHAR}");
        }
        return SQL();

    }


    public String update(Map<String,Object> para){
        modification modification = (modification) para.get("modification");
        BEGIN();
        UPDATE(TABLE_NAME);
        SET("version = #{modification.version,javaType=int,jdbcType=INTEGER}");
        SET("studentEmail = #{modification.studentEmail,javaType=String,jdbcType=VARCHAR}");
        SET("summary = #{modification.summary,javaType=String,jdbcType=VARCHAR}");
        SET("description = #{modification.description,javaType=String,jdbcType=VARCHAR}");
        SET("date = #{modification.date,javaType=String,jdbcType=VARCHAR}");
        SET("fileName = #{modification.fileName,javaType=String,jdbcType=VARCHAR}");
        SET("teacherAdvice = #{modification.teacherAdvice,javaType=String,jdbcType=VARCHAR}");
        SET("teacherFileName = #{modification.teacherFileName,javaType=String,jdbcType=VARCHAR}");
        WHERE("id = #{modification.id,javaType=int,jdbcType=INTEGER}");
        return SQL();
    }
}
