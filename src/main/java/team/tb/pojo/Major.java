package team.tb.pojo;

import java.io.Serializable;

public class Major implements Serializable {
    private Integer mid;

    private String mname;

    private String mdepartment;

    private static final long serialVersionUID = 1L;

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname == null ? null : mname.trim();
    }

    public String getMdepartment() {
        return mdepartment;
    }

    public void setMdepartment(String mdepartment) {
        this.mdepartment = mdepartment == null ? null : mdepartment.trim();
    }

    @Override
    public String toString() {
        return "Major{" +
                "mid=" + mid +
                ", mname='" + mname + '\'' +
                ", mdepartment='" + mdepartment + '\'' +
                '}';
    }
}
