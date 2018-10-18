package com.nju.paperSystem.controller;

import com.nju.paperSystem.entity.student;
import com.nju.paperSystem.service.modificationService;
import com.nju.paperSystem.service.studentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class studentController {
    @Autowired
    studentService studentService;
    @Autowired
    modificationService modificationService;

    @RequestMapping(value="/index",method = RequestMethod.GET)
    public student studentinfo(){
        student student = studentService.getStudentByEmail("MF1832223@smail.nju.edu.cn");
        return student;
    }

}
