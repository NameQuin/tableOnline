package team.tb.pojo;

import java.io.Serializable;

public class Department implements Serializable {
    private Integer did;

    private String dname;

    private String dgrade;

    private static final long serialVersionUID = 1L;

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname == null ? null : dname.trim();
    }

    public String getDgrade() {
        return dgrade;
    }

    public void setDgrade(String dgrade) {
        this.dgrade = dgrade == null ? null : dgrade.trim();
    }

    @Override
    public String toString() {
        return "Department{" +
                "did=" + did +
                ", dname='" + dname + '\'' +
                ", dgrade='" + dgrade + '\'' +
                '}';
    }
}
