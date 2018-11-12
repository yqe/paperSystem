package com.nju.paperSystem.service.serviceImpl;

import com.nju.paperSystem.entity.modification;
import com.nju.paperSystem.entity.student;
import com.nju.paperSystem.mapper.studentMapper;
import com.nju.paperSystem.service.studentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class studentServiceImpl implements studentService {
    @Autowired
    studentMapper studentMapper;

    public boolean update(student student){
        return studentMapper.update(student);
    }

    public boolean insert(student student){
        return studentMapper.insert(student);
    }

    public List<student> getBachelorStudentByTeacherEmail(String email){
        return studentMapper.getBachelorStudentByTeacherEmail(email);
    }

    public List<student> getMasterStudentByTeacherEmail(String email){
        return studentMapper.getMasterStudentByTeacherEmail(email);
    }

    public List<student> getDoctorStudentByTeacherEmail(String email){
        return studentMapper.getDoctorStudentByTeacherEmail(email);
    }

    public List<student> getGraduatedBachelorStudentByTeacherEmail(String email){
        return studentMapper.getGraduatedBachelorStudentByTeacherEmail(email);
    }

    public List<student> getGraduatedMasterStudentByTeacherEmail(String email){
        return studentMapper.getGraduatedMasterStudentByTeacherEmail(email);
    }

    public List<student> getGraduatedDoctorStudentByTeacherEmail(String email){
        return studentMapper.getGraduatedDoctorStudentByTeacherEmail(email);
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
