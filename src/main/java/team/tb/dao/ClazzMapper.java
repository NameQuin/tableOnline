package team.tb.dao;

import java.util.List;
import team.tb.pojo.Clazz;

public interface ClazzMapper {
    int deleteByPrimaryKey(Integer cid);

    int insert(Clazz record);

    Clazz selectByPrimaryKey(Integer cid);

    List<Clazz> selectAll();

    int updateByPrimaryKey(Clazz record);

    /**
     * 根据院系获得班级
     * @param majorId
     * @return
     */
    List<Clazz> getClazzByDepartmentId(Integer majorId);
}
