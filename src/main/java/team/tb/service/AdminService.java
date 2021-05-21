package team.tb.service;

import com.sun.istack.internal.NotNull;
import org.dom4j.DocumentException;
import sun.rmi.server.LoaderHandler;
import team.tb.common.FormField;
import team.tb.common.FormInfo;
import team.tb.common.KeysWithUid;
import team.tb.pojo.Keys;
import team.tb.pojo.TableInfo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface AdminService {



    /**
     * 创建一张表单
     * @param data
     * @param realPath
     * @param basePath
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
     * 修改表单信息
     * @param formId
     * @param formTitle
     * @param startTime
     * @param endTime
     */
    void changeFormInfo(Integer formId, String formTitle, LocalDateTime startTime, LocalDateTime endTime, String realPath, String basePath) throws NoSuchMethodException, DocumentException, IOException;

    /**
     * 从文件中读出所有的字段信息返回
     * @param formId
     * @param realPath
     * @return
     */
    Map<String, Object> getFormAllFields(Integer formId, String realPath) throws DocumentException;

    /**
     * 封禁与解封普通用户
     * @param uid
     * @param status
     * @return
     */
    int changeUserStatus(Integer uid, Integer status);

    /**
     * 根据id获取普通用户的所有信息
     * @param userId
     * @return
     */
    List<Keys> getUserAllInfo(Integer userId);

    /**
     * 管理员修改普通用户的信息
     * @param changedUserInfo
     * @return
     */
    int modifyUserInfoByAdmin(KeysWithUid changedUserInfo);

    /**
     * 管理员修改自己的信息
     * @param adminInfo
     * @param uid
     * @return
     */
    int modifyAdminInfoBySelf(List<Keys> adminInfo, String uid);

    /**
     * 管理员向修改自己的密码
     * @param uid
     * @param oldPwd
     * @param newPwd
     * @return
     */
    int changeAdminPwdBySelf(Integer uid, String oldPwd, String newPwd);

    /**
     * 重置用户密码
     * @param uid
     * @return
     */
    int resetUserPwd(Integer uid);
}
