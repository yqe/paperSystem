package com.nju.paperSystem.service;

import com.nju.paperSystem.entity.student;
import com.nju.paperSystem.entity.teacher;

public interface teacherService {
    boolean update(teacher teacher);

    boolean insert(teacher teacher);

    teacher getTeacherByEmail(String email);

    teacher getStudentByEmail(String email);

    boolean login(String email,String password);
}
