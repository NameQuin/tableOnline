package team.tb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import team.tb.pojo.TableInfo;

public interface TableInfoMapper {
    int deleteByPrimaryKey(Integer tfid);

    int insert(TableInfo record);

    TableInfo selectByPrimaryKey(Integer tfid);

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
}
