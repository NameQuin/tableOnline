package team.tb.dao;

import java.util.List;
import team.tb.pojo.AccessDepartment;

public interface AccessDepartmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccessDepartment record);

    AccessDepartment selectByPrimaryKey(Integer id);

    List<AccessDepartment> selectAll();

    int updateByPrimaryKey(AccessDepartment record);
}
