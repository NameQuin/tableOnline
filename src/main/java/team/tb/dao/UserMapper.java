package team.tb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import team.tb.pojo.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer uid);

    int insert(User record);

    User selectByPrimaryKey(Integer uid);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    /**
     * 根据用户名和密码查询未被封禁用户
     * @param user
     * @return
     */
    User findUserByUsernameAndPwd(User user);

    /**
     * 查询所有普通用户，包括被封禁的号
     * @param page
     * @param limit
     * @return
     */
    List<User> getUserList(@Param("page") Integer page, @Param("limit") Integer limit);

    /**
     * 普通用户人数
     * @return
     */
    int getCount();

    /**
     * 根据条件查找用户
     * @param grade
     * @param department
     * @param major
     * @param clazz
     * @param username
     * @return
     */
    List<User> getUserOnCondition(@Param("grade") Integer grade, @Param("department") Integer department,
                                  @Param("major") Integer major, @Param("clazz") Integer clazz,
                                  @Param("username") String username, @Param("page") Integer page,
                                  @Param("limit") Integer limit);

    /**
     * 根据条件查找符合条件的用户个数
     * @param grade
     * @param department
     * @param major
     * @param clazz
     * @param username
     * @return
     */
    Integer getUserCountOnCondition(@Param("grade") Integer grade, @Param("department") Integer department,
                                       @Param("major") Integer major, @Param("clazz") Integer clazz,
                                       @Param("username") String username);

    /**
     * 根据班级查找用户数
     * @param clazzs
     * @return
     */
    List<User> getUserByClass(@Param("clazzs") Integer[] clazzs);
}
