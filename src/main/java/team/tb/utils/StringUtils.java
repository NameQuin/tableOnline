package team.tb.utils;

import team.tb.common.FormInfo;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 一些关于String操作的工具方法
 */
public class StringUtils {

    /**
     * 判断字符串是否为空或Null
     * @param str
     * @return
     */
    public static boolean isEmpty(CharSequence str){
        return str == null || str.length() == 0;
    }

    /**
     * 判断数组是否为空或null
     * @param strs
     * @return
     */
    public static boolean isEmptyArray(CharSequence[] strs){
        return strs == null || strs.length == 0;
    }

    /**
     * 将传递表单数据所有字符串去掉多余空格
     * @param formInfo
     */
    public static void trimData(FormInfo formInfo){
        formInfo.setFormTitle(formInfo.getFormTitle().trim());
        List<String> grades = formInfo.getGrades().stream().map(String::trim).collect(Collectors.toList());
        List<String> departments = formInfo.getDepartments().stream().map(String::trim).collect(Collectors.toList());
        List<String> majors = formInfo.getMajors().stream().map(String::trim).collect(Collectors.toList());
        List<String> classes = formInfo.getClasses().stream().map(String::trim).collect(Collectors.toList());
        List<String> students = formInfo.getStudents().stream().map(String::trim).collect(Collectors.toList());
        List<String> timeRange = formInfo.getTimeRange().stream().map(String::trim).collect(Collectors.toList());
        formInfo.setGrades(grades);
        formInfo.setDepartments(departments);
        formInfo.setMajors(majors);
        formInfo.setClasses(classes);
        formInfo.setStudents(students);
        formInfo.setTimeRange(timeRange);
        formInfo.getFormData().forEach(s -> {
            s.setLabel(s.getLabel().trim());
            s.setType(s.getType().trim());
            if(s.getHasOption()){
                s.setOptions(s.getOptions().stream().map(String::trim).collect(Collectors.toList()));
            }
        });
    }
}
