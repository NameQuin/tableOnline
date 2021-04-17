package team.tb.service;

import team.tb.pojo.Major;

import java.util.List;

public interface MajorService {
    /**
     * 根据院系id查找专业
     * @param departmentId
     * @return
     */
    List<Major> getMajorByDepartmentId(Integer departmentId);
}
