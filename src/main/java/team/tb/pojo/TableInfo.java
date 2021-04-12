package team.tb.pojo;

import java.io.Serializable;
import java.util.Date;

public class TableInfo implements Serializable {
    private Integer tfid;

    private String tftitle;

    private Date tfbegintime;

    private Date tfendtime;

    private String tfsrc;

    private Integer tfflag;

    private String tfcreator;

    private String tfkeys;

    private String tfcondition;

    private static final long serialVersionUID = 1L;

    public Integer getTfid() {
        return tfid;
    }

    public void setTfid(Integer tfid) {
        this.tfid = tfid;
    }

    public String getTftitle() {
        return tftitle;
    }

    public void setTftitle(String tftitle) {
        this.tftitle = tftitle == null ? null : tftitle.trim();
    }

    public Date getTfbegintime() {
        return tfbegintime;
    }

    public void setTfbegintime(Date tfbegintime) {
        this.tfbegintime = tfbegintime;
    }

    public Date getTfendtime() {
        return tfendtime;
    }

    public void setTfendtime(Date tfendtime) {
        this.tfendtime = tfendtime;
    }

    public String getTfsrc() {
        return tfsrc;
    }

    public void setTfsrc(String tfsrc) {
        this.tfsrc = tfsrc == null ? null : tfsrc.trim();
    }

    public Integer getTfflag() {
        return tfflag;
    }

    public void setTfflag(Integer tfflag) {
        this.tfflag = tfflag;
    }

    public String getTfcreator() {
        return tfcreator;
    }

    public void setTfcreator(String tfcreator) {
        this.tfcreator = tfcreator == null ? null : tfcreator.trim();
    }

    public String getTfkeys() {
        return tfkeys;
    }

    public void setTfkeys(String tfkeys) {
        this.tfkeys = tfkeys == null ? null : tfkeys.trim();
    }

    public String getTfcondition() {
        return tfcondition;
    }

    public void setTfcondition(String tfcondition) {
        this.tfcondition = tfcondition == null ? null : tfcondition.trim();
    }
}