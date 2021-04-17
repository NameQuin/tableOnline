package team.tb.pojo;

import java.io.Serializable;

public class AccessClass implements Serializable {
    private Integer id;

    private String tabid;

    private String classid;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTabid() {
        return tabid;
    }

    public void setTabid(String tabid) {
        this.tabid = tabid == null ? null : tabid.trim();
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid == null ? null : classid.trim();
    }

    @Override
    public String toString() {
        return "AccessClass{" +
                "id=" + id +
                ", tabid='" + tabid + '\'' +
                ", classid='" + classid + '\'' +
                '}';
    }
}
