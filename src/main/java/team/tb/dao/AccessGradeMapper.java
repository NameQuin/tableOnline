package team.tb.dao;

import java.util.List;
import team.tb.pojo.AccessGrade;

public interface AccessGradeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccessGrade record);

    AccessGrade selectByPrimaryKey(Integer id);

    List<AccessGrade> selectAll();

    int updateByPrimaryKey(AccessGrade record);
}