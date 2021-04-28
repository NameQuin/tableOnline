package team.tb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import team.tb.pojo.Department;

public interface DepartmentMapper {
    int deleteByPrimaryKey(Integer did);

    int insert(Department record);

    Department selectByPrimaryKey(Integer did);

    List<Department> selectAll();

    int updateByPrimaryKey(Department record);

    /**
     * 根据年级Id查找院系
     * @param grades
     * @return
     */
    List<Department> getDepartmentByGrade(@Param("grades") Integer[] grades);
}
