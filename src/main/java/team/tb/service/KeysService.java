package team.tb.service;

import team.tb.pojo.Keys;

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
}
