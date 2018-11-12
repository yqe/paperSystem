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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
        String email = request.getParameter("email");
        if(teacherService.getTeacherByEmail(email) != null){
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
            session.setAttribute("email",email);
            return "redirect:checkStudent";
        }
        else{
            model.addAttribute("error", "密码错误，请重新登录！");
            return "login";
        }
    }

    @RequestMapping(value = "/checkStudent",method = RequestMethod.GET)
    public String checkStudent(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        teacher teacher = teacherService.getTeacherByEmail(session.getAttribute("email").toString());
        String email = teacher.getEmail();
        List<student> bachelorList = studentService.getBachelorStudentByTeacherEmail(email);
        List<student> masterList = studentService.getMasterStudentByTeacherEmail(email);
        List<student> doctorList = studentService.getDoctorStudentByTeacherEmail(email);
        model.addAttribute("teacher", teacher);
        model.addAttribute("bachelorList", bachelorList);
        model.addAttribute("masterList", masterList);
        model.addAttribute("doctorList", doctorList);
        return "checkStudent";
    }

    @RequestMapping(value = "/checkGraduatedStudent",method = RequestMethod.GET)
    public String checkGraduatedStudent(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        teacher teacher = teacherService.getTeacherByEmail(session.getAttribute("email").toString());
        String email = teacher.getEmail();
        List<student> bachelorList = studentService.getGraduatedBachelorStudentByTeacherEmail(email);
        List<student> masterList = studentService.getGraduatedMasterStudentByTeacherEmail(email);
        List<student> doctorList = studentService.getGraduatedDoctorStudentByTeacherEmail(email);
        model.addAttribute("teacher", teacher);
        model.addAttribute("bachelorList", bachelorList);
        model.addAttribute("masterList", masterList);
        model.addAttribute("doctorList", doctorList);
        return "checkGraduatedStudent";
    }

    @RequestMapping(value = "/paperInfo/{studentEmail}",method = RequestMethod.GET)
    public String paperInfo(@PathVariable("studentEmail")String studentEmail, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        teacher teacher = teacherService.getTeacherByEmail(session.getAttribute("email").toString());
        List<modification> modificationList = modificationService.getAllModificationByStudentEmail(studentEmail);
        model.addAttribute("teacher", teacher);
        model.addAttribute("student",studentService.getStudentByEmail(studentEmail));
        model.addAttribute("modificationList",modificationList);
        return "checkModification";
    }

    @RequestMapping(value = "/paperDownload/{id}",method = RequestMethod.GET)
    public String paperDownload(@PathVariable("id")int id, Model model, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        modificationService.download(request, response,modificationService.getModificationById(id),0);
        return "redirect:checkStudent";
    }

    @RequestMapping(value = "/teacherVersionDownload/{id}",method = RequestMethod.GET)
    public String teacherVersionDownload(@PathVariable("id")int id, Model model, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        modificationService.download(request, response,modificationService.getModificationById(id),1);
        return "redirect:checkStudent";
    }

    @RequestMapping(value = "/paperRevise/{id}",method = RequestMethod.GET)
    public String paperRevise(@PathVariable("id")int id, Model model, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        HttpSession session = request.getSession();
        teacher teacher = teacherService.getTeacherByEmail(session.getAttribute("email").toString());
        model.addAttribute("teacher", teacher);
        model.addAttribute("studentEmail",modificationService.getModificationById(id).getStudentEmail());
        model.addAttribute("id", id);
        return "checkRevise";
    }

    @RequestMapping(value = "/reviseUpload",method = RequestMethod.POST)
    public String reviseUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        int id = Integer.valueOf(request.getParameter("id"));
        String studentEmail = modificationService.getModificationById(id).getStudentEmail();
        student student = studentService.getStudentByEmail(studentEmail);
        modification modification = modificationService.getModificationById(id);

        //更新教师意见和修改的论文
        modification.setTeacherAdvice(request.getParameter("advice"));
        modificationService.upload(file, student, modification,1);
        return "redirect:paperInfo/"+studentEmail;
    }

    @RequestMapping(value="/close/{studentEmail}",method = RequestMethod.POST)
    public String close(@PathVariable("studentEmail")String studentEmail, HttpServletRequest request){
        student student = studentService.getStudentByEmail(studentEmail);
        student.setState(1);
        studentService.update(student);
        return "redirect:checkStudent";
    }



}
