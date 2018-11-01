package com.nju.paperSystem.service;

import com.nju.paperSystem.config.emailConfig;
import com.nju.paperSystem.entity.email;
import com.nju.paperSystem.entity.modification;
import com.nju.paperSystem.entity.student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class mailService {
    @Autowired
    emailConfig mc;
    public void sendEmail(student student, modification modification){
        email studentemail = new email();
        email teacheremail = new email();
        //给学生发送邮件提醒
        studentemail.setReceiver(student.getStudentEmail());
        studentemail.setContent(modification.getDescription());
        studentemail.setSubject(modification.getSummary());
        //给老师发送邮件提醒
        teacheremail.setReceiver(student.getTeacherEmail());
        teacheremail.setContent(modification.getDescription());
        teacheremail.setSubject(modification.getSummary());
        mc.sendSimpleMail(studentemail);
        mc.sendSimpleMail(teacheremail);
        System.out.println("successful to send message!");
    }

}