package com.nju.paperSystem.service;

import com.nju.paperSystem.entity.modification;
import com.nju.paperSystem.mapper.modificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class modificationService {
    @Autowired
    modificationMapper modificationMapper;

    public List<modification> getAllModificationByStudentId(String studentId){
        return modificationMapper.getAllModificationByStudentId(studentId);
    }

//    public boolean update(modification modification){
//        return modificationMapper.update(modification.getId(), modification.getDescription());
//    }

    public boolean insert(modification modification){
        return modificationMapper.insert(modification.getStudentId(), modification.getSummary(), modification.getDescription(), modification.getDate());
    }

    public boolean delete(int id){
        return modificationMapper.delete(id);
    }
}
