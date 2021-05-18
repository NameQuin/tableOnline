package team.tb.service;

import team.tb.pojo.UserInfo;

public interface UserInfoService {
    /**
     * 返回自增主键的部分信息插入
     * @param userInfo
     */
    void insertWithoutKey(UserInfo userInfo);

    /**
     * 根据字段Id和用户id获取该字段值
     * @param keyId
     * @param userId
     * @return
     */
    UserInfo getUserInfoById(Integer keyId, Integer userId);

    /**
     * 特定的几个带引用的字段值查询
     * 引用字段需要二次查询，年级，院系，专业，班级(keyId为5，4, 10, 6)
     * @param keyId
     * @param userId
     * @return
     */
    UserInfo getUserInfoWithReference(Integer keyId, Integer userId);

    /**
     * 更新指定用户的某一个字段值
     * @param userInfo
     * @return
     */
    Integer updateValue(UserInfo userInfo);
}
