package com.nju.paperSystem.service;

import com.nju.paperSystem.entity.modification;
import com.nju.paperSystem.entity.student;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface modificationService {
    modification getModificationById(int id);

    List<modification> getAllModificationByStudentEmail(String studentEmail);

    List<modification> getModificationListByVersion(String studentEmail);

    boolean insert(modification modification);

    boolean update(modification modification);

    boolean delete(int id);

    //type分为0,1 0代表学生论文的上传下载，1代表教师修改的论文的上传下载

    String upload(MultipartFile file, student student, modification modification,int type);

    String download(HttpServletRequest request, HttpServletResponse response, modification modification, int type) throws UnsupportedEncodingException;
}
