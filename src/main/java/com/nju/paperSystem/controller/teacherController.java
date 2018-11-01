package com.nju.paperSystem.controller;

import com.nju.paperSystem.entity.modification;
import com.nju.paperSystem.entity.student;
import com.nju.paperSystem.entity.teacher;
import com.nju.paperSystem.service.modificationService;
import com.nju.paperSystem.service.studentService;
import com.nju.paperSystem.service.teacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;

@Controller
public class teacherController {
    @Autowired
    teacherService teacherService;
    @Autowired
    modificationService modificationService;
    @Autowired
    studentService studentService;

    @RequestMapping(value="/teacherRegister",method = RequestMethod.GET)
    public String teacherRegister(){
        return "teacherRegister";
    }

    @RequestMapping(value="/addTeacher",method = RequestMethod.POST)
    public String addTeacher(Model model,HttpServletRequest request){
        String id = request.getParameter("id");
        if(teacherService.getTeacherById(id) != null){
            model.addAttribute("error","该工号已存在，请重新注册！");
            return "teacherRegister";
        }
        teacher teacher = new teacher();
        teacher.setEmail(request.getParameter("email"));
        teacher.setName(request.getParameter("name"));
        teacher.setPassword(request.getParameter("password"));
        teacherService.insert(teacher);
        return "redirect:index";
    }

    @RequestMapping(value="/teacherLogin",method = RequestMethod.POST)
    public String teacherLogin(Model model, HttpServletRequest request){
        String email = request.getParameter("teacherEmail");
        String password = request.getParameter("teacherPassword");
        if(teacherService.login(email, password)){
            HttpSession session = request.getSession();
            session.setAttribute("teacherId",teacherService.getTeacherByEmail(email).getId());
            return "redirect:checkStudent";
        }
        else{
            model.addAttribute("error", "密码错误，请重新登录！");
            return "login";
        }
    }

    @RequestMapping(value = "/teacherInfo",method = RequestMethod.GET)
    public String teacherInfo(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        teacher teacher = teacherService.getTeacherById(session.getAttribute("teacherId").toString());
        model.addAttribute("teacher",teacher);
        return "teacherInfoUpdate";
    }

    @RequestMapping(value = "/checkStudent",method = RequestMethod.GET)
    public String checkStudent(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        teacher teacher = teacherService.getTeacherById(session.getAttribute("teacherId").toString());
        List<student> studentList = studentService.getAllStudentByTeacherEmail(teacher.getEmail());
        model.addAttribute("teacher", teacher);
        model.addAttribute("studentList", studentList);
        return "checkStudent";
    }

    @RequestMapping(value = "/paperInfo/{studentId}",method = RequestMethod.GET)
    public String paperInfo(@PathVariable("studentId")String studentId, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        teacher teacher = teacherService.getTeacherById(session.getAttribute("teacherId").toString());
        List<modification> modificationList = modificationService.getAllModificationByStudentId(studentId);
        model.addAttribute("teacher", teacher);
        model.addAttribute("student",studentService.getStudentById(studentId));
        model.addAttribute("modificationList",modificationList);
        return "checkModification";
    }

    @RequestMapping(value = "/paperDownload/{studentId}",method = RequestMethod.GET)
    public String paperDownload(@PathVariable("studentId")String studentId, Model model, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        System.out.println(teacherService.download(request, response, studentService.getStudentById(studentId)));
        return "redirect:checkStudent";
    }



}
