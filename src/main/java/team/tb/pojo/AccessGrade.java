package team.tb.pojo;

import java.io.Serializable;

public class AccessGrade implements Serializable {
    private Integer id;

    private String tabid;

    private String gradeid;

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

    public String getGradeid() {
        return gradeid;
    }

    public void setGradeid(String gradeid) {
        this.gradeid = gradeid == null ? null : gradeid.trim();
    }

    @Override
    public String toString() {
        return "AccessGrade{" +
                "id=" + id +
                ", tabid='" + tabid + '\'' +
                ", gradeid='" + gradeid + '\'' +
                '}';
    }
}
