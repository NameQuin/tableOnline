package team.tb.dao;

import java.util.List;
import team.tb.pojo.Grade;

public interface GradeMapper {
    int deleteByPrimaryKey(Integer gid);

    int insert(Grade record);

    Grade selectByPrimaryKey(Integer gid);

    List<Grade> selectAll();

    int updateByPrimaryKey(Grade record);
}
