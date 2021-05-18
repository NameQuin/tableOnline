package team.tb.dao;

import java.util.Arrays;
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

    /**
     * 根据专业查找用户
     * @param majors
     * @return
     */
    List<User> getUserByMajor(@Param("majors") Integer[] majors);

    /**
     * 根据院系查找用户
     * @param departments
     * @return
     */
    List<User> getUserByDepartment(@Param("departments") Integer[] departments);

    /**
     * 根据年级查找用户
     * @param grades
     * @return
     */
    List<User> getUserByGrade(@Param("grades") Integer[] grades);

    /**
     * 选出所有普通用户
     * @return
     */
    List<String> selectAllNormalUserId();

    /**
     * 获得管理员数据
     * @param page
     * @param limit
     * @return
     */
    List<User> getAdminList(@Param("page") Integer page, @Param("limit") Integer limit);

    /**
     * 获得管理员数量
     * @return
     */
    int getAdminCount();

    /**
     * 根据名字模糊搜索管理员
     * @param username
     * @param page
     * @param limit
     * @return
     */
    List<User> searchAdmin(@Param("username") String username, @Param("page") Integer page, @Param("limit") Integer limit);

    /**
     * 名字模糊搜索管理员数据条数
     * @param username
     * @return
     */
    int searchAdminCount(@Param("username") String username);

    /**
     * 普通用户封禁与解封状态
     * @param uid
     * @param status
     * @return
     */
    int changeUserStatus(@Param("uid") Integer uid, @Param("status") Integer status);

    /**
     * 管理员封禁与解封
     * @param uid
     * @param status
     * @return
     */
    int changeAdminStatus(@Param("uid") Integer uid, @Param("status") Integer status);
}
