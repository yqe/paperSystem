package com.nju.paperSystem.controller;

import com.nju.paperSystem.entity.modification;
import com.nju.paperSystem.entity.student;
import com.nju.paperSystem.service.mailService;
import com.nju.paperSystem.service.modificationService;
import com.nju.paperSystem.service.studentService;
import com.nju.paperSystem.service.teacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@EnableAsync
public class studentController {
    @Autowired
    studentService studentService;
    @Autowired
    modificationService modificationService;
    @Autowired
    mailService mailService;
    @Autowired
    teacherService teacherService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request) {
        String message = request.getParameter("message");
        if (message != null)
            model.addAttribute("message", message);
        return "login";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String login(Model model, HttpServletRequest request) {
        String message = request.getParameter("message");
        if (message != null)
            model.addAttribute("message", message);
        return "login";
    }

    @RequestMapping(value = "/studentRegister", method = RequestMethod.GET)
    public String studentRegister(Model model, HttpServletRequest request) {
        String error = request.getParameter("error");
        if (error != null)
            model.addAttribute("error", error);
        return "studentRegister";
    }

    @RequestMapping(value = "/studentLogin", method = RequestMethod.POST)
    public String studentLogin(Model model, HttpServletRequest request) {
        String email = request.getParameter("studentEmail");
        String password = request.getParameter("studentPassword");
        if (studentService.getStudentByEmail(email) == null) {
            model.addAttribute("error", "账号不存在！");
            return "login";
        }
        if (studentService.login(email, password)) {
            HttpSession session = request.getSession();
            session.setAttribute("email", email);
            return "redirect:paperUpload";
        } else {
            model.addAttribute("error", "密码错误，请重新登录！");
            return "login";
        }
    }

    @RequestMapping(value = "/addStudent", method = RequestMethod.POST)
    public ModelAndView addStudent(Model model, HttpServletRequest request) {
        String email = request.getParameter("email");
        ModelAndView view = new ModelAndView("redirect:studentRegister");
        if (studentService.getStudentByEmail(email) != null) {
            view.addObject("error", "该邮箱已存在，请重新注册！");
            return view;
        }
        if (teacherService.getTeacherByEmail(request.getParameter("teacherEmail")) == null) {
            view.addObject("error", "不存在该导师邮箱，请重新注册！");
            return view;
        }
        student student = new student();
        student.setStudentEmail(request.getParameter("email"));
        student.setStudentName(request.getParameter("name"));
        student.setPhone(request.getParameter("phone"));
        student.setPassword(request.getParameter("password"));
        student.setTeacherEmail(request.getParameter("teacherEmail"));
        student.setDegree(request.getParameter("degree"));
        student.setState(0);
        studentService.insert(student);
        view = new ModelAndView("redirect:index");
        view.addObject("message", "注册成功！");
        return view;
    }

    @RequestMapping(value = "/studentInfo", method = RequestMethod.GET)
    public String studentInfo(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        student student = studentService.getStudentByEmail(session.getAttribute("email").toString());
        String warning = request.getParameter("warning");
        String message = request.getParameter("message");
        if (warning != null)
            model.addAttribute("warning", warning);
        if (message != null)
            model.addAttribute("message", message);
        model.addAttribute("student", student);
        return "studentInfoUpdate";
    }

    @RequestMapping(value = "/studentInfoUpdate", method = RequestMethod.POST)
    public ModelAndView studentInfoUpdate(HttpServletRequest request) {
        String email = request.getParameter("studentEmail");
        ModelAndView view = new ModelAndView("redirect:studentInfo");
        if (teacherService.getTeacherByEmail(request.getParameter("teacherEmail")) == null) {
            view.addObject("warning", "不存在该导师邮箱！");
            return view;
        }
        student student = studentService.getStudentByEmail(email);
        student.setPhone(request.getParameter("phone"));
        student.setStudentName(request.getParameter("studentName"));
        student.setTeacherEmail(request.getParameter("teacherEmail"));
        studentService.update(student);
        view.addObject("message", "修改成功！");
        return view;
    }

    @RequestMapping(value = "/paperUpload", method = RequestMethod.GET)
    public String paperUpload(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        student student = studentService.getStudentByEmail((session.getAttribute("email")).toString());
        String warning = request.getParameter("warning");
        String messeage = request.getParameter("message");
        List<modification> modificationList = modificationService.getAllModificationByStudentEmail(student.getStudentEmail());
        if (warning != null)
            model.addAttribute("warning", warning);
        if (messeage != null)
            model.addAttribute("state", messeage);
        model.addAttribute("student", student);
        model.addAttribute("modificationList", modificationList);
        return "paperUpload";
    }

    @RequestMapping(value = "/paperReUpload/{modificationId}", method = RequestMethod.GET)
    public String paperReUpload(@PathVariable("modificationId") int modificationId, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        student student = studentService.getStudentByEmail((session.getAttribute("email")).toString());
        modification modification = modificationService.getModificationById(modificationId);
        String warning = request.getParameter("warning");
        if (warning != null)
            model.addAttribute("warning", warning);
        model.addAttribute("student", student);
        model.addAttribute("modification", modification);
        return "studentReUpload";
    }

    @RequestMapping(value = "/paperModification", method = RequestMethod.POST)
    public ModelAndView paperModification(@RequestParam("file") MultipartFile file, Model model, HttpServletRequest request) throws MessagingException, IOException {
        HttpSession session = request.getSession();
        student student = studentService.getStudentByEmail((session.getAttribute("email")).toString());
        ModelAndView view = new ModelAndView("redirect:paperUpload");
        if (file.isEmpty()) {
            view.addObject("warning", "请选择上传文件");
            return view;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        modification modification = new modification();
        List<modification> modificationList = modificationService.getModificationListByVersion(student.getStudentEmail());
        if (modificationList.size() > 0) {
            modification.setVersion(modificationList.get(0).getVersion() + 1);
        } else {
            modification.setVersion(1);
        }
        modification.setStudentEmail(student.getStudentEmail());
        modification.setSummary(request.getParameter("summary"));
        modification.setDescription(request.getParameter("description"));
        modification.setDate(sdf.format(date));
        student.setLastCommit(sdf.format(date));
        // 上传文件
        String state = modificationService.upload(file, student, modification, 0);
        studentService.update(student);
//        // 邮件发送
        mailService.sendEmail(student, modification);

        if (state.equals("上传成功")){
            view.addObject("message", "提交成功");
            modificationService.insert(modification);}
        else
            view.addObject("message", "提交失败");

        return view;
    }

    @RequestMapping(value = "/paperReModification", method = RequestMethod.POST)
    public ModelAndView paperReModification(@RequestParam("file") MultipartFile file, Model model, HttpServletRequest request) throws MessagingException, IOException {
        HttpSession session = request.getSession();
        student student = studentService.getStudentByEmail((session.getAttribute("email")).toString());
        int id = Integer.valueOf(request.getParameter("modificationId"));
        ModelAndView view = new ModelAndView("redirect:paperReUpload/" + id);
        if (file.isEmpty()) {
            view.addObject("warning", "请选择上传文件");
            return view;
        }
        view = new ModelAndView("redirect:paperUpload");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        modification modification = modificationService.getModificationById(id);
        modification.setSummary(request.getParameter("summary"));
        modification.setDescription(request.getParameter("description"));
        modification.setDate(sdf.format(date));
        student.setLastCommit(sdf.format(date));
        // 上传文件
        String state = modificationService.upload(file, student, modification, 0);
        studentService.update(student);
        modificationService.update(modification);
//        // 邮件发送
        mailService.sendEmail(student, modification);
        if (state.equals("上传成功"))
            view.addObject("message", "提交成功");
        else
            view.addObject("message", "提交失败");

        return view;
    }

    @RequestMapping(value = "/paperDownloadStudent/{id}", method = RequestMethod.GET)
    public void paperDownloadStudent(@PathVariable("id") int id, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        modification modification = modificationService.getModificationById(id);
        modificationService.download(request, response, modificationService.getModificationById(modification.getId()), 0);
    }

    @RequestMapping(value = "/teacherVersionDownloadStudent/{id}", method = RequestMethod.GET)
    public void teacherVersionDownloadStudent(@PathVariable("id") int id, Model model, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        modificationService.download(request, response, modificationService.getModificationById(id), 1);
    }


}
