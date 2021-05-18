package team.tb.common;

import java.util.List;

/**
 * 存放表单信息的bean
 */
public class FormInfo {
    private Boolean submitToDb; // 是否提交至数据库
    private String formTitle; // 表单标题(或者表单id，当填写表单提交时复用)
    private List<String> grades; // 年级信息
    private List<String> departments; // 院系信息
    private List<String> majors; // 专业信息
    private List<String> classes; // 班级信息
    private List<String> students; // 学生信息
    private List<String> timeRange; // 有效期
    private List<FormField> formData; // 表单项信息

    public FormInfo() {
    }

    public FormInfo(Boolean submitToDb, String formTitle, List<String> grades, List<String> departments, List<String> majors, List<String> classes, List<String> students, List<String> timeRange, List<FormField> formData) {
        this.submitToDb = submitToDb;
        this.formTitle = formTitle;
        this.grades = grades;
        this.departments = departments;
        this.majors = majors;
        this.classes = classes;
        this.students = students;
        this.timeRange = timeRange;
        this.formData = formData;
    }

    public List<String> getGrades() {
        return grades;
    }

    public void setGrades(List<String> grades) {
        this.grades = grades;
    }

    public List<String> getDepartments() {
        return departments;
    }

    public void setDepartments(List<String> departments) {
        this.departments = departments;
    }

    public List<String> getMajors() {
        return majors;
    }

    public void setMajors(List<String> majors) {
        this.majors = majors;
    }

    public List<String> getClasses() {
        return classes;
    }

    public void setClasses(List<String> classes) {
        this.classes = classes;
    }

    public List<String> getStudents() {
        return students;
    }

    public void setStudents(List<String> students) {
        this.students = students;
    }

    public List<String> getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(List<String> timeRange) {
        this.timeRange = timeRange;
    }

    public List<FormField> getFormData() {
        return formData;
    }

    public void setFormData(List<FormField> formData) {
        this.formData = formData;
    }

    public Boolean getSubmitToDb() {
        return submitToDb;
    }

    public void setSubmitToDb(Boolean submitToDb) {
        this.submitToDb = submitToDb;
    }

    public String getFormTitle() {
        return formTitle;
    }

    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle == null ? null : formTitle.trim();
    }

    @Override
    public String toString() {
        return "FormInfo{" +
                "submitToDb=" + submitToDb +
                ", formTitle='" + formTitle + '\'' +
                ", grades=" + grades +
                ", departments=" + departments +
                ", majors=" + majors +
                ", classes=" + classes +
                ", students=" + students +
                ", timeRange=" + timeRange +
                ", formData=" + formData +
                '}';
    }
}
