package com.nju.paperSystem.mapper;

import com.nju.paperSystem.entity.modification;
import com.nju.paperSystem.mapper.provider.modificationProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface modificationMapper {
    @Select("SELECT * FROM modification WHERE id = #{id}")
    modification getModificationById(int id);

    @Select("SELECT * FROM modification WHERE studentEmail = #{studentEmail} ORDER BY id DESC")
    List<modification> getAllModificationByStudentEmail(String studentEmail);

    @Select("SELECT * FROM modification WHERE studentEmail = #{studentEmail} ORDER BY version DESC")
    List<modification> getModificationListByVersion(String studentEmail);

    @InsertProvider(type = modificationProvider.class,method = "insert")
    Boolean insert(@Param("modification")modification modification);

    @UpdateProvider(type = modificationProvider.class,method = "update")
    Boolean update(@Param("modification")modification modification);

    @Delete("DELETE FROM modification WHERE id = #{id}")
    Boolean delete(int id);

}
