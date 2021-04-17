package team.tb.service;

import team.tb.pojo.Department;

import java.util.List;

public interface DepartmentService {

    /**
     * 根据年级id查找院系
     * @param gradeId
     * @return
     */
    List<Department> getDepartmentByGrade(Integer gradeId);
}
