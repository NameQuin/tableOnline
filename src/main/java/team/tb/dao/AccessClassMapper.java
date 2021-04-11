package team.tb.dao;

import java.util.List;
import team.tb.pojo.AccessClass;

public interface AccessClassMapper {
    int insert(AccessClass record);

    List<AccessClass> selectAll();
}
