package team.tb.dao;

import java.util.List;
import team.tb.pojo.Major;

public interface MajorMapper {
    int deleteByPrimaryKey(Integer mid);

    int insert(Major record);

    Major selectByPrimaryKey(Integer mid);

    List<Major> selectAll();

    int updateByPrimaryKey(Major record);
}
