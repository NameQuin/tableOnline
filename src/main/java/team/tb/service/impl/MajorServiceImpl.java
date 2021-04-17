package team.tb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.tb.dao.MajorMapper;
import team.tb.pojo.Major;
import team.tb.service.MajorService;

import java.util.List;

@Service
public class MajorServiceImpl implements MajorService {
    @Autowired
    private MajorMapper majorMapper;
    @Override
    public List<Major> getMajorByDepartmentId(Integer departmentId) {
        return majorMapper.getMajorByDepartmentId(departmentId);
    }
}
