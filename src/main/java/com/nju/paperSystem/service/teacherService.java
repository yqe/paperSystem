package com.nju.paperSystem.service;

import com.nju.paperSystem.entity.teacher;
import com.nju.paperSystem.mapper.teacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class teacherService {
    @Autowired
    teacherMapper teacherMapper;

    public boolean update(teacher teacher){
        return teacherMapper.update(teacher);
    }

    public boolean insert(teacher teacher){
        return teacherMapper.insert(teacher);
    }

    public teacher getTeacherById(String id){
        return teacherMapper.getTeacherById(id);
    }

    public teacher getStudentByEmail(String email){
        return teacherMapper.getTeacherByEmail(email);
    }

    public boolean login(String email,String password){
        teacher teacher = teacherMapper.getTeacherByEmail(email);
        if(teacher.getPassword().equals(password)){
            return true;
        }
        else{
            return false;
        }
    }
}
