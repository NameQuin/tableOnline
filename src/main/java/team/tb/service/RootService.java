package team.tb.service;

import com.sun.istack.internal.NotNull;
import org.dom4j.DocumentException;
import team.tb.common.FormField;
import team.tb.common.FormInfo;
import team.tb.common.KeysWithUid;
import team.tb.pojo.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface RootService {
    /**
     * 获取所有普通用户
     * @param page
     * @param limit
     * @return
     */
    List<User> getUserList(Integer page, Integer limit);

    /**
     * 获取所有普通用户的总数
     * @return
     */
    int getUserCount();

    /**
     * 获得年级信息
     * @return
     */
    List<Grade> getGrade();

    /**
     * 根据年级获取院系信息
     * @param grades
     * @return
     */
    List<Department> getDepartmentByGrade(Integer[] grades);

    /**
     * 根据院系获取专业信息
     * @param departments
     * @return
     */
    List<Major> getMajorByDepartmentId(Integer[] departments);

    /**
     * 根据专业获取班级信息
     * @param majors
     * @return
     */
    List<Clazz> getClazzByMajorId(Integer[] majors);

    /**
     * 根据班级查找学生
     * @param clazzs
     * @return
     */
    List<User> getUserByClass(Integer[] clazzs);

    /**
     * 获取对应条件下的用户数量
     * @param grade
     * @param department
     * @param major
     * @param clazz
     * @param username
     * @return
     */
    int getUserCountOnCondition(Integer grade, Integer department, Integer major, Integer clazz, String username);

    /**
     * 获取对应条件下的用户信息
     * @param grade
     * @param department
     * @param major
     * @param clazz
     * @param username
     * @param page
     * @param limit
     * @return
     */
    List<User> getUserOnCondition(Integer grade, Integer department, Integer major, Integer clazz, String username, Integer page, Integer limit);

    /**
     * 获取管理员信息
     * @param page
     * @param limit
     * @return
     */
    List<User> getAdminList(Integer page, Integer limit);

    /**
     * 获取管理员数量
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
     * 模糊搜索管理员的数据
     * @param username
     * @return
     */
    int getSearchAdminCount(String username);

    /**
     * 查找所有未被删除的表单
     * @param page
     * @param limit
     * @return
     */
    List<TableInfo> getFormList(Integer page, Integer limit);

    /**
     * 所有未被删除表单的数量
     * @return
     */
    Integer getFormCount();

    /**
     * 根据条件查询表单
     * @param formTitle
     * @param startTime
     * @param endTime
     * @param page
     * @param limit
     * @return
     */
    List<TableInfo> searchForm(String formTitle, String startTime, String endTime, Integer page, Integer limit);

    /**
     * 指定条件下的表单数量
     * @param formTitle
     * @param startTime
     * @param endTime
     * @return
     */
    Integer getFormCountOnCondition(String formTitle, String startTime, String endTime);

    /**
     * 修改表单部分信息，标题，起始时间
     * @param formId
     * @param formTitle
     * @param startTime
     * @param endTime
     * @param realPath
     * @param basePath
     */
    void changeFormInfo(Integer formId, String formTitle, LocalDateTime startTime, LocalDateTime endTime, String realPath, String basePath) throws DocumentException, IOException, NoSuchMethodException;

    /**
     * 修改表单标志位达到删除效果
     * @param formId
     * @return
     */
    Integer deleteForm(Integer formId);

    /**
     * 根据id得到表单的标题，id，起始时间数据
     * @param formId
     * @return
     */
    TableInfo getFormById(Integer formId);

    /**
     * 获取表单全部数据，用于页面展示
     * @param formId
     * @param realPath
     * @param formStatus
     * @return
     */
    Map getFormAllFields(Integer formId, String realPath, Integer formStatus) throws DocumentException;

    /**
     * 获得数据库中所有的字段数据
     * @return
     */
    List<Keys> getAllKeys();

    /**
     * 根据信息创建表单
     * @param data
     * @param realPath
     * @param basePath
     * @param creatorId
     */
    void createForm(FormInfo data, String realPath, String basePath, String creatorId) throws IOException, NoSuchMethodException;

    /**
     * 创建表单文件并在数据库插入表单信息
     * @param formInfo
     * @param realPath
     * @param basePath
     * @param creatorId
     * @throws IOException
     */
    void createFormAndTableInfo(FormInfo formInfo, String realPath, String basePath, String creatorId) throws IOException, NoSuchMethodException;

    /**
     * 处理新增字段是否是提交数据库，为新增字段添加id
     * @param fields
     */
    void handleNewField(@NotNull List<FormField> fields, @NotNull Boolean submitToDB);

    /**
     * 获取所有能接收表单的对象
     * @param targetUsers
     * @param classes
     * @param majors
     * @param departments
     * @param grades
     */
    List<String> getTargetUsers(List<String> targetUsers, List<String> classes, List<String> majors,
                                List<String> departments, List<String> grades);

    /**
     * 普通用户的封禁与解封
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

    /**
     * 查询所有已标记删除的表单
     * @param page
     * @param limit
     * @return
     */
    List<TableInfo> getDeleteForm(Integer page, Integer limit);

    /**
     * 获得已被删除标记的表单总数
     * @return
     */
    Integer getDeleteFormCount();

    /**
     * 根据条件搜索已被删除标记的表单
     * @param formTitle
     * @param startTime
     * @param endTime
     * @param page
     * @param limit
     * @return
     */
    List<TableInfo> searchDeleteForm(String formTitle, String startTime, String endTime, Integer page, Integer limit);

    /**
     * 符合条件的被标记删除的表单数
     * @param formTitle
     * @param startTime
     * @param endTime
     * @return
     */
    int searchDeleteFormCount(String formTitle, String startTime, String endTime);

    /**
     * 恢复被删除的表单
     * @param formId
     * @return
     */
    int resetDeleteForm(Integer formId);

    /**
     * 获得用户的所有信息
     * @param userId
     * @return
     */
    List<Keys> getUserAllInfo(Integer userId);

    /**
     * 超级管理员修改用户数据
     * @param changedUserInfo
     * @return
     */
    int modifyUserInfoByRoot(KeysWithUid changedUserInfo);

    /**
     * 修改root管理员数据
     * @param rootInfo
     * @param uid
     * @return
     */
    int modifyRootInfoBySelf(List<Keys> rootInfo, String uid);

    /**
     * root用户修改自己的登录密码
     * @param uid
     * @param oldPwd
     * @param newPwd
     * @return
     */
    int changeRootPwdBySelf(Integer uid, String oldPwd, String newPwd);

    /**
     * 重置用户密码
     * @param uid
     * @return
     */
    int resetUserPwd(Integer uid);

    /**
     * 修改用户的管理员状态，解除与赋予管理员权限
     * @param uid
     * @param level
     * @return
     */
    int changeUserLevel(Integer uid, Integer level);
}
