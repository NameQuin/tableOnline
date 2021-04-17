package team.tb.dao;

import java.util.List;
import team.tb.pojo.AccessMajor;

public interface AccessMajorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccessMajor record);

    AccessMajor selectByPrimaryKey(Integer id);

    List<AccessMajor> selectAll();

    int updateByPrimaryKey(AccessMajor record);
}