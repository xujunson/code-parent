package com.atu.extensible.controller;

import com.atu.extensible.entity.Teacher;
import com.atu.extensible.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @RequestMapping("/findAll")
    public List<Teacher> findAll(){
        return teacherService.findAll();
    }

    @GetMapping("/findOne/{teacherId}")
    public Teacher findOne(@PathVariable("teacherId") Integer teacherId){
        return teacherService.findOne(teacherId);
    }

    @PostMapping("/insertOne")
    public String insertOne(Teacher teacher){
        return teacherService.insertOne(teacher);
    };

    @PutMapping("/updateOne")
    public String updateOne(Teacher teacher){
        return teacherService.updateOne(teacher);
    }

    @DeleteMapping("/deleteOne/{teacherId}")
    public String deleteOne(@PathVariable("teacherId") Integer teacherId){
        return teacherService.deleteOne(teacherId);
    }
}
