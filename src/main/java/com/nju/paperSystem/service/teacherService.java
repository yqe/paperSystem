package com.nju.paperSystem.service;

import com.nju.paperSystem.entity.student;
import com.nju.paperSystem.entity.teacher;
import com.nju.paperSystem.mapper.teacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

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

    public String download(HttpServletRequest request, HttpServletResponse response, student student) throws UnsupportedEncodingException {
        String fileName = student.getFileName();
        if (fileName != null) {
            //设置文件路径
            // "E://aim//"+student.getStudentId()+"//";
            // "//home//ubuntu//web//papersystem"+student.getStudentId()+"//";
            String realPath = "//home//ubuntu//web//papersystem//"+student.getStudentId()+"//";
            File file = new File(realPath , fileName);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition", "attachment;fileName=" + java.net.URLEncoder.encode(fileName, "UTF-8"));// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    return "success";
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return "error";
    }
}
