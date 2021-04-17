package team.tb.pojo;

import java.io.Serializable;

public class Keys implements Serializable {
    private Integer kid;

    private String kname;

    private Integer ktype;

    private String ktypevalue;

    private String kcnname;

    private String kusvalue;

    private Boolean kchange;

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

    public Integer getKtype() {
        return ktype;
    }

    public void setKtype(Integer ktype) {
        this.ktype = ktype;
    }

    public String getKtypevalue() {
        return ktypevalue;
    }

    public void setKtypevalue(String ktypevalue) {
        this.ktypevalue = ktypevalue == null ? null : ktypevalue.trim();
    }

    public String getKcnname() {
        return kcnname;
    }

    public void setKcnname(String kcnname) {
        this.kcnname = kcnname == null ? null : kcnname.trim();
    }

    public String getKusvalue() {
        return kusvalue;
    }

    public void setKusvalue(String kusvalue) {
        this.kusvalue = kusvalue == null ? null : kusvalue.trim();
    }

    public Boolean getKchange() {
        return kchange;
    }

    public void setKchange(Boolean kchange) {
        this.kchange = kchange;
    }

    @Override
    public String toString() {
        return "Keys{" +
                "kid=" + kid +
                ", kname='" + kname + '\'' +
                ", ktype=" + ktype +
                ", ktypevalue='" + ktypevalue + '\'' +
                ", kcnname='" + kcnname + '\'' +
                ", kusvalue='" + kusvalue + '\'' +
                ", kchange=" + kchange +
                '}';
    }
}
