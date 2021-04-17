package team.tb.service;

import team.tb.pojo.TableInfo;

import java.util.List;

public interface AdminService {

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
}
