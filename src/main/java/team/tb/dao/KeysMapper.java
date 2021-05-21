package team.tb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import team.tb.pojo.Keys;
import team.tb.pojo.UserInfo;

public interface KeysMapper {
    int deleteByPrimaryKey(Integer kid);

    int insert(Keys record);

    Keys selectByPrimaryKey(Integer kid);

    List<Keys> selectAll();

    int updateByPrimaryKey(Keys record);

    Keys selectLastOne();

    void insertForKey(Keys key);

    /**
     * 获得该字段下的所有
     * @param uid
     * @return
     */
    List<Keys> getUserAllInfo(@Param("uid") Integer uid);
}
