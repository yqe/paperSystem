package com.nju.paperSystem.mapper;

import com.nju.paperSystem.entity.modification;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface modificationMapper {
    @Select("SELECT * FROM modification WHERE studentId = #{studentId} ORDER BY date DESC")
    List<modification> getAllModificationByStudentId(String studentId);

    @Insert("INSERT INTO modification (studentId, summary, description, date) VALUES (#{studentId}, #{summary}, #{description}, #{date})")
    Boolean insert(@Param("studentId") String studentId, @Param("summary")String summary, @Param("description")String description, @Param("date")String date);

//    @Update("UPDATE modification SET description = #{description} WHERE id = #{id} ")
//    Boolean update(@Param("id") int id, @Param("description")String description);

    @Delete("DELETE FROM modification WHERE id =#{id}")
    Boolean delete(int id);

}
