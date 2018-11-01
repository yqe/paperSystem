package com.nju.paperSystem.service;

import com.nju.paperSystem.entity.student;
import com.nju.paperSystem.mapper.studentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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

    public String upload(MultipartFile file, student student) {
        try {
            if (file.isEmpty()) {
                return "文件为空";
            }
            // 获取文件名
            String fileName = file.getOriginalFilename();

            // 设置文件存储路径
            // "E://aim//"+student.getStudentId()+"//";
            // "//home//ubuntu//web//papersystem"+student.getStudentId()+"//";
            String filePath = "//home//ubuntu//web//papersystem//"+student.getStudentId()+"//";
            String path = filePath + fileName;

            student.setFileName(fileName);
            update(student);

            File dest = new File(path);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();// 新建文件夹
            }
            file.transferTo(dest);// 文件写入
            return "上传成功";
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败";
    }


}
