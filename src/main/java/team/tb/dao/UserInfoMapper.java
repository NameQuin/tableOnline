package team.tb.dao;

import java.util.List;
import team.tb.pojo.UserInfo;

public interface UserInfoMapper {
    int deleteByPrimaryKey(Integer ufid);

    int insert(UserInfo record);

    UserInfo selectByPrimaryKey(Integer ufid);

    List<UserInfo> selectAll();

    int updateByPrimaryKey(UserInfo record);
}