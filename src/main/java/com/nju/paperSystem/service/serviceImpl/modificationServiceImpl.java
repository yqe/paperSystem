package com.nju.paperSystem.service.serviceImpl;

import com.nju.paperSystem.entity.modification;
import com.nju.paperSystem.entity.student;
import com.nju.paperSystem.mapper.modificationMapper;
import com.nju.paperSystem.mapper.teacherMapper;
import com.nju.paperSystem.service.modificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Service
public class modificationServiceImpl implements modificationService {
    @Autowired
    modificationMapper modificationMapper;
    @Autowired
    teacherMapper teacherMapper;

    @Override
    public modification getModificationById(int id) {
        return modificationMapper.getModificationById(id);
    }

    public List<modification> getAllModificationByStudentEmail(String studentEmail){
        return modificationMapper.getAllModificationByStudentEmail(studentEmail);
    }

    public List<modification> getModificationListByVersion(String studentEmail){
        return modificationMapper.getModificationListByVersion(studentEmail);
    }

    public boolean insert(modification modification){
        return modificationMapper.insert(modification);
    }

    @Override
    public boolean update(modification modification) {
        return modificationMapper.update(modification);
    }

    public boolean delete(int id){
        return modificationMapper.delete(id);
    }

    @Override
    public String upload(MultipartFile file, student student, modification modification,int type) {
        try {
            if (file.isEmpty()) {
                return "文件为空";
            }
            // 获取文件名
            String fileName = "";
            if(type == 0){
                fileName = student.getStudentName()+"-"+modification.getDate()+"-"+modification.getVersion()+"-"+file.getOriginalFilename();
            }
            else{
                fileName = teacherMapper.getTeacherByEmail(student.getTeacherEmail()).getName()+"-"+file.getOriginalFilename();
            }

            modification.setFileName(fileName);
            update(modification);
            // 设置文件存储路径
            // "E://aim//"+student.getStudentEmail()+"//";//本地路径
            // "//home//ubuntu//web//papersystem"+student.getStudentEmail()+"//";//服务器路径
            String filePath = "E://aim//"+student.getStudentEmail()+"//";
            String path = filePath + fileName;
            File dest = new File(path);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();// 新建文件夹
            }
            file.transferTo(dest);// 文件写入
            return "上传成功";
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败";
    }

    public String download(HttpServletRequest request, HttpServletResponse response, modification modification, int type) throws UnsupportedEncodingException {
        String fileName = "";
        if(type == 0){
            fileName =  modification.getFileName();
        }
        else{
            fileName =  modification.getTeacherFileName();
        }
        if (fileName != null) {
            //设置文件路径
            // "E://aim//"+student.getStudentId()+"//";
            // "//home//ubuntu//web//papersystem"+student.getStudentId()+"//";
            String realPath = "E://aim//"+modification.getStudentEmail()+"//";
            File file = new File(realPath , fileName);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition", "attachment;fileName=" + java.net.URLEncoder.encode(fileName, "UTF-8"));// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    return "success";
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return "error";
    }

}
