package team.tb.service;

import team.tb.pojo.User;

import java.util.List;

public interface UserService {
    /**
     * 根据用户名和密码查询未被封禁的用户，实现登录
     * @param user
     * @return
     */
    User findUserByUsernameAndPwd(User user);

    /**
     * 获取所有普通用户
     * @param page
     * @param limit
     * @return
     */
    List<User> getUserList(Integer page, Integer limit);

    /**
     * 普通用户数目
     * @return
     */
    int getCount();

    /**
     * 根据条件查找用户信息
     * @param grade
     * @param department
     * @param major
     * @param clazz
     * @param username
     * @return
     */
    List<User> getUserOnCondition(Integer grade, Integer department, Integer major, Integer clazz, String username, Integer page, Integer limit);

    /**
     * 根据条件查找符合的数据条数
     * @param grade
     * @param department
     * @param major
     * @param clazz
     * @param username
     * @return
     */
    Integer getUserCountOnCondition(Integer grade, Integer department, Integer major, Integer clazz, String username);
}
