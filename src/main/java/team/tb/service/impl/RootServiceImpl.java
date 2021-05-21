package team.tb.service.impl;

import com.sun.istack.internal.NotNull;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.tb.common.*;
import team.tb.dao.UserMapper;
import team.tb.pojo.*;
import team.tb.service.*;
import team.tb.utils.DateUtils;
import team.tb.utils.MD5Utils;
import team.tb.utils.StringUtils;
import team.tb.utils.XmlUtils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RootServiceImpl implements RootService {

    @Autowired
    private UserService userService;
    @Autowired
    private GradeService gradeService;
    @Autowired
    private TableInfoService tableInfoService;
    @Autowired
    private KeysService keysService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private MajorService majorService;
    @Autowired
    private ClazzService clazzService;

    @Override
    public List<User> getUserList(Integer page, Integer limit) {
        return userService.getUserList(page, limit);
    }

    @Override
    public int getUserCount() {
        return userService.getCount();
    }

    @Override
    public List<Grade> getGrade() {
        return gradeService.getGrade();
    }

    @Override
    public List<Department> getDepartmentByGrade(Integer[] grades) {
        return departmentService.getDepartmentByGrade(grades);
    }

    @Override
    public List<Major> getMajorByDepartmentId(Integer[] departments) {
        return majorService.getMajorByDepartmentId(departments);
    }

    @Override
    public List<Clazz> getClazzByMajorId(Integer[] majors) {
        return clazzService.getClazzByMajorId(majors);
    }

    @Override
    public List<User> getUserByClass(Integer[] clazzs) {
        return userService.getUserByClass(clazzs);
    }

    @Override
    public int getUserCountOnCondition(Integer grade, Integer department, Integer major, Integer clazz, String username) {
        return userService.getUserCountOnCondition(grade, department, major, clazz, username);
    }

    @Override
    public List<User> getUserOnCondition(Integer grade, Integer department, Integer major, Integer clazz, String username, Integer page, Integer limit) {
        return userService.getUserOnCondition(grade, department, major, clazz, username, page, limit);
    }

    @Override
    public List<User> getAdminList(Integer page, Integer limit) {
        return userService.getAdminList(page, limit);
    }

    @Override
    public int getAdminCount() {
        return userService.getAdminCount();
    }

    @Override
    public List<User> searchAdmin(String username, Integer page, Integer limit) {
        return userService.searchAdmin(username, page, limit);
    }

    @Override
    public int getSearchAdminCount(String username) {
        return userService.searchAdminCount(username);
    }

    @Override
    public List<TableInfo> getFormList(Integer page, Integer limit) {
        return tableInfoService.getFormListByRoot(page, limit);
    }

    @Override
    public Integer getFormCount() {
        return tableInfoService.getFormCountByRoot();
    }

    @Override
    public List<TableInfo> searchForm(String formTitle, String startTime, String endTime, Integer page, Integer limit) {
        return tableInfoService.searchFormByRoot(formTitle, startTime, endTime, page, limit);
    }

    @Override
    public Integer getFormCountOnCondition(String formTitle, String startTime, String endTime) {
        return tableInfoService.getSearchFormCountByRoot(formTitle, startTime, endTime);
    }

    @Override
    public TableInfo getFormById(Integer formId) {
        return tableInfoService.getFormById(formId);
    }

    @Override
    public void changeFormInfo(Integer formId, String formTitle, LocalDateTime startTime, LocalDateTime endTime, String realPath, String basePath) throws DocumentException, IOException, NoSuchMethodException {
        // 获取需要修改的表单信息
        TableInfo targetTable = tableInfoService.getTableInfoById(formId, 1);
        /*
         撤销原来的任务，设置新任务
         */
        long now = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        long startOffset = startTime.toEpochSecond(ZoneOffset.of("+8")) - now;
        long endOffset = endTime.toEpochSecond(ZoneOffset.of("+8")) - now;
        int flag = 0;
        // 撤销旧任务
        if(TimingTask.cancelTimingTask(formId+"start") == -1){
            throw new RuntimeException("{"+formId+"start}任务取消出错");
        }
        if(TimingTask.cancelTimingTask(formId+"end") == -1){
            throw new RuntimeException("{"+formId+"}任务取消出错");
        }
        // 依据情况创建新任务
        // 执行修改的方法
        Method method = this.getClass().getDeclaredMethod("changeTableFlag", Integer.class, Integer.class);
        if(endOffset <= 0){// 任务已经结束
            flag = 2;
            TableInfo tableInfo = new TableInfo(formId, formTitle, startTime, endTime, flag);
            tableInfoService.updateTableInfo(tableInfo); // 修改表单信息
        }else if(startOffset <= 0){// 任务应当开始尚未结束
            flag = 1;
            TableInfo tableInfo = new TableInfo(formId, formTitle, startTime, endTime, flag);
            tableInfoService.updateTableInfo(tableInfo); // 修改表单信息
            // 定时任务
            TimingTask.createTask(formId+"end", endOffset, new Task(formId+"end", this, method, formId, 2));
        }else{// 任务尚未开始也尚未结束
            TableInfo tableInfo = new TableInfo(formId, formTitle, startTime, endTime, 0);
            tableInfoService.updateTableInfo(tableInfo); // 修改表单信息
            // 定时任务
            TimingTask.createTask(formId+"start", startOffset, new Task(formId+"start", this, method, formId, 1));
            TimingTask.createTask(formId+"end", endOffset, new Task(formId+"end", this, method, formId, 2));
        }
        /*
         修改表单文件数据
         */
        // 发布目录下的文件与源代码中的文件目录
        Document document1 = XmlUtils.readDocument(realPath+targetTable.getTfsrc());
        // 获取数据并写入文件
        document1.selectSingleNode("//form-title").setText(formTitle);
        document1.selectSingleNode("//time-range").setText(DateUtils.date2str(startTime)+"|"+DateUtils.date2str(endTime));
        // 将文件写入
        XmlUtils.writeToFile(document1, realPath+targetTable.getTfsrc(), true);
        XmlUtils.writeToFile(document1, basePath+targetTable.getTfsrc(), true);
    }

    @Override
    public Integer deleteForm(Integer formId) {
        return tableInfoService.deleteForm(formId);
    }

    @Override
    public Map getFormAllFields(Integer formId, String realPath, Integer formStatus) throws DocumentException {
        Map<String, Object> ret = new HashMap<>();
        // 获取表单的所有信息
        TableInfo tableInfo = tableInfoService.getTableInfoById(formId, formStatus);
        // 表单路径
        String path = realPath + tableInfo.getTfsrc();
        // 读取文件，获得文档对象
        Document doc = XmlUtils.readDocument(path);
        // 获取所有的字段名，作为表头传递给前端
        List<Map> header = new ArrayList<>();
        List<Element> elements = (List<Element>) doc.selectNodes("//form-items/form-item");
        for (Element element : elements) {
            Map<String, String> map = new HashMap<>();
            map.put("label", element.element("label").getText());
            map.put("value", "value");
            header.add(map);
        }
        ret.put("header", header);
        // 获取对应的字段值
        List<Element> userInfos = doc.selectNodes("//user");
        List<Map> infos = new ArrayList<>();
        // 表单中用户的所有字段值
        for (Element element : userInfos) {
            Map<String, Object> map = new HashMap<>();
            // 设置用户id
            map.put("uid", element.attributeValue("id"));
            // 设置标签名和值
            List<Map> fList = new ArrayList<>();
            List<Element> children = element.selectNodes("field");
            for (Element child : children) {
                Map<String, Object> f = new HashMap<>();
                f.put("label", child.attributeValue("label"));
                f.put("value", child.attributeValue("value"));
                fList.add(f);
            }
            map.put("info", fList);
            infos.add(map);
        }
        ret.put("userInfo", infos);
        return ret;
    }

    @Override
    public List<Keys> getAllKeys() {
        return keysService.getAllKeys();
    }

    @Override
    public void createForm(FormInfo formInfo, String realPath, String basePath, String creatorId) throws IOException, NoSuchMethodException {
        /*
         对数据进行检查，去除多余空格
         */
        StringUtils.trimData(formInfo);
        /*
         选出符合接收表单条件的用户
         */
        formInfo.setStudents(getTargetUsers(formInfo.getStudents(), formInfo.getClasses(), formInfo.getMajors(),
                formInfo.getDepartments(), formInfo.getGrades()));
        // 处理表单新增字段
        handleNewField(formInfo.getFormData(), formInfo.getSubmitToDb());
        /*
         将所有信息写入文件并在数据库插入以及开启定时任务
         */
        createFormAndTableInfo(formInfo, realPath, basePath, creatorId);
    }

    /**
     * 创建表单文件并在数据库插入表单信息
     * @param formInfo
     * @param realPath
     * @param basePath
     * @param creatorId
     * @throws IOException
     */
    @Override
    public void createFormAndTableInfo(FormInfo formInfo, String realPath, String basePath, String creatorId) throws IOException, NoSuchMethodException {
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
        // 学生id集合字符串
        String studentIds = null;
        List<String> studentsList = formInfo.getStudents();
        if(studentsList != null && studentsList.size() > 0){
            StringBuilder studentsText = new StringBuilder();
            for (String s : studentsList) {
                studentsText.append(s).append("/");
            }
            studentIds = studentsText.substring(0, studentsText.length()-1);
            students.setText(studentIds);
        }
        // 字段信息
        org.dom4j.Element formItems = header.addElement("form-items");
        List<FormField> formFields = formInfo.getFormData();
        if(formFields != null && formFields.size() > 0){
            for (FormField formField : formFields) {
                org.dom4j.Element formItem = formItems.addElement("form-item");
                formItem.addElement("id").addText(formField.getId());
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
        for(String userId : formInfo.getStudents()){
            Element user = body.addElement("user").addAttribute("id", userId);
            for (int j = 0; j < formFields.size(); j++) {
                FormField field = formFields.get(j);
                // 获取数据库中已有字段对应的值
                String fieldValue = "";
                if(!field.getNewField()){ // 是数据库中原有字段
                    UserInfo userInfo = null;
                    // 部分引用字段需要二次查询，年级，院系，专业，班级(kid为5，4, 10, 6)
                    Integer keyId = Integer.valueOf(field.getKid());
                    if(keyId.equals(5) || keyId.equals(4) || keyId.equals(10) || keyId.equals(6)){// 是年级字段
                        userInfo = userInfoService.getUserInfoWithReference(keyId, Integer.valueOf(userId));
                    }else{
                        userInfo = userInfoService.getUserInfoById(keyId, Integer.valueOf(userId));
                    }
                    fieldValue = userInfo.getValue();
                }
                Element fieldElement = user.addElement("field")
                        .addAttribute("kid", field.getKid())
                        .addAttribute("label", field.getLabel())
                        .addAttribute("value", fieldValue);
            }
        }
        // 存储文件名
        String fileName = formInfo.getFormTitle() + "$" + LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")) + ".xml";
        // 使用工具类，将文档写入磁盘
        XmlUtils.writeToFile(doc, basePath+"\\formFiles\\"+fileName, true);
        XmlUtils.writeToFile(doc, realPath+"\\formFiles\\"+fileName, true);
        // 写入完成，将新表单信息插入数据库
        addTableInfoAndStartJob(formInfo.getFormTitle(), formInfo.getTimeRange(), "/formFiles/"+fileName, creatorId, studentIds);
    }

    /**
     * 处理新增字段是否是提交数据库，为新增字段添加id
     * @param fields
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleNewField(@NotNull List<FormField> fields, @NotNull Boolean submitToDB){
        // 数据库已有字段
        List<FormField> oldFields = new ArrayList<>();
        // 新增字段
        List<FormField> newFields = new ArrayList<>();
        for (FormField field : fields) {
            if(field.getNewField()){
                newFields.add(field);
            }else{
                oldFields.add(field);
            }
        }
        if(submitToDB){ // 需要提交到数据库保存
            /*
             将新增字段在数据库中插入并将插入后获得的id取出
             */
            // 选出最后一条新增字段数，以接着序号新增
            Keys lastOne = keysService.selectLastOne();
            String kname = lastOne.getKname();
            Integer newFieldId = null;
            if(kname.startsWith("newField")){
                newFieldId = new Integer(kname.substring(8));
            }
            if(newFieldId == null){
                newFieldId = 0;
            }
            // 接着上次的序号继续新增
            String namePrefix = "newField";
            for (FormField field : newFields) {
                StringBuilder options = null;
                if(field.getHasOption()){
                    options = new StringBuilder();
                    for(String option : field.getOptions()){
                        options.append(option).append("/");
                    }
                }
                String options2 = options == null ? null : options.substring(0, options.length()-1);
                newFieldId++;
                Keys key = new Keys(namePrefix+newFieldId, field.getType(), options2, field.getLabel());
                // 插入新增字段
                keysService.insertForKey(key);
                // 将新增字段的主键赋值给字段对象
                field.setKid(key.getKid().toString());
            }
            /*
             所有用户全部增加新字段的
            */
            List<User> users = userService.selectAll();
            for (User user : users) {
                for (FormField field : newFields) {
                    UserInfo userInfo = new UserInfo(field.getKid(), "", String.valueOf(user.getUid()));
                    userInfoService.insertWithoutKey(userInfo);
                }
            }
        }else{ // 无需提交到数据库，将新增字段赋予自定义kid
            for(int i = 0; i < newFields.size(); i++){
                newFields.get(i).setKid("new"+(i+1));
            }
        }
    }

    /**
     * 获取所有能接收表单的对象
     * @param targetUsers
     * @param classes
     * @param majors
     * @param departments
     * @param grades
     */
    @Override
    public List<String> getTargetUsers(List<String> targetUsers, List<String> classes, List<String> majors,
                                       List<String> departments, List<String> grades){
        if(targetUsers == null || targetUsers.size() == 0){ // 学生列表为空
            // 班级列表是否为空
            if(classes != null && classes.size() > 0){ // 根据班级查找所有的学生
                Integer[] classes2 = (Integer[]) classes.stream().map(Integer::valueOf).toArray(Integer[]::new);
                List<User> users = userService.getUserByClass(classes2);
                if(users != null){
                    targetUsers = users.stream().map(s -> String.valueOf(s.getUid())).collect(Collectors.toList());
                }
            }else if(majors != null && majors.size() > 0){// 班级列表空，专业列表不空
                Integer[] majors2 = (Integer[]) majors.stream().map(Integer::valueOf).toArray(Integer[]::new);
                List<User> users = userService.getUserByMajor(majors2);
                if(users != null){
                    targetUsers = users.stream().map(s -> String.valueOf(s.getUid())).collect(Collectors.toList());
                }
            }else if(departments != null && departments.size() > 0){// 专业列表空，院系列表不空
                Integer[] departments2 = (Integer[]) departments.stream().map(Integer::valueOf).toArray(Integer[]::new);
                List<User> users = userService.getUserByDepartment(departments2);
                if(users != null){
                    targetUsers = users.stream().map(s -> String.valueOf(s.getUid())).collect(Collectors.toList());
                }
            }else if(grades != null && grades.size() > 0){// 院系列表空，年级列表不空
                Integer[] grades2 = (Integer[]) grades.stream().map(Integer::valueOf).toArray(Integer[]::new);
                List<User> users = userService.getUserByGrade(grades2);
                if(users != null){
                    targetUsers = users.stream().map(s -> String.valueOf(s.getUid())).collect(Collectors.toList());
                }
            }else{ // 所有条件列表均为空，即是全选
                targetUsers = userService.selectAllNormalUserId();
            }
        }
        return targetUsers;
    }

    @Override
    public int changeUserStatus(Integer uid, Integer status) {
        return userService.changeUserStatus(uid, status);
    }

    @Override
    public int changeAdminStatus(Integer uid, Integer status) {
        return userService.changeAdminStatus(uid,status);
    }

    @Override
    public List<TableInfo> getDeleteForm(Integer page, Integer limit) {
        return tableInfoService.getDeleteForm(page, limit);
    }

    @Override
    public Integer getDeleteFormCount() {
        return tableInfoService.getDeleteFormCount();
    }

    @Override
    public List<TableInfo> searchDeleteForm(String formTitle, String startTime, String endTime, Integer page, Integer limit) {
        return tableInfoService.searchDeleteForm(formTitle, startTime, endTime, page, limit);
    }

    @Override
    public int searchDeleteFormCount(String formTitle, String startTime, String endTime) {
        return tableInfoService.searchDeleteFormCount(formTitle, startTime, endTime);
    }

    @Override
    public int resetDeleteForm(Integer formId) {
        return tableInfoService.resetDeleteForm(formId);
    }

    @Override
    public List<Keys> getUserAllInfo(Integer userId) {
        List<Keys> list = keysService.getUserAllInfo(userId);
        // 找出年级院系专业信息
        String grade = null, department = null, major = null;
        for (Keys keys : list) {
            String label = keys.getKcnname();
            if("年级".equals(label)){
                grade = keys.getKusvalue();
            }else if("院系".equals(label)){
                department = keys.getKusvalue();
            }else if("专业".equals(label)){
                major = keys.getKusvalue();
            }
        }
        for(Keys keys : list) {
            String label = keys.getKcnname();
            // 对特殊引用值进行再次处理
            if ("年级".equals(label)) {
                List<Grade> grades = gradeService.getGrade();
                StringBuilder sb = new StringBuilder();
                StringBuilder reduce = grades.stream().reduce(sb, (acc, item) -> {
                    acc.append(item.getGid()).append(":").append(item.getGrade()).append("&");
                    return acc;
                }, (acc, item) -> null);
                keys.setKtypevalue(reduce.substring(0, reduce.length() - 1));
            } else if ("院系".equals(label)) {
                if (grade == null) { // 信息不足直接退出
                    return null;
                }
                List<Department> departments = departmentService.getDepartmentByGrade(new Integer[]{Integer.valueOf(grade)});
                StringBuilder sb = new StringBuilder();
                StringBuilder reduce = departments.stream().reduce(sb, (acc, item) -> {
                    acc.append(item.getDid()).append(":").append(item.getDname()).append("&");
                    return acc;
                }, (acc, item) -> null);
                keys.setKtypevalue(reduce.substring(0, reduce.length() - 1));
            }else if("专业".equals(label)){
                if(department == null) {
                    return null;
                }
                List<Major> majors = majorService.getMajorByDepartmentId(new Integer[]{Integer.valueOf(department)});
                StringBuilder sb = new StringBuilder();
                StringBuilder reduce = majors.stream().reduce(sb, (acc, item) -> {
                    acc.append(item.getMid()).append(":").append(item.getMname()).append("&");
                    return acc;
                }, (acc, item) -> null);
                keys.setKtypevalue(reduce.substring(0, reduce.length()-1));
            }else if("班级".equals(label)){
                if(major == null){
                    return null;
                }
                List<Clazz> clazzes = clazzService.getClazzByMajorId(new Integer[]{Integer.valueOf(major)});
                StringBuilder sb = new StringBuilder();
                StringBuilder reduce = clazzes.stream().reduce(sb, (acc, item) -> {
                    acc.append(item.getCid()).append(":").append(item.getCnum()).append("&");
                    return acc;
                }, (acc, item) -> null);
                keys.setKtypevalue(reduce.substring(0, reduce.length()-1));
            }
        }
        return list;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int modifyUserInfoByRoot(KeysWithUid changedUserInfo) {
        String userId = changedUserInfo.getUserId();
        List<Keys> keys = changedUserInfo.getData();
        int ret = keys.stream().map(s -> new UserInfo(String.valueOf(s.getKid()), s.getKusvalue(), userId))
                .mapToInt(s -> userInfoService.updateValue(s))
                .sum();
        return ret;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int modifyRootInfoBySelf(List<Keys> rootInfo, String uid) {
        int ret = rootInfo.stream().map(s -> new UserInfo(String.valueOf(s.getKid()), s.getKusvalue(), uid))
                .mapToInt(s -> userInfoService.updateValue(s))
                .sum();
        return ret;
    }

    @Override
    public int changeRootPwdBySelf(Integer uid, String oldPwd, String newPwd) {
        User user = userService.getUserById(uid);
        if(user.getUstatus() == 1 && user.getUlevel() == 2){ // 判断必须是正常且是管理员用户
            // 验证密码
            if(MD5Utils.encryption(user.getUsername(), oldPwd).equals(user.getPassword())){ // 密码验证通过，修改密码
                user.setPassword(MD5Utils.encryption(user.getUsername(), newPwd));
                int ret = userService.updateById(user);
                return ret;
            }else{
                return 0;
            }
        }
        return -1;
    }

    @Override
    public int resetUserPwd(Integer uid) {
        User user = userService.getUserById(uid);
        user.setPassword(MD5Utils.encryption(user.getUsername(), "000000Aa@"));
        int ret = userService.updateById(user);
        return ret;
    }

    @Override
    public int changeUserLevel(Integer uid, Integer level) {
        return userService.updateUserLevel(uid, level);
    }

    /**
     * 添加表单信息到数据库，设定定时任务
     * @param formTitle
     * @param timeRange
     * @param src
     * @param creatorId
     * @param studentIds
     */
    private void addTableInfoAndStartJob(String formTitle, List<String> timeRange, String src, String creatorId, String studentIds) throws NoSuchMethodException {
        // 格式化时间字符串为对象
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.parse(timeRange.get(0), dtf);
        LocalDateTime endTime = LocalDateTime.parse(timeRange.get(1), dtf);
        LocalDateTime now = LocalDateTime.now();
        // 初始表单标志位
        Integer flag = 0;
        // 距离表单开始时间
        long offsetStart = startTime.toEpochSecond(ZoneOffset.of("+8")) - now.toEpochSecond(ZoneOffset.of("+8"));
        long offsetEnd = endTime.toEpochSecond(ZoneOffset.of("+8")) - now.toEpochSecond(ZoneOffset.of("+8"));
        // 确定标志位
        if(offsetEnd <= 0){
            flag = 2;
        }else if(offsetStart <= 0){
            flag = 1;
        }
        // 将表单信息插入数据库并返回id
        TableInfo tableInfo = new TableInfo(formTitle, startTime, endTime, src, creatorId, studentIds, flag);
        tableInfoService.insertInfoForId(tableInfo);
        // 表单id
        String formId = tableInfo.getTfid().toString();
        // 添加定时任务
        Method method = this.getClass().getDeclaredMethod("changeTableFlag", Integer.class, Integer.class);
        if(flag.equals(0)){// 表单尚未开始
            // 定时开始任务
            TimingTask.createTask(formId+"start", offsetStart,
                    new Task(formId+"start", this, method, tableInfo.getTfid(), 1));
            // 定时结束任务
            TimingTask.createTask(formId+"end", offsetEnd,
                    new Task(formId+"end", this, method, tableInfo.getTfid(), 2));
        }else if(flag.equals(1)){// 表单已经开始，只需要定时结束任务
            TimingTask.createTask(formId+"end", offsetEnd,
                    new Task(formId+"end", this, method, tableInfo.getTfid(), 2));
        }
    }

    /**
     * 改变表单标志位
     * @param id
     * @param flag
     * @return
     */
    private Integer changeTableFlag(Integer id, Integer flag){
        return tableInfoService.updateFlag(id, flag);
    }


}
