package team.tb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.tb.dao.ClazzMapper;
import team.tb.pojo.Clazz;
import team.tb.service.ClazzService;

import java.util.List;

@Service
public class ClazzServiceImpl implements ClazzService {
    @Autowired
    private ClazzMapper clazzMapper;
    @Override
    public List<Clazz> getClazzByDepartmentId(Integer majorId) {
        return clazzMapper.getClazzByDepartmentId(majorId);
    }
}
