package com.atu.extensible.controller;

import com.atu.extensible.entity.Student;
import com.atu.extensible.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/findAll")
    public List<Student> findAll(){
        return studentService.findAll();
    }

    @GetMapping("/findOne/{studentId}")
    public Student findOne(@PathVariable("studentId") Integer studentId){
        return studentService.findOne(studentId);
    }

    @PostMapping("/insertOne")
    public String insertOne(Student student){
        return studentService.insertOne(student);
    };

    @PutMapping("/updateOne")
    public String updateOne(Student student){
        return studentService.updateOne(student);
    }

    @DeleteMapping("/deleteOne/{studentId}")
    public String deleteOne(@PathVariable("studentId") Integer studentId){
        return studentService.deleteOne(studentId);
    }

    @GetMapping("/async")
    public String testAsyncMethod(){
        return studentService.testAsyncMethod();
    }
}
