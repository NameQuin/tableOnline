package team.tb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import team.tb.common.FormInfo;
import team.tb.pojo.TableInfo;

public interface TableInfoMapper {
    int deleteByPrimaryKey(Integer tfid);

    int insert(TableInfo record);

    TableInfo selectByPrimaryKey(@Param("tfid") Integer tfid, @Param("formStatus") Integer formStatus);

    List<TableInfo> selectAll();

    int updateByPrimaryKey(TableInfo record);

    /**
     * 根据分页信息获取相应条数的表单信息
     * @param id
     * @param page
     * @param limit
     * @return
     */
    List<TableInfo> getFormListByAdminId(@Param("id") Integer id, @Param("page") Integer page, @Param("limit") Integer limit);

    /**
     * 获取数据总条数
     * @return
     */
    int getFormCount(Integer id);

    /**
     * 搜索符合条件的表单
     * @param formTitle
     * @param startTime
     * @param endTime
     * @param page
     * @param limit
     * @return
     */
    List<TableInfo> searchForm(@Param("id") Integer id, @Param("formTitle") String formTitle, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("page") Integer page, @Param("limit") Integer limit);

    /**
     * 符合搜索条件的表单总数
     * @param formTitle
     * @param startTime
     * @param endTime
     * @return
     */
    Integer getFormCountOnCondition(@Param("id") Integer id, @Param("formTitle") String formTitle, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 插入不带主键的信息
     * @param tableInfo
     */
    void insertInfoForId(TableInfo tableInfo);

    /**
     * 更新表单的标志位
     * @param id
     * @param flag
     * @return
     */
    Integer updateFlag(@Param("id") Integer id, @Param("flag") Integer flag);

    /**
     * 根据id获取表单信息
     * @param formId
     * @return
     */
    TableInfo getFormById(Integer formId);

    /**
     * 更新表单的标题与起始时间，标志位
     * @param tableInfo
     * @return
     */
    Integer updatePartTableInfo(TableInfo tableInfo);

    /**
     * 修改表单状态位，假装删除
     * @param formId
     * @return
     */
    Integer updateFormStatus(Integer formId);


    /**
     * 只获取全部表单的部分信息
     * @return
     */
    List<TableInfo> selectALlForms();

    /**
     * 根据条件获取当前普通用户能填写的表单
     * @return
     */
    List<TableInfo> searchFormByCurUser(@Param("formTitle") String formTitle, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 查询所有未被删除的表单
     * @param page
     * @param limit
     * @return
     */
    List<TableInfo> getFormListByRoot(@Param("page") Integer page, @Param("limit") Integer limit);

    /**
     * 查询所有未被删除的表单的数量
     * @return
     */
    Integer getFormCountByRoot();

    /**
     * 按条件搜索表单
     * @param formTitle
     * @param startTime
     * @param endTime
     * @param page
     * @param limit
     * @return
     */
    List<TableInfo> searchFormByRoot(@Param("formTitle") String formTitle, @Param("startTime") String startTime,
                                     @Param("endTime") String endTime,@Param("page") Integer page,@Param("limit") Integer limit);

    /**
     * 按条件搜索表单数据条数
     * @param formTitle
     * @param startTime
     * @param endTime
     * @return
     */
    Integer getSearchFormCountByRoot(@Param("formTitle") String formTitle,@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 获得已被标记删除的表单
     * @param page
     * @param limit
     * @return
     */
    List<TableInfo> getDeleteForm(@Param("page") Integer page, @Param("limit") Integer limit);

    /**
     * 获得已被删除标记的表单总数
     * @return
     */
    Integer getDeleteFormCount();

    /**
     * 符合条件且被标记删除的表单
     * @param formTitle
     * @param startTime
     * @param endTime
     * @param page
     * @param limit
     * @return
     */
    List<TableInfo> searchDeleteForm(@Param("formTitle") String formTitle, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("page") Integer page, @Param("limit") Integer limit);

    /**
     * 符合条件且被标记删除的表单数
     * @param formTitle
     * @param startTime
     * @param endTime
     * @return
     */
    int searchDeleteFormCount(@Param("formTitle") String formTitle, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 恢复被删除的表单
     * @param formId
     * @return
     */
    int resetDeleteForm(@Param("formId") Integer formId);
}
