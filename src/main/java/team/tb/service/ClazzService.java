package team.tb.service;

import team.tb.pojo.Clazz;

import java.util.List;

public interface ClazzService {
    /**
     * 根据院系获得班级
     * @param departmentId
     * @return
     */
    List<Clazz> getClazzByDepartmentId(Integer majorId);
}
