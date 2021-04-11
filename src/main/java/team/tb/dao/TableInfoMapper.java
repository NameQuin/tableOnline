package team.tb.dao;

import java.util.List;
import team.tb.pojo.TableInfo;

public interface TableInfoMapper {
    int deleteByPrimaryKey(Integer tfid);

    int insert(TableInfo record);

    TableInfo selectByPrimaryKey(Integer tfid);

    List<TableInfo> selectAll();

    int updateByPrimaryKey(TableInfo record);
}
