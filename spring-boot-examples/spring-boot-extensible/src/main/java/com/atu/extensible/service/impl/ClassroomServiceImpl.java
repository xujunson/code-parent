package com.atu.extensible.service.impl;

import com.atu.extensible.constant.BaseConstant;
import com.atu.extensible.constant.ErrorCodeEnum;
import com.atu.extensible.entity.Classroom;
import com.atu.extensible.entity.Student;
import com.atu.extensible.exception.BaseException;
import com.atu.extensible.mapper.ClassroomMapper;
import com.atu.extensible.mapper.StudentMapper;
import com.atu.extensible.service.ClassroomService;
import com.atu.extensible.util.JsonUtil;
import com.atu.extensible.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ClassroomServiceImpl implements ClassroomService {
    @Resource
    private ClassroomMapper classroomMapper;
    @Resource
    private StudentMapper studentMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<Classroom> findAll() {
        log.info("获取所有的班级信息");
        Map<Object,Object> map = redisUtil.hmget("classroom");
        String classroomJsonStr = map.values().toString();
        return JsonUtil.string2List(classroomJsonStr,Classroom.class);
    }

    @Override
    public Classroom findOne(Integer classroomId) {
        log.info("获取一个班级的信息");
        if (classroomId == null){
            return null;
        }
        String classroomStr = (String) redisUtil.hget("classroom", String.valueOf(classroomId));
        if (classroomStr == null || "".equals(classroomStr.trim())){
            Classroom classroom = classroomMapper.findById(classroomId);
            if (classroom == null){
                throw new BaseException(ErrorCodeEnum.MISS_INFO_ERROR);
            }
            redisUtil.hset("classroom",String.valueOf(classroomId),JsonUtil.obj2String(classroom));
            return classroom;
        }
        return JsonUtil.string2Obj(classroomStr,Classroom.class);
    }

    @Override
    public String insertOne(Classroom classroom) {
        log.info("插入一条新的班级数据");
        Integer classroomId = classroomMapper.insertOne(classroom);
        classroom.setId(classroomId);
        redisUtil.hset("classroom",String.valueOf(classroomId),JsonUtil.obj2String(classroom));
        return BaseConstant.SUCCESS;
    }

    @Override
    public String updateOne(Classroom classroom) {
        log.info("更新一个班级的信息");
        Integer grade = classroom.getGrade();
        Integer classNo = classroom.getClassNo();
        if (grade != null && classNo != null){
            List<Classroom> classroomList = classroomMapper.findByGradeAndClassNo(grade,classNo);
            if (classroomList.size() != 0){
                throw new BaseException(ErrorCodeEnum.OPERATION_DENY_ERROR);
            }
        }
        Integer classroomId = classroom.getId();
        Classroom originClassroomInfo = classroomMapper.findById(classroomId);
        if (originClassroomInfo == null){
            classroomMapper.insertOne(classroom);
        } else {
            classroomMapper.updateOne(classroom);
        }
        redisUtil.hset("classroom",String.valueOf(classroomId),JsonUtil.obj2String(classroom));
        return BaseConstant.SUCCESS;
    }

    @Override
    public String deleteOne(Integer classroomId) {
        log.info("删除一个班级的信息");
        if (classroomId == null){
            return BaseConstant.FAIL;
        }
        List<Student> studentList = studentMapper.findByClassroomId(classroomId);
        if (studentList.size() != 0){
            throw new BaseException(ErrorCodeEnum.OPERATION_DENY_ERROR);
        }
        classroomMapper.deleteById(classroomId);
        redisUtil.hdel("classroom",String.valueOf(classroomId));
        return BaseConstant.SUCCESS;
    }
}
