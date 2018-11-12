package com.nju.paperSystem.mapper;
import com.nju.paperSystem.entity.teacher;
import com.nju.paperSystem.mapper.provider.teacherProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface teacherMapper {

    @Select("SELECT * FROM teacher WHERE email = #{email}")
    teacher getTeacherByEmail(String email);

    @InsertProvider(type = teacherProvider.class,method = "insert")
    Boolean insert(@Param("teacher")teacher teacher);

    @UpdateProvider(type = teacherProvider.class,method = "update")
    Boolean update(@Param("teacher")teacher teacher);
}