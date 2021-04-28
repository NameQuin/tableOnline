package team.tb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import team.tb.pojo.Clazz;

public interface ClazzMapper {
    int deleteByPrimaryKey(Integer cid);

    int insert(Clazz record);

    Clazz selectByPrimaryKey(Integer cid);

    List<Clazz> selectAll();

    int updateByPrimaryKey(Clazz record);

    /**
     * 根据院系获得班级
     * @param majors
     * @return
     */
    List<Clazz> getClazzByMajorId(@Param("majors") Integer[] majors);
}
