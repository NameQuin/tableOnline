package team.tb.service.impl;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.tb.common.FormField;
import team.tb.common.FormInfo;
import team.tb.dao.UserMapper;
import team.tb.pojo.Keys;
import team.tb.pojo.TableInfo;
import team.tb.pojo.User;
import team.tb.pojo.UserInfo;
import team.tb.service.KeysService;
import team.tb.service.TableInfoService;
import team.tb.service.UserInfoService;
import team.tb.service.UserService;
import team.tb.utils.MD5Utils;
import team.tb.utils.XmlUtils;

import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service

public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TableInfoService tableInfoService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private KeysService keysService;

    @Override
    public User getUserById(Integer uid) {
        return userMapper.selectByPrimaryKey(uid);
    }

    @Override
    public Integer updateById(User user) {
        return userMapper.updateByPrimaryKey(user);
    }

    @Override
    public User findUserByUsernameAndPwd(User user) {
        return userMapper.findUserByUsernameAndPwd(user);
    }

    @Override
    public List<User> getUserList(Integer page, Integer limit) {
        return userMapper.getUserList(page, limit);
    }

    @Override
    public int getCount() {
        return userMapper.getCount();
    }

    @Override
    public List<User> getUserOnCondition(Integer grade, Integer department, Integer major, Integer clazz, String username, Integer page, Integer limit) {
        return userMapper.getUserOnCondition(grade, department, major, clazz, username, page, limit);
    }

    @Override
    public Integer getUserCountOnCondition(Integer grade, Integer department, Integer major, Integer clazz, String username) {
        return userMapper.getUserCountOnCondition(grade, department, major, clazz, username);
    }

    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    public List<User> getUserByClass(Integer[] clazzs) {
        return userMapper.getUserByClass(clazzs);
    }

    @Override
    public List<User> getUserByMajor(Integer[] majors) {
        return userMapper.getUserByMajor(majors);
    }

    @Override
    public List<User> getUserByDepartment(Integer[] departments) {
        return userMapper.getUserByDepartment(departments);
    }

    @Override
    public List<User> getUserByGrade(Integer[] grades) {
        return userMapper.getUserByGrade(grades);
    }

    @Override
    public List<String> selectAllNormalUserId() {
        return userMapper.selectAllNormalUserId();
    }

    @Override
    public Map<String, Object> getAllFormCurUser(String uid, Integer page, Integer limit) {
        Map<String, Object> map = new HashMap<>();
        List<TableInfo> list = tableInfoService.selectAllForms();
        List<TableInfo> ret = list.stream().filter(s -> {
            String[] condition = s.getTfcondition().split("/");
            if (condition.length > 0) {
                for (int i = 0; i < condition.length; i++) {
                    if (uid.equals(condition[i])) {
                        return true;
                    }
                }
            }
            return false;
        }).collect(Collectors.toList());
        int size = ret.size();
        map.put("count", size);
        if(size >= page){
            limit = Math.min(size - page, limit);
        }else{
            limit = page = size -1;
        }
        List<TableInfo> target = ret.subList(page, limit);
        map.put("list", target);
        return map;
    }

    @Override
    public Map<String, Object> searchForm(String id, String formTitle, String startTime, String endTime, Integer page, Integer limit) {
        Map<String, Object> map = new HashMap<>();
        List<TableInfo> list = tableInfoService.searchFormByCurUser(formTitle, startTime, endTime);
        List<TableInfo> ret = list.stream().filter(s -> {
            String[] condition = s.getTfcondition().split("/");
            if (condition.length > 0) {
                for (int i = 0; i < condition.length; i++) {
                    if (id.equals(condition[i])) {
                        return true;
                    }
                }
            }
            return false;
        }).collect(Collectors.toList());
        int size = ret.size();
        map.put("count", size);
        if(size >= page){
            limit = Math.min(size - page, limit);
        }else{
            limit = page = size -1;
        }
        List<TableInfo> target = ret.subList(page, limit);
        map.put("list", target);
        return map;
    }

    /**
     * 从文件读出所有字段数据并返回
     * @param formId
     * @param realPath
     * @return 0表示无法填写的表单尝试更新，1表示更新成功
     */
    @Override
    public Map<String, Object> getFormAllFields(Integer formId, String realPath, String id) throws DocumentException {
        Map<String, Object> ret = new HashMap<>();
        // 存入表单id
        ret.put("formId", formId);
        // 获取表单的所有信息
        TableInfo tableInfo = tableInfoService.getTableInfoById(formId, 1);
        // 存入表单开放状态
        ret.put("formStatus", tableInfo.getTfflag());
        // 表单路径
        String path = realPath + tableInfo.getTfsrc();
        // 读取文件，获得文档对象
        Document doc = XmlUtils.readDocument(path);
        // 是否提交到数据库
        Element subToDb = (Element) doc.selectSingleNode("//submit-to-db");
        ret.put("submitToDb", Boolean.valueOf(subToDb.getText()));
        // 获取所有字段节点
        List<FormField> fields = new ArrayList<>();
        List<Element> elements = (List<Element>) doc.selectNodes("//form-items/form-item");
        for (Element element : elements) {
            FormField field = new FormField();
            field.setId(element.element("id").getText());
            field.setKid(element.element("kid").getText());
            field.setType(element.element("type").getText());
            field.setLabel(element.element("label").getText());
            field.setReq(Boolean.valueOf(element.element("req").getText()));
            field.setCanEdit(Boolean.valueOf(element.element("can-edit").getText()));
            field.setNewField(Boolean.valueOf(element.element("new-field").getText()));
            field.setHasOption(Boolean.valueOf(element.element("has-option").getText()));
            // 处理选项部分
            List<Element> options = element.selectNodes("options/option");
            if(options.size() > 0){
                List<String> list = new ArrayList<>();
                options.forEach(s -> list.add(s.getText()));
                field.setOptions(list);
            }
            fields.add(field);
        }
        ret.put("fields", fields);
        // 获取对应的字段值
        List<Element> userInfos = doc.selectNodes("//user");
        List<Map> infos = new ArrayList<>();
        // 根据用户id拿到文件中的字段值
        for (Element element : userInfos) {
            if(id.equals(element.attributeValue("id"))){
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
                break;
            }
        }
        ret.put("userInfo", infos);
        return ret;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateUserInfoInForm(String realPath, String basePath, FormInfo data, String uid) throws DocumentException, IOException {
        // 根据表单id获取表单信息，判断表单是否在进行中，是则进行更新，否则就不更新
        Integer formId = Integer.valueOf(data.getFormTitle());
        TableInfo tableInfo = tableInfoService.getTableInfoById(formId, 1);
        // 是否需要在数据库同步更新
        boolean submitToDb = data.getSubmitToDb();
        // 表单位置
        String src1 = realPath + tableInfo.getTfsrc();
        String src2 = basePath + tableInfo.getTfsrc();
        if(tableInfo.getTfflag() != 1){
            return 0;
        }
        // 字段信息
        // 将字段信息过滤，选出需要更新的部分并组装成map，key为kid，value为对应字段信息
        Map<String, FormField> retFields = data.getFormData().stream()
                .filter(FormField::getCanEdit)
                .collect(Collectors.toMap(FormField::getKid, s -> s));
        if(submitToDb){ // 提交到数据库，将该用户下的字段进行更新
            retFields.forEach((kid, field) -> {
                UserInfo userInfo = new UserInfo();
                userInfo.setUid(uid);
                userInfo.setKid(field.getKid());
                userInfo.setValue(field.getValue());
                userInfoService.updateValue(userInfo);
            });
        }
        // 更新文件
        // 获取文档对象
        Document doc = XmlUtils.readDocument(src1);
        List<Element> users = doc.selectNodes("//user");
        for (Element user : users) {
            if(uid.equals(user.attributeValue("id"))){ // 找到目标用户，修改对应字段值
                List<Element> attributes = user.selectNodes("field");
                attributes.forEach(field -> {
                    FormField f = null;
                    if((f = retFields.get(field.attributeValue("kid"))) != null){
                        field.attribute("value").setValue(f.getValue());
                    }
                });
                break;
            }
        }
        // 更新完毕，写入磁盘
        XmlUtils.writeToFile(doc, src1, true);
        XmlUtils.writeToFile(doc, src2, true);
        // 获得用户信息节点
        return 1;
    }

    @Override
    public List<User> getAdminList(Integer page, Integer limit) {
        return userMapper.getAdminList(page, limit);
    }

    @Override
    public int getAdminCount() {
        return userMapper.getAdminCount();
    }

    @Override
    public List<User> searchAdmin(String username, Integer page, Integer limit) {
        return userMapper.searchAdmin(username, page, limit);
    }

    @Override
    public int searchAdminCount(String username) {
        return userMapper.searchAdminCount(username);
    }

    @Override
    public int changeUserStatus(Integer uid, Integer status) {
        return userMapper.changeUserStatus(uid, status);
    }

    @Override
    public int changeAdminStatus(Integer uid, Integer status) {
        return userMapper.changeAdminStatus(uid,status);
    }

    @Override
    public List<Keys> getUserAllInfo(Integer uid) {
        List<Keys> list = keysService.getUserAllInfo(uid);
        list.forEach(s -> {
            String label = s.getKcnname();
            // 对引用值进行再次查询
            if("年级".equals(label) || "院系".equals(label) || "专业".equals(label) || "班级".equals(label)){
                s.setKtype("text");
                UserInfo userInfo = userInfoService.getUserInfoWithReference(s.getKid(), uid);
                s.setKusvalue(userInfo.getValue());
            }
        });
        return list;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int modifyUserInfo(List<Keys> data, Integer uid) {
        // 筛选数据，仅对可编辑项(即kchange为true的数据进行修改)
        int ret = data.stream().filter(Keys::getKchange)
                .map(s -> new UserInfo(String.valueOf(s.getKid()), s.getKusvalue(), String.valueOf(uid)))
                .mapToInt(userInfo -> userInfoService.updateValue(userInfo)).sum();
        // 该项可编辑，修改数据
        return ret;
    }

    @Override
    public int changeUserPwdBySelf(Integer uid, String oldPwd, String newPwd) {
        User user = userMapper.selectByPrimaryKey(uid);
        if(user.getUstatus() == 1 && user.getUlevel() == 0){ // 判断必须是正常且普通用户
            // 验证密码
            if(MD5Utils.encryption(user.getUsername(), oldPwd).equals(user.getPassword())){ // 密码验证通过，修改密码
                System.out.println("密码验证通过: "+oldPwd);
                user.setPassword(MD5Utils.encryption(user.getUsername(), newPwd));
                int ret = userMapper.updateByPrimaryKey(user);
                return ret;
            }else{
                System.out.println("密码验证通不过："+oldPwd);
                return 0;
            }
        }
        return -1;
    }

    @Override
    public int updateUserLevel(Integer uid, Integer level) {
        return userMapper.updateUserLevel(uid, level);
    }
}
