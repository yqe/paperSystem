package com.nju.paperSystem.service.serviceImpl;

import com.nju.paperSystem.entity.student;
import com.nju.paperSystem.entity.teacher;
import com.nju.paperSystem.mapper.teacherMapper;
import com.nju.paperSystem.service.teacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Service
public class teahcerServiceImpl implements teacherService {
    @Autowired
    teacherMapper teacherMapper;

    public boolean update(teacher teacher){
        return teacherMapper.update(teacher);
    }

    public boolean insert(teacher teacher){
        return teacherMapper.insert(teacher);
    }

    public teacher getTeacherByEmail(String email){
        return teacherMapper.getTeacherByEmail(email);
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
