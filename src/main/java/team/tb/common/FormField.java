package team.tb.common;

import java.util.List;

/**
 * 表格字段信息
 */
public class FormField {
    private String id; // 字段信息在数组中的id
    private String kid; // 数据库中字段id
    private String type; // 字段类型
    private String label; // 字段名
    private List<String> options; // 字段可选项
    private Boolean req; // 是否必填
    private Boolean canEdit; // 是否可编辑
    private Boolean newField; // 是否是新增字段
    private Boolean hasOption; // 是否有选项
    private String value; // 字段填写的值

    public FormField() {
    }

    public FormField(String id, String kid, String type, String label, List<String> options, Boolean req, Boolean canEdit, Boolean newField, Boolean hasOption, String value) {
        this.id = id;
        this.kid = kid;
        this.type = type;
        this.label = label;
        this.options = options;
        this.req = req;
        this.canEdit = canEdit;
        this.newField = newField;
        this.hasOption = hasOption;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKid() {
        return kid;
    }

    public void setKid(String kid) {
        this.kid = kid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public Boolean getReq() {
        return req;
    }

    public void setReq(Boolean req) {
        this.req = req;
    }

    public Boolean getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
    }

    public Boolean getNewField() {
        return newField;
    }

    public void setNewField(Boolean newField) {
        this.newField = newField;
    }

    public Boolean getHasOption() {
        return hasOption;
    }

    public void setHasOption(Boolean hasOption) {
        this.hasOption = hasOption;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return "FormField{" +
                "id='" + id + '\'' +
                ", kid='" + kid + '\'' +
                ", type='" + type + '\'' +
                ", label='" + label + '\'' +
                ", options=" + options +
                ", req=" + req +
                ", canEdit=" + canEdit +
                ", newField=" + newField +
                ", hasOption=" + hasOption +
                ", value='" + value + '\'' +
                '}';
    }
}
