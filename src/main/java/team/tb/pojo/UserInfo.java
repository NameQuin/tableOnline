package team.tb.pojo;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private Integer ufid;

    private String kid;

    private String value;

    private String uid;

    private static final long serialVersionUID = 1L;

    public UserInfo() {
    }

    public UserInfo(String kid, String value, String uid) {
        this.kid = kid;
        this.value = value;
        this.uid = uid;
    }

    public Integer getUfid() {
        return ufid;
    }

    public void setUfid(Integer ufid) {
        this.ufid = ufid;
    }

    public String getKid() {
        return kid;
    }

    public void setKid(String kid) {
        this.kid = kid == null ? null : kid.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "ufid=" + ufid +
                ", kid='" + kid + '\'' +
                ", value='" + value + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }
}
