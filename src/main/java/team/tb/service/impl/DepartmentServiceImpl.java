package team.tb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.tb.dao.DepartmentMapper;
import team.tb.pojo.Department;
import team.tb.service.DepartmentService;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;
    @Override
    public List<Department> getDepartmentByGrade(Integer gradeId) {
        return departmentMapper.getDepartmentByGrade(gradeId);
    }
}
