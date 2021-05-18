package team.tb.dao;

import java.util.List;
import team.tb.pojo.Keys;

public interface KeysMapper {
    int deleteByPrimaryKey(Integer kid);

    int insert(Keys record);

    Keys selectByPrimaryKey(Integer kid);

    List<Keys> selectAll();

    int updateByPrimaryKey(Keys record);

    Keys selectLastOne();

    void insertForKey(Keys key);
}
