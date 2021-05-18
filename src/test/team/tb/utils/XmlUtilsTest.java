package team.tb.utils;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultElement;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.testng.reporters.XMLUtils;
import team.tb.common.FormField;
import team.tb.common.FormInfo;
import team.tb.pojo.Keys;

import java.io.*;
import java.lang.reflect.Field;
import java.sql.SQLOutput;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class XmlUtilsTest {
    @Test
    public void test01() throws IOException {
        String realPath = "E:\\JetBrainJava\\tableOnline\\target\\tableOnline\\WEB-INF\\formFiles\\";
        String basePath = "E:\\JetBrainJava\\tableOnline\\src\\main\\webapp\\WEB-INF\\formFiles\\";
        // 创建测试数据
        List<String> data = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i < 10; i++){
            data.add(String.valueOf(random.nextInt(50)));
        }
        List<FormField> fields = new ArrayList<>();
        List<String> options = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            options.add("选项"+i);
        }
        FormField formField1 = new FormField("1", null, "text", "姓名", null, true, true,true, false, "");
        FormField formField2 = new FormField("2", null, "select", "单选", options, true, true, true, true, "");
        FormField formField3 = new FormField("2", "1", "checkbox", "多选", options, true, true, false, true, "");
        fields.add(formField1);
        fields.add(formField2);
        fields.add(formField3);
        FormInfo formInfo = new FormInfo(true, "测试",data, data, data, data, data, data, fields);

        // 创建一个Document对象
        Document doc = DocumentHelper.createDocument();
        // 创建根对象
        org.dom4j.Element form = doc.addElement("form");
        org.dom4j.Element header = form.addElement("header");
        // 创建一级子元素
        // 标题
        org.dom4j.Element formTitle = header.addElement("form-title").addText(formInfo.getFormTitle());
        // 是否提交到数据库
        org.dom4j.Element submit2Db = header.addElement("submit-to-db").addText(formInfo.getSubmitToDb().toString());
        // 时间范围
        org.dom4j.Element timeRage = header.addElement("time-range")
                .addText(formInfo.getTimeRange().get(0)+"|"+formInfo.getTimeRange().get(1));
        // 年级
        org.dom4j.Element grades = header.addElement("grades");
        List<String> gradesList = formInfo.getGrades();
        if(gradesList != null && gradesList.size() > 0){
            StringBuilder gradesText = new StringBuilder();
            for (String s : gradesList) {
                gradesText.append(s+"/");
            }
            grades.setText(gradesText.substring(0, gradesText.length()-1));
        }
        // 院系
        org.dom4j.Element departments = header.addElement("departments");
        List<String> departmentsList = formInfo.getDepartments();
        if(departmentsList != null && departmentsList.size() > 0){
            StringBuilder departmentsText = new StringBuilder();
            for (String s : departmentsList) {
                departmentsText.append(s).append("/");
            }
            departments.setText(departmentsText.substring(0, departmentsText.length()-1));
        }
        // 专业
        org.dom4j.Element majors = header.addElement("majors");
        List<String> majorsList = formInfo.getMajors();
        if(majorsList != null && majorsList.size() > 0){
            StringBuilder majorsText = new StringBuilder();
            for (String s : majorsList) {
                majorsText.append(s).append("/");
            }
            majors.setText(majorsText.substring(0, majorsText.length()-1));
        }
        // 班级
        org.dom4j.Element classes = header.addElement("classes");
        List<String> classesList = formInfo.getClasses();
        if(classesList != null && classesList.size() > 0){
            StringBuilder classesText = new StringBuilder();
            for (String s : classesList) {
                classesText.append(s).append("/");
            }
            classes.setText(classesText.substring(0, classesText.length()-1));
        }
        // 学生
        org.dom4j.Element students = header.addElement("students");
        List<String> studentsList = formInfo.getStudents();
        if(studentsList != null && studentsList.size() > 0){
            StringBuilder studentsText = new StringBuilder();
            for (String s : studentsList) {
                studentsText.append(s).append("/");
            }
            students.setText(studentsText.substring(0, studentsText.length()-1));
        }
        // 字段信息
        org.dom4j.Element formItems = header.addElement("form-items");
        List<FormField> formFields = formInfo.getFormData();
        if(formFields != null && formFields.size() > 0){
            for (FormField formField : formFields) {
                org.dom4j.Element formItem = formItems.addElement("form-item");
                formItem.addElement("id").addText(formField.getId().toString());
                formItem.addElement("kid").addText(formField.getKid() != null ? formField.getKid() : "null");
                formItem.addElement("type").addText(formField.getType());
                formItem.addElement("label").addText(formField.getLabel());
                formItem.addElement("req").addText(formField.getReq().toString());
                formItem.addElement("can-edit").addText(formField.getCanEdit().toString());
                formItem.addElement("new-field").addText(formField.getNewField().toString());
                String hasOption = formField.getHasOption().toString();
                formItem.addElement("has-option").addText(hasOption);
                org.dom4j.Element options2 = formItem.addElement("options");
                if("true".equals(hasOption)){
                    List<String> options3 = formField.getOptions();
                    for (String s : options3) {
                        options2.addElement("option").addText(s);
                    }
                }

            }
        }
        // 主体数据
        Element body = form.addElement("body");
        for(int i = 0; i < 10; i++){
            Element user = body.addElement("user").addAttribute("id", String.valueOf(i));
            for (int j = 0; j < formFields.size(); j++) {
                FormField field = formFields.get(j);
                Element fieldElement = user.addElement("field")
                        .addAttribute("kid", field.getKid())
                        .addAttribute("label", field.getLabel())
                        .addAttribute("value", "");
            }
        }
        // 设置输出流来生成一个xml文件
        OutputStream os = new FileOutputStream(basePath+"demo.xml");
        // format输出格式格式刷
        OutputFormat format = OutputFormat.createPrettyPrint();
        // 设置xml编码
        format.setEncoding("utf-8");
        // 创建写入对象
        XMLWriter xw = new XMLWriter(os, format);
        // 写入文档
        xw.write(doc);
        // 清空缓存关闭资源
        xw.flush();
        xw.close();
    }

    @Test
    public void test02(){
        // 创建测试数据
        List<String> data = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i < 10; i++){
            data.add("  "+random.nextInt(50) +"  ");
        }
        List<FormField> fields = new ArrayList<>();
        List<String> options = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            options.add(" 选项"+i+"     ");
        }
        FormField formField1 = new FormField("1", null, " text ", " 姓名 ", null, true, true,true, false, "");
        FormField formField2 = new FormField("2", null, " select ", " 单选 ", options, true, true, true, true, "");
        FormField formField3 = new FormField("2", "1", " checkbox ", " 多选  ", options, true, true, false, true, "");
        fields.add(formField1);
        fields.add(formField2);
        fields.add(formField3);
        FormInfo formInfo = new FormInfo(true, " 测试 ",data, data, data, data, data, data, fields);
        System.out.println(formInfo);
        StringUtils.trimData(formInfo);
        System.out.println(formInfo);
    }

    private void ts(@NotNull List<String> students){
        List<String> ret = new ArrayList<>();
        for (int i = 0; i < students.size(); i++) {
            ret.add("new"+i);
        }
        students = ret;
    }

    /**
     * 测试使用xpath读取xml文件
     */
    @Test
    public void test03() throws DocumentException {
        String path = "E:\\JetBrainJava\\tableOnline\\src\\main\\webapp\\WEB-INF\\formFiles\\demo.xml";
        Document doc = XmlUtils.readDocument(path);
//        List<Element> list = XmlUtils.getElements(doc, "form-title");
        List<Element> elements = doc.selectNodes("//form-title");
        System.out.println(elements.size());
        elements.forEach(s -> System.out.println(s.getText()));
    }

    /**
     * 读取xml并修改
     * @throws DocumentException
     * @throws IOException
     */
    @Test
    public void test04() throws DocumentException, IOException {
        String path = "E:\\JetBrainJava\\tableOnline\\target\\tableOnline\\WEB-INF\\formFiles\\demo.xml";
        Document doc = XmlUtils.readDocument(path);
        List<Element> elements = (List<Element>) doc.selectNodes("//form-items/form-item");
        for (Element element : elements) {
            element.element("id").getText();
            element.element("kid").getText();
            element.element("type").getText();
            element.element("label").getText();
            element.element("req").getText();
            element.element("can-edit").getText();
            element.element("new-field").getText();
            String hasOptions = element.element("has-option").getText();
            List<Element> options = element.selectNodes("options/option");
            options.forEach(s -> System.out.println(s.getText()));
        }
    }

    @Test
    public void test05(){
        List<FormField> list = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i < 10; i++){
            FormField field = new FormField();
            field.setKid(""+i);
            field.setCanEdit(random.nextBoolean());
            list.add(field);
        }
        list.forEach(s -> System.out.println("kid = "+s.getKid() + "\t canEdit = " + s.getCanEdit()));

        Map<String, FormField> collect = list.stream().filter(s -> s.getCanEdit()).collect(Collectors.toMap(FormField::getKid, s -> s));
        collect.forEach((k, v) -> System.out.println("key = " + k + "\tvalue = " + v));
    }
}
