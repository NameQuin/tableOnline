package team.tb.dao;

import java.util.List;
import team.tb.pojo.Department;

public interface DepartmentMapper {
    int deleteByPrimaryKey(Integer did);

    int insert(Department record);

    Department selectByPrimaryKey(Integer did);

    List<Department> selectAll();

    int updateByPrimaryKey(Department record);

    /**
     * 根据年级Id查找院系
     * @param gradeId
     * @return
     */
    List<Department> getDepartmentByGrade(Integer gradeId);
}
