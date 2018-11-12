package com.nju.paperSystem.service;

import com.nju.paperSystem.entity.modification;
import com.nju.paperSystem.entity.student;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface studentService {
    boolean update(student student);

    boolean insert(student student);

    List<student> getBachelorStudentByTeacherEmail(String email);

    List<student> getMasterStudentByTeacherEmail(String email);

    List<student> getDoctorStudentByTeacherEmail(String email);

    List<student> getGraduatedBachelorStudentByTeacherEmail(String email);

    List<student> getGraduatedMasterStudentByTeacherEmail(String email);

    List<student> getGraduatedDoctorStudentByTeacherEmail(String email);

    student getStudentByEmail(String email);

    boolean login(String email,String password);
}
