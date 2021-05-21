package team.tb.service;

import team.tb.pojo.Keys;
import team.tb.pojo.UserInfo;

import java.util.List;

public interface KeysService {
    // 获取所有的
    List<Keys> getAllKeys();

    /**
     * 获取最后一条记录
     * @return
     */
    Keys selectLastOne();

    /**
     * 插入数据获取自增id
     * @param key
     */
    void insertForKey(Keys key);

    /**
     * 获得指定普通用户id的所有字段信息
     * @param uid
     * @return
     */
    List<Keys> getUserAllInfo(Integer uid);
}
