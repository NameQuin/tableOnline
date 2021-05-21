package team.tb.common;

import team.tb.pojo.Keys;

import java.util.List;

/**
 * 为了存放管理员对用户信息修改后的数据
 */
public class KeysWithUid {

    private String userId;
    private List<Keys> data;

    public KeysWithUid() {
    }

    public KeysWithUid(String userId, List<Keys> data) {
        this.userId = userId;
        this.data = data;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Keys> getData() {
        return data;
    }

    public void setData(List<Keys> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "KeysWithUid{" +
                "userId='" + userId + '\'' +
                ", data=" + data +
                '}';
    }
}
