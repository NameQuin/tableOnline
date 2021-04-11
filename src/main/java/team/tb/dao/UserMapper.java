package team.tb.dao;

import java.util.List;

import com.beust.jcommander.Parameter;
import org.apache.ibatis.annotations.Param;
import team.tb.pojo.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer uid);

    int insert(User record);

    User selectByPrimaryKey(Integer uid);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    User findUserByUsernameAndPwd(User user);
}
