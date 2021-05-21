package team.tb.pojo;

import java.io.Serializable;

public class Keys implements Serializable {
    private Integer kid;

    private String kname; // 字段英文名

    private String ktype; // 字段类型

    private String ktypevalue; // 字段选项

    private String kcnname; //字段中文名

    private String kusvalue; // 字段值英文版（在获取用户信息时存放该字段值）

    private Boolean kchange; // 字段是否可变，仅对普通用户而言

    private static final long serialVersionUID = 1L;

    public Keys() {
    }

    public Keys(String kname, String ktype, String ktypevalue, String kcnname) {
        this.kname = kname;
        this.ktype = ktype;
        this.ktypevalue = ktypevalue;
        this.kcnname = kcnname;
    }

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

    public String getKtype() {
        return ktype;
    }

    public void setKtype(String ktype) {
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
                ", ktype='" + ktype + '\'' +
                ", ktypevalue='" + ktypevalue + '\'' +
                ", kcnname='" + kcnname + '\'' +
                ", kusvalue='" + kusvalue + '\'' +
                ", kchange=" + kchange +
                '}';
    }
}
