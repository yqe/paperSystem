package com.nju.paperSystem.service.serviceImpl;

import com.nju.paperSystem.config.emailConfig;
import com.nju.paperSystem.entity.email;
import com.nju.paperSystem.entity.modification;
import com.nju.paperSystem.entity.student;
import com.nju.paperSystem.service.mailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;

@Service
public class mailServiceImpl implements mailService {
    @Autowired
    emailConfig mc;

    @Async
    @Override
    public void sendEmail(student student, modification modification) throws MessagingException, IOException {
        email studentemail = new email();
        email teacheremail = new email();
        //给学生发送邮件提醒
        studentemail.setReceiver(student.getStudentEmail());
        studentemail.setContent(modification.getSummary()+"<br>"+modification.getDescription());
        studentemail.setSubject(student.getStudentName()+"-版本"+modification.getVersion()+"-"+modification.getDate()+"-学位论文");
        //给老师发送邮件提醒
        teacheremail.setReceiver(student.getTeacherEmail());
        teacheremail.setContent(modification.getSummary()+"<br>"+modification.getDescription());
        teacheremail.setSubject(student.getStudentName()+"-版本"+modification.getVersion()+"-"+modification.getDate()+"-学位论文");
        mc.sendMail(studentemail, student.getStudentEmail(), modification.getFileName());
        mc.sendMail(teacheremail, student.getStudentEmail(), modification.getFileName());
        System.out.println("successful to send message!");
    }


    @Async
    @Override
    public void sendReviseEmail(student student, modification modification) throws MessagingException, IOException {
        email studentemail = new email();
//        email teacheremail = new email();
        //给学生发送邮件提醒
        studentemail.setReceiver(student.getStudentEmail());
        studentemail.setContent(modification.getSummary()+"<br>"+modification.getTeacherAdvice());
        studentemail.setSubject(student.getStudentName()+"-版本"+modification.getVersion()+"-"+modification.getDate()+"-论文批注");
        mc.sendMail(studentemail, student.getStudentEmail(), modification.getTeacherFileName());
//        mc.sendMail(teacheremail, student.getStudentEmail(), fileName);
        System.out.println("successful to send message!");
    }
}
