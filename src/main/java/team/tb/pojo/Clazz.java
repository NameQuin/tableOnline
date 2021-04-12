package team.tb.pojo;

import java.io.Serializable;

public class Clazz implements Serializable {
    private Integer cid;

    private String cnum;

    private String cmajor;

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

    public String getCmajor() {
        return cmajor;
    }

    public void setCmajor(String cmajor) {
        this.cmajor = cmajor == null ? null : cmajor.trim();
    }
}