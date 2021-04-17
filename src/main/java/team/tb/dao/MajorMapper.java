package team.tb.dao;

import java.util.List;
import team.tb.pojo.Major;

public interface MajorMapper {
    int deleteByPrimaryKey(Integer mid);

    int insert(Major record);

    Major selectByPrimaryKey(Integer mid);

    List<Major> selectAll();

    int updateByPrimaryKey(Major record);

    /**
     * 根据院系获取专业
     * @param departmentId
     * @return
     */
    List<Major> getMajorByDepartmentId(Integer departmentId);
}
