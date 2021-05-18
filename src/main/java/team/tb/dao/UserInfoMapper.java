package team.tb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import team.tb.pojo.UserInfo;

public interface UserInfoMapper {
    int deleteByPrimaryKey(Integer ufid);

    int insert(UserInfo record);

    UserInfo selectByPrimaryKey(Integer ufid);

    List<UserInfo> selectAll();

    int updateByPrimaryKey(UserInfo record);

    /**
     * 返回自增主键的部分信息插入
     * @param userInfo
     */
    void insertWithoutKey(UserInfo userInfo);

    /**
     * 根据字段id和用户id获取字段值
     * @param keyId
     * @param userId
     * @return
     */
    UserInfo getUserInfoByKeyId(@Param("keyId") Integer keyId, @Param("userId") Integer userId);

    /**
     * 返回年级字段所引用的值
     * @param keyId
     * @param userId
     * @return
     */
    UserInfo getUserGradeInfo(@Param("keyId") Integer keyId, @Param("userId") Integer userId);

    /**
     * 返回院系字段所引用的值
     * @param keyId
     * @param userId
     * @return
     */
    UserInfo getUserDepartmentInfo(@Param("keyId") Integer keyId, @Param("userId") Integer userId);

    /**
     * 返回专业字段所引用的值
     * @param keyId
     * @param userId
     * @return
     */
    UserInfo getUserMajorInfo(@Param("keyId") Integer keyId, @Param("userId") Integer userId);

    /**
     * 返回班级字段所引用的值
     * @param keyId
     * @param userId
     * @return
     */
    UserInfo getUserClassInfo(@Param("keyId") Integer keyId, @Param("userId") Integer userId);

    /**
     * 更新指定用户的指定字段的值
     * @param userInfo
     * @return
     */
    Integer updateValue(UserInfo userInfo);
}
