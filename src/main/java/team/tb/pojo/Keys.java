package team.tb.pojo;

import java.io.Serializable;

public class Keys implements Serializable {
    private Integer kid;

    private String kname;

    private static final long serialVersionUID = 1L;

    public Integer getKid() {
        return kid;
    }

    public void setKid(Integer kid) {
        this.kid = kid;
    }

    public String getKname() {
        return kname;
    }

    public void setKname(String kname) {
        this.kname = kname == null ? null : kname.trim();
    }
}