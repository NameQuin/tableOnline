package team.tb.pojo;

import java.io.Serializable;

public class AccessMajor implements Serializable {
    private Integer id;

    private String tabid;

    private String majorid;

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

    public String getMajorid() {
        return majorid;
    }

    public void setMajorid(String majorid) {
        this.majorid = majorid == null ? null : majorid.trim();
    }

    @Override
    public String toString() {
        return "AccessMajor{" +
                "id=" + id +
                ", tabid='" + tabid + '\'' +
                ", majorid='" + majorid + '\'' +
                '}';
    }
}
