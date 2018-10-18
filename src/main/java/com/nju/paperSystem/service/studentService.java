package com.nju.paperSystem.service;

import com.nju.paperSystem.entity.student;
import com.nju.paperSystem.mapper.studentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class studentService {
    @Autowired
    studentMapper studentMapper;

    public boolean update(student student){
        return studentMapper.update(student);
    }

    public boolean insert(student student){
        return studentMapper.insert(student);
    }

    public List<student> getAllStudentByTeacherEmail(String email){
        return studentMapper.getAllStudentByTeacherEmail(email);
    }

    public student getStudentById(String id){
        return studentMapper.getStudentById(id);
    }

    public student getStudentByEmail(String email){
        return studentMapper.getStudentByEmail(email);
    }

    public boolean login(String email,String password){
        student student = studentMapper.getStudentByEmail(email);
        if(student.getPassword().equals(password)){
            return true;
        }
        else{
            return false;
        }
    }
}
