package com.nju.paperSystem.service;

import com.nju.paperSystem.entity.modification;
import com.nju.paperSystem.entity.student;

import javax.mail.MessagingException;
import java.io.IOException;

public interface mailService {
    void sendEmail(student student, modification modification, String fileName) throws MessagingException, IOException;
}
