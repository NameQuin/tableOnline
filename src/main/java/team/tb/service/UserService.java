package team.tb.service;

import org.dom4j.DocumentException;
import team.tb.common.FormInfo;
import team.tb.pojo.TableInfo;
import team.tb.pojo.User;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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

    /**
     * 选择所有的用户
     * @return
     */
    List<User> selectAll();

    /**
     * 根据班级获取学生
     * @param classes
     * @return
     */
    List<User> getUserByClass(Integer[] classes);

    /**
     * 根据专业获取学生
     * @param majors
     * @return
     */
    List<User> getUserByMajor(Integer[] majors);

    /**
     * 根据院系获取学生
     * @param departments
     * @return
     */
    List<User> getUserByDepartment(Integer[] departments);

    /**
     * 根据年级获取学生
     * @param grades
     * @return
     */
    List<User> getUserByGrade(Integer[] grades);

    /**
     * 选择所有用户的id
     * @return
     */
    List<String> selectAllNormalUserId();

    /**
     * 获取当前普通用户所有能填写的表
     * @param uid
     * @return
     */
    Map<String, Object> getAllFormCurUser(String uid, Integer page, Integer limit);

    /**
     * 搜索普通用户可以填写的表单
     * @param id
     * @param formTitle
     * @param startTime
     * @param endTime
     * @param page
     * @param limit
     * @return
     */
    Map<String, Object> searchForm(String id, String formTitle, String startTime, String endTime, Integer page, Integer limit);

    /**
     * 从文件中读出所有的字段信息返回
     * @param formId
     * @param realPath
     * @return
     */
    Map<String, Object> getFormAllFields(Integer formId, String realPath, String id) throws DocumentException;

    /**
     * 将用户填写好的表单数据更新原来的表单
     * @param realPath
     * @param data
     * @param uid
     * @return
     */
    int updateUserInfoInForm(String realPath, String basePath, FormInfo data, String uid) throws DocumentException, IOException;

    /**
     * 获得管理员信息
     * @param page
     * @param limit
     * @return
     */
    List<User> getAdminList(Integer page, Integer limit);

    /**
     * 管理员数据条数
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
    List<User> searchAdmin(String username, Integer page, Integer limit);

    /**
     * 符合搜索条件的管理员数量
     * @param username
     * @return
     */
    int searchAdminCount(String username);

    /**
     * 普通用户封禁与解封
     * @param uid
     * @param status
     * @return
     */
    int changeUserStatus(Integer uid, Integer status);

    /**
     * 管理员封禁与解封
     * @param uid
     * @param status
     * @return
     */
    int changeAdminStatus(Integer uid, Integer status);
}
