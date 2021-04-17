package team.tb.service;

import team.tb.pojo.Grade;

import java.util.List;

public interface GradeService {
    /**
     * 的到所有的年级信息
     * @return
     */
    List<Grade> getGrade();
}
