package team.tb.service;

import team.tb.common.FormInfo;
import team.tb.pojo.TableInfo;

import java.util.List;

public interface TableInfoService {

    /**
     * 根据管理员id获取其发布的表单
     * @param id
     * @param page
     * @param limit
     * @return
     */
    List<TableInfo> getFormListByAdminId(Integer id, Integer page, Integer limit);

    /**
     * 获取该管理员发布的表单数量
     * @return
     */
    int getFormCount(Integer id);

    /**
     * 根据条件搜索表单
     * @param formTitle
     * @param startTime
     * @param endTime
     * @param page
     * @param limit
     * @return
     */
    List<TableInfo> searchForm(Integer id, String formTitle, String startTime, String endTime, Integer page, Integer limit);

    /**
     * 符合搜索条件的表单数量
     * @param formTitle
     * @param startTime
     * @param endTime
     * @return
     */
    Integer getFormCountOnCondition(Integer id, String formTitle, String startTime, String endTime);

    /**
     * 根据Id获取表单信息
     * @param formId
     * @return
     */
    TableInfo getFormById(Integer formId);

    /**
     * 插入不带主键的信息
     * @param tableInfo
     */
    void insertInfoForId(TableInfo tableInfo);

    /**
     * 修改表单状态位
     * @param id
     * @param flag
     * @return
     */
    Integer updateFlag(Integer id, Integer flag);

    /**
     * 更新表单的时间与表名，标志位
     * @param tableInfo
     * @return
     */
    Integer updateTableInfo(TableInfo tableInfo);

    /**
     * 根据id获取表单的全部信息
     * @param formId
     * @param formStatus 表单状态是被删除的0还是未被删除的1
     * @return
     */
    TableInfo getTableInfoById(Integer formId, Integer formStatus);

    /**
     * 根据id删除表单，值修改标志位，不进行真实删除
     * @param formId
     * @return
     */
    Integer deleteForm(Integer formId);

    /**
     * 获取所有的表单部分信息
     * @return
     */
    List<TableInfo> selectAllForms();

    /**
     * 根据条件获取当前普通用户可以填写的表单
     * @return
     */
    List<TableInfo> searchFormByCurUser(String formTitle, String startTime, String endTime);

    /**
     * root查询所有未被删除的表单
     * @param page
     * @param limit
     * @return
     */
    List<TableInfo> getFormListByRoot(Integer page, Integer limit);

    /**
     * root查询所有未被删除的表单数量
     * @return
     */
    Integer getFormCountByRoot();

    /**
     * root搜索符合条件的表单
     * @param formTitle
     * @param startTime
     * @param endTime
     * @param page
     * @param limit
     * @return
     */
    List<TableInfo> searchFormByRoot(String formTitle, String startTime, String endTime, Integer page, Integer limit);

    /**
     * root搜索符合条件的表单数量
     * @param formTitle
     * @param startTime
     * @param endTime
     * @return
     */
    Integer getSearchFormCountByRoot(String formTitle, String startTime, String endTime);

    /**
     * 获得已被删除标记的表单
     * @param page
     * @param limit
     * @return
     */
    List<TableInfo> getDeleteForm(Integer page, Integer limit);

    /**
     * 获得已被删除标记的表单总数
     * @return
     */
    Integer getDeleteFormCount();

    /**
     * 按条件搜索被标记删除的表单
     * @param formTitle
     * @param startTime
     * @param endTime
     * @param page
     * @param limit
     * @return
     */
    List<TableInfo> searchDeleteForm(String formTitle, String startTime, String endTime, Integer page, Integer limit);

    /**
     * 符合条件搜索被标记删除的表单数
     * @param formTitle
     * @param startTime
     * @param endTime
     * @return
     */
    int searchDeleteFormCount(String formTitle, String startTime, String endTime);

    /**
     * 恢复被删除的表单
     * @param formId
     * @return
     */
    int resetDeleteForm(Integer formId);
}
