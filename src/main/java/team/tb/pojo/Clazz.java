package team.tb.pojo;

import java.io.Serializable;

public class Clazz implements Serializable {
    private Integer cid;

    private String cnum;

    private String cdepartment;

    private String cmajor;

    private String cgrade;

    private static final long serialVersionUID = 1L;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getCnum() {
        return cnum;
    }

    public void setCnum(String cnum) {
        this.cnum = cnum == null ? null : cnum.trim();
    }

    public String getCdepartment() {
        return cdepartment;
    }

    public void setCdepartment(String cdepartment) {
        this.cdepartment = cdepartment == null ? null : cdepartment.trim();
    }

    public String getCmajor() {
        return cmajor;
    }

    public void setCmajor(String cmajor) {
        this.cmajor = cmajor == null ? null : cmajor.trim();
    }

    public String getCgrade() {
        return cgrade;
    }

    public void setCgrade(String cgrade) {
        this.cgrade = cgrade == null ? null : cgrade.trim();
    }
}