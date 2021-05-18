package team.tb.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.internal.NotNull;
import net.sf.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class TableInfo implements Serializable {
    private Integer tfid;

    private String tftitle;

    //设置时间格式，防止在转换成json时变成秒数
//    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") // 入参格式化
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8") // 将出参格式化
    private LocalDateTime tfbegintime;

//    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private LocalDateTime tfendtime;

    private String tfsrc;

    private Integer tfflag;

    private String tfcreator;

    private String tfkeys;

    private String tfcondition;

    private Integer tfstatus;

    private static final long serialVersionUID = 1L;

    public TableInfo() {
    }

    public TableInfo(Integer tfid, String tftitle, LocalDateTime tfbegintime, LocalDateTime tfendtime, Integer tfflag) {
        this.tfid = tfid;
        this.tftitle = tftitle;
        this.tfbegintime = tfbegintime;
        this.tfendtime = tfendtime;
        this.tfflag = tfflag;
    }

    public TableInfo(String tftitle, LocalDateTime tfbegintime, LocalDateTime tfendtime, String tfsrc, String tfcreator,
                     @NotNull String tfcondition, Integer tfflag) {
        this.tftitle = tftitle;
        this.tfbegintime = tfbegintime;
        this.tfendtime = tfendtime;
        this.tfsrc = tfsrc;
        this.tfcreator = tfcreator;
        this.tfcondition = tfcondition;
        this.tfflag = tfflag;
    }

    public Integer getTfstatus() {
        return tfstatus;
    }

    public void setTfstatus(Integer tfstatus) {
        this.tfstatus = tfstatus;
    }

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

    public LocalDateTime getTfbegintime() {
        return tfbegintime;
    }

    public void setTfbegintime(LocalDateTime tfbegintime) {
        this.tfbegintime = tfbegintime;
    }

    public LocalDateTime getTfendtime() {
        return tfendtime;
    }

    public void setTfendtime(LocalDateTime tfendtime) {
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

    @Override
    public String toString() {
        return "TableInfo{" +
                "tfid=" + tfid +
                ", tftitle='" + tftitle + '\'' +
                ", tfbegintime=" + tfbegintime +
                ", tfendtime=" + tfendtime +
                ", tfsrc='" + tfsrc + '\'' +
                ", tfflag=" + tfflag +
                ", tfcreator='" + tfcreator + '\'' +
                ", tfkeys='" + tfkeys + '\'' +
                ", tfcondition='" + tfcondition + '\'' +
                ", tfstatus=" + tfstatus +
                '}';
    }
}
