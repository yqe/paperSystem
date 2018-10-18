package com.nju.paperSystem.controller;

import com.nju.paperSystem.service.modificationService;
import com.nju.paperSystem.service.teacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class teacherController {
    @Autowired
    teacherService teacherService;
    @Autowired
    modificationService modificationService;
}
